package com.libring.scala.util

import com.libring.scala.DistancesApp.LOGGER
import com.libring.scala.datatypes.{City, State}

import scala.collection.SortedMap

object SortUtil {
  def sortAndLogMap(paramMap: SortedMap[String, List[City]], func: Function1[(String, List[City]), (String, List[City])], id: String): SortedMap[String, List[City]] = {
    val sortedMap = paramMap.map(func)
    LOGGER.debug(s"${id}: ${sortedMap}")
    CsvUtil.writeMapToCsv(id + ".csv", sortedMap)
    sortedMap
  }

  def sortAndLogList(paramList: List[State], func: Function1[State, Double], id: String): List[State] = {
    val sortedList = paramList.sortBy(func)
    LOGGER.debug(s"${id}: ${sortedList}")
    CsvUtil.writeListToCsv(id + ".csv", sortedList)
    sortedList
  }

  object SortOrder {
    def cityName = (cities: (String, List[City])) => (cities._1, cities._2.sortBy(_.city))

    def populationDesc = (cities: (String, List[City])) => (cities._1, cities._2.sortWith((city1, city2) => city1.population > city2.population).take(2))

    def populationAsc = (cities: (String, List[City])) => (cities._1, cities._2.sortWith((city1, city2) => city1.population < city2.population).take(3))

    def stateInternalDistanceAsc = (state: State) => state.distance
  }
}


