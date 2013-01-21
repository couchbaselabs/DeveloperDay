package com.couchbase.devday;

import com.couchbase.client.CouchbaseClient;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Ex01Connect {

    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase Simple Connection");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "default", "");

            System.out.println( cb.getStats() );


            cb.shutdown(10, TimeUnit.SECONDS);


        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
        }

    }

}
