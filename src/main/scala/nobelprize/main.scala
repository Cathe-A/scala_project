package nobelprize

import com.github.tototoshi.csv.CSVReader
import java.io.File
import scala.collection.mutable.ListBuffer


object main {


  def main(args: Array[String]): Unit = {

    try{
      val reader = CSVReader.open(new File("src/data/06-NobelPrizeByWinner.csv"))
      val t = reader.toStream.toList

      //list of all winners in classes structure
      val listofwinners:List[Winner] = extractDataofWinners(t:List[List[String]])


      //TEST OF EXPLORATION SERVICE
      //val malesInMedicine =   ExplorationService.filterByCategory(listofwinners, "medicine")
      //val males = ExplorationService.filterByGender(malesInMedicine, "male")
      //ExplorationService.findTheMostYoungWinner(males)
     // for ( i <- ExplorationService.filterByWinnersAlive(listofwinners)) println(i)
     // for ( i <- ExplorationService.sort(listofwinners, ExplorationService.byFirstname)) println(i)
      //TEST of OPERATION SERVICE
     val percentCountry = OperationService.getWinnersPercentByCountryCode(listofwinners, "US")
      println(percentCountry)

      val genderPercent = OperationService.getPercentOfWinnersByGender(listofwinners, "female")
      println(genderPercent)

      val percentAlive = OperationService.getPercentOfWinnersAlive(listofwinners)
      println(percentAlive)
      val avg = OperationService.getAvgAgeOfWinners(listofwinners)
      println(avg)

      val avgAgeFemale = OperationService.getAvgAgeOfWinnersByGender(listofwinners,"female" )
      println(avgAgeFemale)

      val winnersByYear = OperationService.getWinnersCountByYear(listofwinners, "2009")
      println(winnersByYear)

      reader.close
    } catch {
      case ex:Exception => println("Cannot read the file " + ex.getMessage)
    }


  }




  def extractDataofWinners(t:List[List[String]]):List[Winner] = {
    val winners = ListBuffer[Winner]()
    val id = 0; val firstname = 1; val surname = 2; val born = 3;
    val died = 4; val bornCountry= 5; val bornCountryCode = 6;
    val bornCity = 7; val diedCountry=8; val diedCountryCode = 9;
    val diedCity = 10; val gender = 11; val year = 12; val category= 13;
    val overallMotivation = 14; val share = 15; val motivation = 16;


    for (line <- t) {

      if(line(firstname).equals("")) {
        println(s"Firstname not found for:   + $line . \nLine  ${t.indexOf(line)}  ignored")
      } else {

        val nobelPrize = NobelPrize(line(year), line(category), line(overallMotivation), line(share), line(motivation))

        line(gender) match {
          case "org" => winners += Organisation(line(id), line(firstname), line(gender), nobelPrize)

          case "male" | "female" => winners += Person(line(id), line(firstname), line(surname),line(born),
            line(died), line(bornCountry),line(bornCountryCode), line(bornCity),
            line(diedCountry), line(diedCountryCode), line(diedCity), line(gender), nobelPrize)

          case _ => println(s"Gender not found for:   + $line . \nLine  ${t.indexOf(line)}  ignored")
        } //endcase
      } //endelse
    } //end for
    winners.toList
  }







}
