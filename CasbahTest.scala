import com.mongodb.casbah.Imports._

object CasbahTest { 
   def main(args: Array[String]) { 
      val mongoClient = MongoClient("localhost", 27017)

      val db = mongoClient("casbahtest")
val collection = db("findtest")
      collection.drop()

      collection.insert( MongoDBObject("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
                             "count" -> 1, "info" -> MongoDBObject("x" -> 203, "y" -> 102)))

      val startTime = System.currentTimeMillis()
      System.out.println("Find result: " +  collection.findOne(MongoDBObject("name" -> "MongoDB")))
      val endTime = System.currentTimeMillis()

      System.out.println("Total execution time for find command: " + (endTime - startTime) + "ms")

      val coll = db("aggregationtest")
      coll.drop()

      coll.insert( MongoDBObject("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
                             "count" -> 1, "info" -> MongoDBObject("x" -> 203, "y" -> 102)))

      val aggregationOptions = AggregationOptions(AggregationOptions.CURSOR)

      val startTime2 = System.currentTimeMillis()
      
      val results = coll.aggregate(
	  List(
  MongoDBObject("$match" -> MongoDBObject("name" -> "MongoDB"))
	), 
       aggregationOptions ) 
     
      for (result <- results) println(result)
      
      val endTime2 = System.currentTimeMillis()
      System.out.println("Total execution time for aggregation command: " + (endTime2 - startTime2) + "ms" )

  }

}
