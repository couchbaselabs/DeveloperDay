package com.couchbase.training;


import com.couchbase.client.CouchbaseClient;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Couchbase001 {


    public static void main(String args[]) throws Exception  {

       CouchbaseClient client = connectToCouchbase();

        basicOperations(client);

        basicOperationWithTTL(client);

        client.shutdown(10, TimeUnit.SECONDS);
        System.exit(0);

    }


    private static CouchbaseClient connectToCouchbase() {

        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient client = null;
        try {
            client = new CouchbaseClient(uris, "default", "", "");
        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
            System.exit(0);
        }

        return client;

    }


    private static void basicOperations(CouchbaseClient client) {

        System.out.println("--- --- Basic Operations --- ---");

        String myKey = "KEY-001";
        String dummyValue = "Hello word... in Couchbase !";


        // get the key
        Object object = client.get( myKey );
        System.out.println( "001 - Get the object for key " + myKey  +" : "+  object );


        System.out.println("\nSet the value");


        client.set( myKey, 0, dummyValue );
        object = client.get( myKey );
        System.out.println( "002 - Get the object for key " + myKey  +" : "+  object  );



        System.out.println("\nDelete the key");
        object = client.delete( myKey );
        object = client.get( myKey );
        System.out.println( "003 - Get the object for key " + myKey  +" : "+  object  );

        System.out.println("--- --- --- --- --- --- ---\n\n");

    }


    private static void  basicOperationWithTTL(CouchbaseClient client) throws Exception {

        System.out.println("--- --- Basic Operations with TTL --- ---");

        String myKey = "KEY-002";
        String dummyValue = "Hello word with TTL !";

        System.out.println("\nSet the value");

        client.set( myKey, 2, dummyValue );
        Object object = client.get( myKey );
        System.out.println( "001 - Get the object for key " + myKey  +" : "+  object +" -- "+ System.currentTimeMillis() );


        while (object != null) {
            Thread.sleep(500);
            object = client.get( myKey );
            System.out.println( "003 - Get the object for key " + myKey  +" : "+  object +" -- "+ System.currentTimeMillis() );
        }

        System.out.println("--- --- --- --- --- --- ---\n\n");

    }


}
