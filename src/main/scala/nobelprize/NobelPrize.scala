package nobelprize


/**
 * Class represents the nobel prize with all its information.
 * It is use as a composite in the Winner (so in Person and Organisation)
 * @param year
 * @param category
 * @param overallMotivation
 * @param share
 * @param motivation
 */
 case class NobelPrize(year:String,
                       category:String,
                       overallMotivation:String,
                       share:String,motivation:String) {

}
