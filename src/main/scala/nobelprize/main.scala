package nobelprize

import com.github.tototoshi.csv.CSVReader

import java.io.File
import java.util.Scanner
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


      val category = "medicine"
      val gender = "male"
      val countryCode = "US"

      val scanner:Scanner = new java.util.Scanner(System.in)
      var continue:Boolean = true


      while (continue){
        println("\n\nChoose the Exploration service you would like to test: ")
        println(s"1. Filter by category: $category")
        println(s"2. Filter by gender: $gender")
        println("3. Find the youngest winner")
        println("4. Filter all winners alive")
        println(s"5. Filter all winners by countrycode: $countryCode")
        println("6. Find the oldest winner for each category of nobel prize")
        println("7. Sort the list of data by Firstname")
        println("8. Sort the list of data by Category")
        println("9. Sort the list of data by the year of Prize")
        println("10. Complex filtering using filterByCategory,filterByGender, filterByWinnersAlive ")
        println("11. Print all Operation Service")
        println("0. Exit")


         val choice = scanner.nextInt()


          choice match {
            case 1 =>  {
              val personsInCategory =   ExplorationService.filterByCategory(listofwinners, category)
              for ( i <- personsInCategory) println(i)
            }
            case 2 => {
              val personsByGender = ExplorationService.filterByGender(listofwinners, gender)
              for ( i <- personsByGender) println(i)
            }
            case 3 => {
              val youngest = ExplorationService.findTheMostYoungWinner(listofwinners)
              println(youngest)
            }
            case 4 => {
              val winnersAlive = ExplorationService.filterByWinnersAlive(listofwinners)
               for ( i <- winnersAlive) println(i)
            }
            case 5 => {
              val winnersByCountryCode = ExplorationService.allWinnersByCountryCode(listofwinners, countryCode)
              for ( i <- winnersByCountryCode) println(i)
            }
            case 6 => {
              val oldestWinners = ExplorationService.oldestWinnersOfEachCategory(listofwinners)
              for ( i <- oldestWinners) println(i)
            }
            /*-----Sort: we can pass the function of sort in the parameter to sort the data------*/
            case 7 => {
              val sortedByFirstname = Sorter.sort(listofwinners, Sorter.byFirstnameAscending);
                for ( i <- sortedByFirstname) println(i)
            }
            case 8 => {
              val sortedByCategory = Sorter.sort(listofwinners, Sorter.byCategoryAscending);
                for ( i <- sortedByCategory) println(i)
            }
            case 9 => {
              val sortedByYearPrize = Sorter.sort(listofwinners, Sorter.byYearPrize);
              for ( i <- sortedByYearPrize) println(i)
            }
            case 10 => {
              val personsInCategory =    ExplorationService
                .filterByWinnersAlive(ExplorationService
                  .filterByGender(ExplorationService
                    .filterByCategory(listofwinners, category), gender) )
              for ( i <- personsInCategory) println(i)

            }
            case 11 => {
                allOperationServices(listofwinners)
            }
            case 0 => {
              continue = false
            }
            case _ => {
                println("You need to choose the value from 0 to 11 ! ")
            }

          }

      }

      reader.close
    } catch {
      case ex:Exception => println("Cannot read the file " + ex.getMessage)
    }

  }


  /**
   * Test of Operation service
   * @param listofwinners
   */
  def allOperationServices (listofwinners:List[Winner]): Unit ={

    val countryCode = "US"
    val gender = "female"
    val year = "2009"

    println("--------Operation Service-----------")

    val percentCountry = OperationService.getWinnersPercentByCountryCode(listofwinners, countryCode)
    println(s"Percent of winners by $countryCode: $percentCountry")


    val genderPercent = OperationService.getPercentOfWinnersByGender(listofwinners, gender)
    println(s"Percent of winners by $gender: $genderPercent")

    val percentAlive = OperationService.getPercentOfWinnersAlive(listofwinners)
    println(s"Percent of winners alive: $percentAlive")

    val avg = OperationService.getAvgAgeOfWinners(listofwinners)
    println(s"Average age of winners is $avg years")

    val avgAgeFemale = OperationService.getAvgAgeOfWinnersByGender(listofwinners, gender)
    println(s"Average age of winners by $gender gender is  $avgAgeFemale years")

    val winnersByYear = OperationService.getWinnersCountByYear(listofwinners, year)
    println(s"Winners by year $year is $winnersByYear")

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
