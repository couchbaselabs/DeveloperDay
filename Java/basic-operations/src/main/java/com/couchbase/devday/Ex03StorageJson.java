package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class SimpleDoc
{
    String docType;
    String name;
}

public class Ex03StorageJson {




    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase JSON Document Storage Operations");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "default", "");



            {
                System.out.println(" Store simple JSON");
                cb.set("mydoc", 0, "{\"doctype\":\"test\", \"name\":\"John Smith\"}");
                System.out.println("Stored value :"+ cb.get("mydoc") );
                System.out.println();
            }

            {
                System.out.println(" Store simple JSON using GSON parsing");
                SimpleDoc simpleDoc = new SimpleDoc();
                simpleDoc.docType = "test2";
                simpleDoc.name = "John Doe";
                Gson json = new Gson();
                String jsonString = json.toJson( simpleDoc );
                cb.set("mydoc2", 0, "{\"doctype\":\"test\", \"name\":\"John Smith\"}");
                System.out.println("GSON value  :"+ cb.get("mydoc2") );

                simpleDoc = json.fromJson( cb.get("mydoc2").toString(), SimpleDoc.class );
                System.out.println("Doc name :"+ simpleDoc.name);

                System.out.println("");
            }


            System.out.println("\n\n");

            cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
        }

    }
}
