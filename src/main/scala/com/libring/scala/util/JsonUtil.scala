package com.libring.scala.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.libring.scala.datatypes.City

import scala.io.Source

object JsonUtil {

  def parseJsonIntoCityList(filename: String) = {
    val json = Source.fromResource(filename)
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.readValue[List[City]](json.reader())
  }
}
