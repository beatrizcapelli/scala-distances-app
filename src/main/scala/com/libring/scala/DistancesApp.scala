package com.libring.scala

import com.libring.scala.datatypes.{City, State}
import com.libring.scala.mongodb.MongoDbHelper
import com.libring.scala.util.SortUtil.SortOrder
import com.libring.scala.util._
import org.apache.log4j.Logger

object DistancesApp extends App {

  val JSON_FILE = "cities.json"

  val LOGGER = Logger.getLogger(DistancesApp.getClass.getName)

  try {
    val mongoDb = MongoDbHelper.getInstance

    val jsonCitiesList: List[City] = JsonUtil.parseJsonIntoCityList(JSON_FILE)

    mongoDb.insertCities(jsonCitiesList)

    val initialMap = mongoDb.findCities

    val alphabeticalMap = SortUtil.sortAndLogMap(initialMap, SortOrder.cityName, id = "ALPHABETICAL")
    val smallestPopulationMap = SortUtil.sortAndLogMap(initialMap, SortOrder.populationAsc, id = "SMALLEST")
    val biggestPopulationMap = SortUtil.sortAndLogMap(initialMap, SortOrder.populationDesc, id = "BIGGEST")

    // create list of States and its Biggest Cities and calculate the distance between them
    val statesList = biggestPopulationMap.filter(statesWithMoreThanTwoCities).map(x => {
      val stateName = x._1
      val biggestCity = x._2.head
      val secondBiggestCity = x._2.tail.head
      State(stateName, biggestCity, secondBiggestCity, DistanceCalculatorUtil.execute(biggestCity, secondBiggestCity))
    })

    val sortedDistancesList = SortUtil.sortAndLogList(statesList.toList, SortOrder.stateInternalDistanceAsc, "DISTANCES")
    logResult(sortedDistancesList.head)

  } catch {
    case e: Throwable => LOGGER.fatal("An error occurred during the manipulation of the files. Please verify.", e)
  } finally {
    MongoDbHelper.getInstance.closeConnection
  }

  def logResult(resultState: State) = {
    LOGGER.info(s"The state of ${resultState.state} is the one with smallest distance between its biggest cities " +
      s"${resultState.biggestCity} and ${resultState.secondBiggestCity}. " +
      s"The distance is " +
      f"${resultState.distance}%4.2f " +
      s"Km.")
  }

  def statesWithMoreThanTwoCities = (x: (String, List[City])) => x._2.length > 1
}