package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Ex05AtomicCounter {

    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase Atomic Operations");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "default", "");


            cb.delete("counter");


            System.out.println("Set Counter to 0");
            cb.incr("counter", 1, 0);
            System.out.println("Counter : " + cb.get("counter"));
            System.out.println("");


            System.out.println("Increment by 1");
            cb.incr("counter",1) ;
            System.out.println("Counter : " + cb.get("counter"));
            System.out.println("");


            System.out.println("Increment by 10");
            cb.incr("counter",10) ;
            System.out.println("Counter : " + cb.get("counter"));
            System.out.println("");


            cb.delete("counter");
            System.out.println("-- counter deleted --");


            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Using incr + initial values for counters ");
            cb.incr("counter", 1,1) ;
            System.out.println("Counter : " + cb.get("counter"));
            System.out.println("");

            System.out.println("Decrease below 0");
            cb.decr("counter", 1000) ;
            System.out.println("Counter : " + cb.get("counter"));
            System.out.println("");


            System.out.println("set counter to -1, decr, then incr sets to max value");
            cb.incr("counter", -1, -1);
            cb.incr("counter", 1);
            System.out.println("Counter : " + cb.get("counter"));
            System.out.println("");

            System.out.println("incr on max value returns it to 0");
            cb.incr("counter",1) ;
            System.out.println("Counter : " + cb.get("counter"));
            System.out.println("");

            cb.delete("counter");

            System.out.println("\n\n");

            cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
        }

    }

}
