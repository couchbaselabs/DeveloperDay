package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import net.spy.memcached.*;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Ex08Observe {

    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase - Observe & Durability");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));
        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "default", "", "");

            Gson json = new Gson();

            // create data
            UserData user1 = new UserData();
            user1.doctype = "learn";
            user1.username = "jsmith";
            user1.name = "John Smith";
            user1.email = "jsmith@email.com";
            user1.password = "p4ssw0rd";
            user1.logins = 0;

            String jsonDoc = json.toJson(user1);


            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Set Document with Durability : Persist to 2 nodes");

            {
                OperationFuture op = cb.set("mydoc", 0, jsonDoc, PersistTo.TWO);
                if ( op.getStatus().isSuccess() ) {
                    System.out.println("Success");
                } else {
                    System.out.println( "Status :"+ op.getStatus() );
                }
                System.out.println("");
            }


            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Set Document with Replication : Replicate to 2 nodes");

            {
                OperationFuture op = cb.set("mydoc", 0, jsonDoc, ReplicateTo.TWO);
                if ( op.getStatus().isSuccess() ) {
                    System.out.println("Success");
                } else {
                    System.out.println( "Status :"+ op.getStatus() );
                }
                System.out.println("");

            }



            System.out.println("\n\n");

            //cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
            System.exit(0);
        }

    }


}
