package reasonable

import scalaz.{~>, Id}

object InteractConsole extends (Interact ~> Id.Id) {
  def apply[A](i: Interact[A]) = i match {
    case Ask(prompt) =>
      println(prompt)
      readLine
    case Tell(msg) =>
      println(msg)
  }
}
