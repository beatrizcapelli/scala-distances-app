package com.libring.scala.util

import java.io.File

import com.github.tototoshi.csv.CSVWriter
import com.libring.scala.datatypes.{City, State}

import scala.collection.SortedMap

object CsvUtil {

  def writeMapToCsv = (filename: String, sourceMap: SortedMap[String, List[City]]) => {
    val writer = CSVWriter.open(new File(filename))
    sourceMap.foreach(x => writer.writeRow(List(x._1, x._2.mkString(" - "))))
    writer.close()
  }

  def writeListToCsv = (filename: String, sourceList: List[State]) => {
    val writer = CSVWriter.open(new File(filename))
    sourceList.foreach(x => writer.writeRow(List(x.toCsvString)))
    writer.close()
  }

}
