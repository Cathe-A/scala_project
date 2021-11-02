package nobelprize

case class Organisation(id:String, firstname:String, gender:String,
                        nobelPrize: NobelPrize) extends Winner {

}

