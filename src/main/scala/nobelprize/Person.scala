package nobelprize

case class Person(id:String, firstname:String, surname:String,
                  born:String, died:String,
                  bornCountry:String,bornCountryCode:String,
                  bornCity:String,diedCountry:String,
                  diedCountryCode:String,diedCity:String,
                  gender:String, nobelPrize: NobelPrize) extends Winner{



  override def toString() : String = {
      id +  s" Firstname of person + $firstname" + s" Surname + $surname " +
        s"+ Borncountry + $bornCountry +  born: $born + died: $died"
  }



}