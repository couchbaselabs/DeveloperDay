package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import net.spy.memcached.CASValue;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Ex07Cas {


    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase - Compare and Swap");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

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

            cb.set( user1.email, 0 ,  json.toJson( user1 ) );


            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Initialize and Retrieve CAS");
            CASValue casValue1 = cb.gets( user1.email );

            System.out.println("Cas Value before update CAS 1 :"+ casValue1.getCas()  );

            System.out.println("");

            System.out.println("--------------------------------------------------------------------------");
            System.out.println("\tNow We'll update the document which results in a new CAS");
            user1.logins += 1;

            OperationFuture op = cb.replace( user1.email, 0 ,  json.toJson( user1 ) );
            System.out.println("\tNow CAS has changed [(cas1) "+ casValue1.getCas() +" != #{cas2} "+ op.getCas() +"]");


            System.out.println("--------------------------------------------------------------------------");
            System.out.println("If we try to update the document from the second copy, it will fail");

            OperationFuture op2 = cb.asyncCAS( user1.email, casValue1.getCas(), json.toJson( user1 ) );
            System.out.println( op2.getStatus() );


            System.out.println("\n\n");

            cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
            System.exit(0);
        }

    }

}
