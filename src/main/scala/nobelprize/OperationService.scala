package nobelprize


object OperationService {

  /**
   * Calculates percentage of nobel prize winners by country code
   * @param winners List of nobel prize winners
   * @param countryCode Country code e.g. "US"
   * @return percent value
   */
  def getWinnersPercentByCountryCode(winners:List[Winner], countryCode:String) : Double = {
    val countryWins: Float = ExplorationService.allWinnersByCountryCode(winners, countryCode).length
    val allWins:Float= winners.length

    val percent = BigDecimal(countryWins/allWins).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    percent
  }

  /**
   * Calculates percentage of nobel prize winners by gender
   * @param winners List of nobel prize winners
   * @param gender "male" or "female"
   * @return percent value
   */
  def getPercentOfWinnersByGender(winners:List[Winner], gender:String) : Double = {
    val genderCount: Float = ExplorationService.filterByGender(winners, gender).length
    val allWins:Float= winners.length


    val  percent = BigDecimal(genderCount/allWins).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    percent
  }
  /**
   * Calculates percentage of nobel prize winners who are still alive
   * @param winners List of nobel prize winners
   * @return percent value
   */
  def getPercentOfWinnersAlive(winners:List[Winner]) : Double = {
    val winnersAlive : Float = ExplorationService.filterByWinnersAlive(winners).length
    val allWins:Float= winners.length

    val percent = BigDecimal(winnersAlive/allWins).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    percent

  }

  /**
   * Calculates the average age of winners based on the year of nobel prize
   * @param winners List of nobel prize winners
   * @return average value
   */
  def getAvgAgeOfWinners(winners:List[Winner]) : Double = {
  var avg : Double = 0
  var sum : Float = 0
  var counter : Int = 0
  winners
    .filter(w  => !w.gender.equals("org"))
    .map(_.asInstanceOf[Person])
    .foreach(p => {

        val bYear:Int = p.born.split("/").last.toInt
        val priceYear:Int = p.nobelPrize.year.toInt
        val age:Float = priceYear-bYear;
        counter = counter +1
        sum = sum + age

        if(age < 150)avg = sum /counter

    })
  avg = BigDecimal(avg).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  avg
}

  /**
   * Calculates the average age of winners based on the year of nobel prize by gender
   * @param winners List of nobel prize winners
   * @param gender "male" or "female"
   * @return average value
   */
  def getAvgAgeOfWinnersByGender(winners:List[Winner], gender:String) : Double = {
    val genderList = ExplorationService.filterByGender(winners, gender)

    val avg = getAvgAgeOfWinners(genderList)

    avg
  }
  /**
   * Calculates the number of winners by year
   * @param winners List of nobel prize winners
   * @param year year of the nobel prize
   * @return number of winners
   */
  def getWinnersCountByYear(winners:List[Winner], year:String) : Int = {
    val count =  winners
      .filter(w  => w.nobelPrize.year.equals(year)).length

    count
  }

}
