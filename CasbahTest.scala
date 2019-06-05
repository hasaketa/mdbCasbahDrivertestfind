import com.mongodb.casbah.Imports._

object CasbahTest { 
   def main(args: Array[String]) { 
      val mongoClient = MongoClient("localhost", 27017)

      val db = mongoClient("casbahtest")
val collection = db("findtest")
      collection.drop()

      collection.insert( MongoDBObject("hello" -> "world"))

      val startTime = System.currentTimeMillis()
      System.out.println("Find result: " +  collection.findOne(MongoDBObject("hello" -> "world")))
      val endTime = System.currentTimeMillis()

      System.out.println("Total execution time for find command: " + (endTime - startTime) + "ms")

      val coll = db("aggregationtest")
      coll.drop()

      coll += MongoDBObject("title" -> "Programming in Scala" ,
                      "author" -> "Martin",
                      "pageViews" ->  50,
                      "tags" ->  ("scala", "functional", "JVM") ,
                      "body" ->  "Test body by Martin")

      coll += MongoDBObject("title" -> "Programming Clojure" ,
                      "author" -> "Stuart",
                      "pageViews" ->  35,
                      "tags" ->  ("clojure", "functional", "JVM") ,
                      "body" ->  "Test body by Stuart")

      coll += MongoDBObject("title" -> "MongoDB: The Definitive Guide" ,
                      "author" -> "Kristina",
                      "pageViews" ->  90,
                      "tags" ->  ("databases", "nosql", "future") ,
                      "body" ->  "Test body by Kristina")

      val aggregationOptions = AggregationOptions(AggregationOptions.CURSOR)

      val startTime2 = System.currentTimeMillis()
      System.out.println("Aggregation result: " + coll.aggregate(
	  List(
  MongoDBObject("$project" -> MongoDBObject("author" -> 1, "tags" -> 1)),
  MongoDBObject("$unwind" -> "$tags"),
  MongoDBObject("$group" -> MongoDBObject("_id" -> "$tags", "authors" -> MongoDBObject("$addToSet" -> "$author")))
	), 
       aggregationOptions )) 
      val endTime2 = System.currentTimeMillis()
      System.out.println("Total execution time for aggregation command: " + (endTime2 - startTime2) + "ms" )

  }

}
