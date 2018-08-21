package com.libring.scala.util

import com.libring.scala.datatypes.City

object DistanceCalculatorUtil {

  val AVERAGE_RADIUS_OF_EARTH_KM = 6371

  def execute(city1: City, city2: City): Double = {
    val latDistance = Math.toRadians(city1.latitude - city2.latitude)
    val lngDistance = Math.toRadians(city1.longitude - city2.longitude)
    val sinLat = Math.sin(latDistance / 2)
    val sinLng = Math.sin(lngDistance / 2)
    val a = sinLat * sinLat +
      (Math.cos(Math.toRadians(city1.latitude)) *
        Math.cos(Math.toRadians(city2.latitude)) *
        sinLng * sinLng)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    (AVERAGE_RADIUS_OF_EARTH_KM * c)
  }

}
