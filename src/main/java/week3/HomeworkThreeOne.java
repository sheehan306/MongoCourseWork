package week3;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

/**
 * Created by deemoshea on 08/04/2018.
 */
public class HomeworkThreeOne {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongoClient.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");
        System.out.println(collection.count());

        Document myDoc = collection.find(Filters.in("scores")).first();


        System.out.println(myDoc.toJson());

//        MongoCursor<Document> cursor = collection.find().iterator();
//        try {
//            while (cursor.hasNext()) {
//                System.out.println(cursor.next().toJson());
//            }
//        } finally {
//            cursor.close();
//        }

    }

}
