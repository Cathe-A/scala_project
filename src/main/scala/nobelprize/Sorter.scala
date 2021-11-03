package nobelprize

/**
 * This class use the HOF to sort the data.
 */
object Sorter {

  /**
   * HOF that takes a winner and the function in parameters and returns
   * a sorted list of winners (Persons and Organisations)
   * @param winners
   * @param compare
   * @return
   */
  def sort(winners:List[Winner], compare:(Winner, Winner) => Boolean): List[Winner] = {

    val sortedWinners = winners.sortWith(compare(_,_))
    sortedWinners
  }


  /*--------------SORTING FUNCTIONS ---------------------*/

  /**
   * Function that sort the list of winners but it firstname
   * @param winner1
   * @param winner2
   * @return
   */
  def byFirstnameAscending(winner1:Winner, winner2: Winner):Boolean = {

    winner1.firstname < winner2.firstname

  }

  def byFirstnameDescending(winner1:Winner, winner2: Winner):Boolean = {

    winner1.firstname > winner2.firstname

  }


  def byId(winner1:Winner, winner2: Winner):Boolean = {

    winner1.id.toInt < winner2.id.toInt;

  }

  def byYearPrize(winner1:Winner, winner2: Winner):Boolean = {

    winner1.nobelPrize.year.toInt < winner2.nobelPrize.year.toInt ;

  }

  def byCategoryAscending(winner1:Winner, winner2: Winner):Boolean = {

    winner1.nobelPrize.category > winner2.nobelPrize.category

  }

  def byCategoryDescending(winner1:Winner, winner2: Winner):Boolean = {

    winner1.nobelPrize.category < winner2.nobelPrize.category

  }




}
