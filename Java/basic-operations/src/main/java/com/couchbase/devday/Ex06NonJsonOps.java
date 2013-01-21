package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import net.spy.memcached.CASValue;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Ex06NonJsonOps {

    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase Non-JSON Ops");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "default", "", "");


            cb.set("store_strings", 10, "The quick brown fox jumped over the lazy dog.");
            cb.set("store_floats", 10, 3.14159265358979F);
            cb.set("store_integers", 10, -42);

            System.out.println( "String :"+ cb.get("store_strings") );
            System.out.println( "Float :"+ cb.get("store_floats") );
            System.out.println( "Int :"+ cb.get("store_integers") );


            System.out.println( "--------------------------------------------------------------------------");
            System.out.println( "Append/Prepend Ops" );

            OperationFuture op = cb.set("mylist", 30, "oranges");
            System.out.println("List :" + cb.get("mylist"));
            Long cas = op.getCas() ;



            System.out.println("Add an item...");
            cb.prepend(cas, "mylist", "apples,");
            CASValue casValue = cb.gets("mylist");
            cas = casValue.getCas();
            System.out.println("List :" + casValue.getValue()  );


            System.out.println("Add an item...");
            cb.append(cas, "mylist", ",bananas");
             casValue = cb.gets("mylist");
            cas = casValue.getCas();
            System.out.println("List :" + casValue.getValue()  );


            System.out.println("Add an item...");
            cb.append(cas, "mylist", ",lemons");
            casValue = cb.gets("mylist");
            cas = casValue.getCas();
            System.out.println("List :" + casValue.getValue()  );



            System.out.println("\n\n");

            cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
            System.exit(0);
        }

    }

}
