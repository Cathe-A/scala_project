package nobelprize

import com.github.tototoshi.csv.CSVReader
import java.io.File
import scala.collection.mutable.ListBuffer

/**
 * authors: Aymon Ekaterina, Agosthazy Csaba
 */
object main {

  /**
   * In the main method, we retrieve the data from the csv file, put them in to classes and
   * use the ExplorationService and OperationService to manipulate the data
   * @param args
   */
  def main(args: Array[String]): Unit = {

    try{
      //Retrieve data from the csv file and transform it to the list of lists
      val reader = CSVReader.open(new File("src/data/06-NobelPrizeByWinner.csv"))
      val t = reader.toStream.toList

      println("----------------------------------DATA PROCESSING------------------------")
      //list of all winners in Classes structure
      val listofwinners:List[Winner] = extractDataOfWinners(t:List[List[String]])
      println("-----------------------------END OF DATA PROCESSING---------------------------------------")


      println("----------------------------------DATA MANIPULATION------------------------")

      /*----------TEST OF EXPLORATION SERVICE----------*/
      val personsInMedicine =   ExplorationService.filterByCategory(listofwinners, "medicine")
      // for ( i <- personsInMedicine) println(i)

      val males = ExplorationService.filterByGender(personsInMedicine, "male")
      // for ( i <- males) println(i)

      val youngest = ExplorationService.findTheMostYoungWinner(males)
     // println(youngest)

      val winnersAlive = ExplorationService.filterByWinnersAlive(listofwinners)
     // for ( i <- ExplorationService.filterByWinnersAlive(listofwinners)) println(i)

      val wins = ExplorationService.allWinnersByCountryCode(listofwinners, "US")
      // for ( i <- wins) println(i)

      val oldestWinners = ExplorationService.oldestWinnersOfEachCategory(listofwinners)
      //for ( i <- oldestWinners) println(i)

      /*-----Sort: we can pass the function of sort in the parameter to sort the data------*/

      val sortedByFirstname = Sorter.sort(listofwinners, Sorter.byFirstnameDescending);
      // for ( i <- sortedByFirstname) println(i)

      val sortedByCategory = Sorter.sort(listofwinners, Sorter.byCategoryDescending);
      // for ( i <- sortedByCategory) println(i)

      val sortedByYearPrize = Sorter.sort(listofwinners, Sorter.byYearPrize);
      // for ( i <- sortedByYearPrize) println(i)





      /*----------TEST of OPERATION SERVICE----------*/
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


  /**
   *This method processes the data and creates a list of winners in the classes structure
   * (Persons, Organisation, NobelPrize)
   * @param t
   * @return list of all winners both organisations and persons with the nobel prize
   */
  def extractDataOfWinners(t:List[List[String]]):List[Winner] = {

    val winners = ListBuffer[Winner]()

    //Define the number of column in the csv file
    val id = 0; val firstname = 1; val surname = 2; val born = 3;
    val died = 4; val bornCountry= 5; val bornCountryCode = 6;
    val bornCity = 7; val diedCountry=8; val diedCountryCode = 9;
    val diedCity = 10; val gender = 11; val year = 12; val category= 13;
    val overallMotivation = 14; val share = 15; val motivation = 16;


    /*We pass through the list and we put each line into class structure depending whether
    * it is organization or person */
    for (line <- t) {

      if(line(firstname).equals("")) {
        println(s"Firstname not found for:   + $line . Line  ${t.indexOf(line)}  ignored")
      } else {

        val nobelPrize = NobelPrize(line(year), line(category), line(overallMotivation), line(share), line(motivation))

        line(gender) match {
          case "org" => winners += Organisation(line(id), line(firstname), line(gender), nobelPrize)

          case "male" | "female" => winners += Person(line(id), line(firstname), line(surname),line(born),
            line(died), line(bornCountry),line(bornCountryCode), line(bornCity),
            line(diedCountry), line(diedCountryCode), line(diedCity), line(gender), nobelPrize)

          case _ => println(s"Gender not found for:   + $line . Line  ${t.indexOf(line)}  ignored")
        } //endcase
      } //endelse
    } //end for
    winners.toList
  }







}
