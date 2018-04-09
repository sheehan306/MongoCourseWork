package week3;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by deemoshea on 08/04/2018.
 */
public class HomeworkThreeOne {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongoClient.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");
        System.out.println(collection.count());

        MongoCursor<String> c = collection.distinct("name", String.class ).iterator();


        AggregateIterable<Document> cursor = collection.aggregate(Arrays.asList(new Document("$unwind","$scores"),
                new Document("$match", new Document("scores.type","homework")),
                new Document("$group", new Document("_id","$_id")
                                .append("minscore", new Document("$min","$scores.score")))
                ));
        for (Document obj: cursor) {
            System.out.println("Before: " + obj + "\n");

            collection.updateOne(Filters.eq("_id", obj.get("_id")),Updates.pull("scores",new Document("score",obj.get("minscore"))));

            System.out.println("After: " + obj + "\n");
        }

        mongoClient.close();
    }

}
