package nobelprize

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar



object OperationService {
  //returns the percent of winners by country code
  def getWinnersPercentByCountryCode(winners:List[Winner], countryCode:String) : Double = {
    val countryWins: Float = ExplorationService.allWinnersByCountryCode(winners, countryCode).length
    val allWins:Float= winners.length

    val percent = BigDecimal(countryWins/allWins).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    percent
  }
  //returns the percent of winners by gender
  def getPercentOfWinnersByGender(winners:List[Winner], gender:String) : Double = {
    val genderCount: Float = ExplorationService.filterByGender(winners, gender).length
    val allWins:Float= winners.length


    val  percent = BigDecimal(genderCount/allWins).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    percent
  }

  def getPercentOfWinnersAlive(winners:List[Winner]) : Double = {
    val winnersAlive : Float = ExplorationService.filterByWinnersAlive(winners).length
    val allWins:Float= winners.length

    val percent = BigDecimal(winnersAlive/allWins).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    percent

  }

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

  def getAvgAgeOfWinnersByGender(winners:List[Winner], gender:String) : Double = {
    val genderList = ExplorationService.filterByGender(winners, gender)

    val avg = getAvgAgeOfWinners(genderList)

    avg
  }
  def getWinnersCountByYear(winners:List[Winner], year:String) : Int = {
    val count =  winners
      .filter(w  => w.nobelPrize.year.equals(year)).length

    count
  }

}
