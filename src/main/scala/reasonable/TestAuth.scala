package reasonable

import auth._
import scalaz.{~>, Id}

object TestAuth {
  val testAuth: Auth ~> Id.Id = new (Auth ~> Id.Id) {
    def apply[A](a: Auth[A]) = a match {
      case Login(uid, pwd) =>
        if (uid == "john.snow" && pwd == "Ghost")
          Some(User("john.snow"))
        else None
      case HasPermission(u, _) =>
        u.id == "john.snow"
    }
  }
}
