package reasonable

import scalaz.Monad

object test {

  type Tester[A] =
    Map[String, String] => (List[String], A)
  

  implicit val testerMonad = new Monad[Tester] {
    def point[A](a: ⇒ A): Tester[A] = _ ⇒ (List(), a)
    def bind[A,B](t: Tester[A])(f: A => Tester[B]) =
      m => {
        val (o1, a) = t(m)
        val (o2, b) = f(a)(m)
        (o1 ++ o2, b)
      }
  }
}
