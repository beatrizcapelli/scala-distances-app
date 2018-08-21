package com.libring.scala.mongodb

import com.libring.scala.datatypes.City
import com.mongodb.MongoClientSettings
import org.bson.codecs.configuration.CodecRegistries
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

import scala.collection.SortedMap
import scala.concurrent.Await
import scala.concurrent.duration._

case class MongoDbHelper(dbClient: MongoClient, db: MongoDatabase) {

  def insertCities(cities: List[City]): Unit = {
    val collection: MongoCollection[City] = db.getCollection("cities")
    collection.drop()
    collection.insertMany(cities)
  }

  def findCities: SortedMap[String, List[City]] = {
    val collection: MongoCollection[City] = db.getCollection("cities")
    val loadedData = Await.result(collection.find.toFuture, 10.second).toList
    SortedMap[String, List[City]]() ++ loadedData.groupBy(_.state)
  }

  def closeConnection = if (dbClient != null) dbClient.close()
}

object MongoDbHelper {

  val CODEC_REGISTRY = CodecRegistries.fromRegistries(CodecRegistries.fromProviders(classOf[City]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val INSTANCE: MongoDbHelper = create

  def getInstance = INSTANCE

  def create: MongoDbHelper = {
    val client = initMongoClient
    MongoDbHelper(client, client.getDatabase("db").withCodecRegistry(CODEC_REGISTRY))
  }

  def initMongoClient: MongoClient = {
    MongoClient(MongoClientSettings.builder.codecRegistry(CODEC_REGISTRY).build)
  }
}
