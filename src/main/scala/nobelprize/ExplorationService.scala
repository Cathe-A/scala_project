package nobelprize

import scala.collection.mutable.ListBuffer

/**
 * This class represents the data exploration service: filter of data, aggregation, etc
 * There is also a recursive function
 */
object ExplorationService {

  /**
   * In this method we filter the winners by the country code, passed in the parameters
   * @param winners
   * @param countryCode
   * @return filtered list of certain country
   */
  def allWinnersByCountryCode(winners:List[Winner], countryCode:String): List[Winner] = {

    val winnersPersons =   filterByPersons(winners)
                          .filter(p => p.bornCountryCode.equals(countryCode))
    winnersPersons
  }

  /**
   * In this method we find the most youngest winner of the nobel prize
   * by calculating the date of prize minus the birthdate
   * @param winners
   * @return one Winner of type Person who is the most youngest
   */
  def findTheMostYoungWinner (winners:List[Winner]):Winner = {

    //We want only Persons, not Organisations
    val winnersPersons =  filterByPersons(winners)

    //takes the values of year of prize, the year of birth and the Person itself
    val dates = winnersPersons.map(p => (p.nobelPrize.year.toInt, p.born.split("/").last.toInt, p))

    //define the minimum function to compare two persons
    def min(x:(Int, Int,Person), y:(Int, Int,Person)): (Int,Int, Person)
                                           = if((x._1 - x._2) - (y._1 - y._2) < 0) x else y

    // reduce to find the youngest using our min function
     val youngest = dates.reduce((x, y) => min(x,y))

    // 3 because we return the third element, which means the Person
    youngest._3
  }

  /**
   *Filter data by gender passed in parameters
   * @param winners
   * @param gender
   * @return filtered list by gender that was passed in parameters
   */
  def filterByGender(winners: List[Winner], gender:String): List[Winner] = {

    val winnersPersons =  winners.filter(w  => w.gender.equals(gender))
    winnersPersons
  }

  /**
   * Filter data by category passed in parameters
   * @param winners
   * @param category
   * @return filtered list by category that was passed in parameters
   */
  def filterByCategory(winners: List[Winner], category:String): List[Winner] = {

    val winnersPersons =  winners.filter(w  => w.nobelPrize.category.equals(category))
    winnersPersons
  }

  /**
   * Filter by all winners (Persons) that are alive
   * @param winners
   * @return list of winners that are alive
   */
  def filterByWinnersAlive (winners: List[Winner]):List[Winner] = {

    val winnersPersons =  filterByPersons(winners)
                              .filter(p => p.died.equals("0000-00-00"))
    winnersPersons
  }

  /**
   * Filter all data by persons
   * @param winners
   * @return list of Persons (!)
   */
  def filterByPersons (winners: List[Winner]):List[Person] = {
    val winnersPersons =  winners
                            .filter(w  => !w.gender.equals("org"))
                            .map(_.asInstanceOf[Person])

    winnersPersons
  }

  /**
   *In this method, we filter the oldest winners of Nobel Prize of each category
   * using inside the recursive function
   * @param winners
   * @return
   */
  def oldestWinnersOfEachCategory(winners: List[Winner]):List[Winner] = {

      val result = ListBuffer[Winner]()
      //We must have sorted list by category
      val sortedCategories = Sorter.sort(winners, Sorter.byCategoryDescending)
      //Filter by persons with correct birthdate
      val winnersPersons =   filterByPersons(sortedCategories)
                              .filter(p => !p.born.equals("0000/00/00") && p.died.equals("0000-00-00"))


      result += recursiveSearchOfOldestWinners(winnersPersons, 0, result)

    result.toList
  }

  /**
   *This recursive function retrieves the oldest winner of each category by going to the deepest
   * level and returning the oldest person. When the category changes during the recursive return,
   * it store the current oldest person in the result set and return the first person of the next category.
   * The last returned person correspond to the oldest person of the last category.
   * The list of winners must be sorted beforehand by its category.
   * @param winners
   * @param position
   * @param result
   * @return the oldest person by category
   */
  private def recursiveSearchOfOldestWinners( winners: List[Winner], position:Int,
                                               result:ListBuffer[Winner]):Person ={
      //exit point of recursion
      if(position == winners.length-1) {
         return winners(position).asInstanceOf[Person]
      }

    //going deeper in one level
    val newPosition = position + 1
    val oldest = recursiveSearchOfOldestWinners( winners, newPosition, result)
    val current = winners(position).asInstanceOf[Person]

    //if the categories are different, it means that we found the oldest of the previous category
    // and we start a new category with a current person as the "supposed" oldest
    if(!oldest.nobelPrize.category.contentEquals(current.nobelPrize.category)) {
      result += oldest
      return current
    }

    //if we are in the same category, so we need to check and return the oldest one, by comparing theirs ages
    if((java.time.Year.now().getValue() - oldest.born.split("/").last.toInt)
              < (java.time.Year.now().getValue() - current.born.split("/").last.toInt)) current else oldest

  }







}
