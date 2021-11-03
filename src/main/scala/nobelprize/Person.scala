package nobelprize

/**
 *This class represents a person based on the information related only to person (not the organisation)
 * @param id
 * @param firstname
 * @param surname
 * @param born
 * @param died
 * @param bornCountry
 * @param bornCountryCode
 * @param bornCity
 * @param diedCountry
 * @param diedCountryCode
 * @param diedCity
 * @param gender
 * @param nobelPrize class which contains all information about prize of this person
 */
case class Person(id:String, firstname:String, surname:String,
                  born:String, died:String,
                  bornCountry:String,bornCountryCode:String,
                  bornCity:String,diedCountry:String,
                  diedCountryCode:String,diedCity:String,
                  gender:String, nobelPrize: NobelPrize) extends Winner{



  override def toString() : String = {
                                        id +  s" Firstname: $firstname Surname: $surname " +
                                        s"Borncountry: $bornCountry Born: $born Died: $died " +
                                        s"Year of nobelPrize: ${nobelPrize.year} " +
                                        s"Category: ${nobelPrize.category} "
  }



}