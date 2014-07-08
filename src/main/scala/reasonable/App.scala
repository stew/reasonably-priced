package reasonable

import language.higherKinds
import scalaz.{Coyoneda, Coproduct, Free, Id, Inject, Monad, NaturalTransformation, ~>, -\/, \/-}
import Free.FreeC
import scalaz.syntax.std.option._
import auth._
import Inject._

object App {
  def lift[F[_], G[_], A](fa: F[A])(implicit I: Inject[F, G]): FreeC[G, A] =
    Free.liftFC(I.inj(fa))

  def or[F[_], G[_], H[_]](f: F ~> H, g: G ~> H): ({type cp[α]=Coproduct[F,G,α]})#cp ~> H = new NaturalTransformation[({type cp[α]=Coproduct[F,G,α]})#cp,H] {
    def apply[A](fa: Coproduct[F,G,A]): H[A] = fa.run match {
      case -\/(ff) ⇒ f(ff)
      case \/-(gg) ⇒ g(gg)
    }
  }

  val KnowSecret = "KnowSecret"

  type OurApp[A] = Coproduct[Auth, Interact, A]
  type ACoyo[A] = Coyoneda[OurApp,A]
  type AFree[A] = Free[ACoyo,A]
  def point[A](a: ⇒ A): FreeC[OurApp, A] = Monad[AFree].point(a)

  def prg(implicit I: Interacts[OurApp], A: auth.Auths[OurApp]) = {
    import I._
    import A._

    for {
      uid ← ask("What's your user ID?")
      pwd ← ask("Password, please.")
      u ← login(uid, pwd)
      b ← u.cata(none = point(false),
                 some = hasPermission(_, KnowSecret))
      _ <- if(b) tell("UUDDLRLRBA") else tell("Go away!")
    } yield ()
  }

  val interpreters: OurApp ~> Id.Id = or(TestAuth.testAuth, InteractConsole)
  val coyoint: ({type f[x] = Coyoneda[OurApp, x]})#f ~> Id.Id = Coyoneda.liftTF(interpreters)
  def runApp = prg.mapSuspension(coyoint)

  def main(args: Array[String]) {
    runApp
  }
}
