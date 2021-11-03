package nobelprize

/**
 * This class represents the organisation that is received the nobel price.
 * It takes toString method from its parent (Winner)
 * @param id
 * @param firstname
 * @param gender
 * @param nobelPrize class which contains all information about prize of this organisation
 */
case class Organisation(id:String, firstname:String, gender:String,
                        nobelPrize: NobelPrize) extends Winner {

}

