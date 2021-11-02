
trait Animal{
  val name:String
}
case class Cat (name:String) extends Animal {
}
case class Dog (name:String) extends Animal {
}


val l = List(1,2,3,4,5)

l.updated(1,l(4)).updated(4,l(1))







