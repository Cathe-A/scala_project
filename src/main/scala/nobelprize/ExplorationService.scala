package nobelprize

import scala.collection.mutable.ListBuffer

object ExplorationService {

  def allWinnersByCountryCode(winners:List[Winner], countryCode:String): List[Winner] = {

  //  assert(!winners.isEmpty, m => )
    val winnersPersons =  winners
                          .filter(w  => !w.gender.equals("org"))
                          .map(_.asInstanceOf[Person])
                          .filter(p => p.bornCountryCode.equals(countryCode))
    winnersPersons
  }

  def findTheMostYoungWinner (winners:List[Winner]):Winner = {

    val winnersPersons =  winners
                              .filter(w  => !w.gender.equals("org"))
                              .map(_.asInstanceOf[Person])

    val dates = winnersPersons.map(p => (p.nobelPrize.year.toInt, p.born.split("/").last.toInt, p))

    def min(x:(Int, Int,Person), y:(Int, Int,Person)): (Int,Int, Person)
                                           = if((x._1 - x._2) - (y._1 - y._2) < 0) x else y

     val youngest = dates.reduce((x, y) => min(x,y))
      youngest._3
  }

  def filterByGender(winners: List[Winner], gender:String): List[Winner] = {

    val winnersPersons =  winners.filter(w  => w.gender.equals(gender))
    winnersPersons
  }

  def filterByCategory(winners: List[Winner], category:String): List[Winner] = {

    val winnersPersons =  winners.filter(w  => w.nobelPrize.category.equals(category))
    winnersPersons
  }

  def filterByWinnersAlive (winners: List[Winner]):List[Winner] = {

    val winnersPersons =  winners
                              .filter(w  => !w.gender.equals("org"))
                              .map(_.asInstanceOf[Person])
                              .filter(p => p.died.equals("0000-00-00"))
    winnersPersons
  }


  def sort(winners:List[Winner], compare:(Winner, Winner) => Int): List[Winner] = {

    val sortedWinners = ListBuffer[Winner]()

    for (j <- winners.indices) {
      val greaterWinner = winners(j)

      if (sortedWinners.isEmpty || compare(greaterWinner, sortedWinners.last) < 0) {

        for (i <- winners.indices) {

          val winner2 = winners(i)

          if (compare(greaterWinner, winner2) < 0) {


          }
        } //endfor
        sortedWinners += greaterWinner
      }
    }

    sortedWinners.toList
  }


  def byFirstname(winner1:Winner, winner2: Winner):Int = {

    winner1.firstname.compareTo(winner2.firstname)

  }



}
