package com.libring.scala.datatypes

case class City(city: String, growth_from_2000_to_2013: String, latitude: Double, longitude: Double, population: Int, rank: Int, state: String) {
  override def toString: String = s"${city} (${population})"
}
