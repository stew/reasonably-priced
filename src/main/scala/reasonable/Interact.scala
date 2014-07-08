package reasonable

import scalaz.{Id, ~>, Free, Monad, Coyoneda, Functor, Inject}
import reasonable._

sealed trait Interact[A]

case class Ask(prompt: String)
  extends Interact[String]
 
case class Tell(msg: String)
  extends Interact[Unit]

object Console extends (Interact ~> Id.Id) {
  def apply[A](i: Interact[A]) = i match {
    case Ask(prompt) =>
      println(prompt)
      readLine
    case Tell(msg) =>
      println(msg)
  }
}

class Interacts[F[_]](implicit I: Inject[Interact,F]) {
  def tell(msg: String): Free.FreeC[F,Unit] = App.lift(Tell(msg))
  def ask(prompt: String): Free.FreeC[F,String] = App.lift(Ask(prompt))
}

object Interacts {
  implicit def interacts[F[_]](implicit ev: Inject[Interact,F]): Interacts[F] = new Interacts
}
