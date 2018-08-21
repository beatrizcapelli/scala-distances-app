package com.libring.scala.datatypes

case class State(state: String, biggestCity: City, secondBiggestCity: City, distance: Double) {
  override def toString: String = s"$state - ${biggestCity.city} and ${secondBiggestCity.city} - ${distance.toInt} Km"
  def toCsvString: String = s"$state,${biggestCity.city} and ${secondBiggestCity.city},${distance.toInt} Km"
}
