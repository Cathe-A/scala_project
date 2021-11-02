package nobelprize

trait Winner {
    val id:String
    val firstname:String
    val gender:String
    val nobelPrize:NobelPrize

    override def toString() : String = {
        id +  s" Firstname + $firstname" + s" Gender + $gender"
    }
}


