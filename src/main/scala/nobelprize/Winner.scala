package nobelprize

/**
 * Defining the trait Winner allows us to use it
 * as a common interface for two classes: Person
 * and Organisation that facilities the manipulation of the data.
 */
trait Winner {
    val id:String
    val firstname:String
    val gender:String
    val nobelPrize:NobelPrize

    override def toString() : String = {
        id +  s" Firstname: $firstname Gender: $gender Category:  ${nobelPrize.category}"
    }
}


