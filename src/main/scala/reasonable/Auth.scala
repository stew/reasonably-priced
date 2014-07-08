package reasonable

object auth {

  type UserId = String
  type Password = String
  type Permission = String

  case class User(id: UserId)

  sealed trait Auth[A]
  case class Login(userId: UserId, password: Password) extends Auth[Option[User]]
  case class HasPermission(user: User, permission: Permission) extends Auth[Boolean]

  import scalaz.Inject
  import scalaz.Free.FreeC

  class Auths[F[_]](implicit I: Inject[Auth,F]) {
    def login(id: UserId, pwd: Password): FreeC[F,Option[User]] =
      App.lift(Login(id, pwd))
    def hasPermission(u: User, p: Permission): FreeC[F,Boolean] =
      App.lift(HasPermission(u, p))
  }

  object Auths {
    implicit def auths[F[_]](implicit I: Inject[Auth,F]): Auths[F] = new Auths[F]
  }
}
