package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Ex02Storage {


    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase Storage Operations");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "default", "");


            {
                System.out.println("Set a Key-Value and Get the Key-Value");
                cb.set("mytest", 0, "my value");
                System.out.println("cb.get(\"mytest\")" + " => " + cb.get("mytest") + "\"");
                System.out.println("");
            }


            {
                System.out.println("Try to Add the same key");
                OperationFuture op = cb.add("mytest", 0, "my value added");
                if (op.getStatus().isSuccess()) {
                    System.out.println("Document saved.. should not be possible");
                } else {
                    System.out.println("Message " + op.getStatus().getMessage());

                }
                System.out.println("");
            }


            {
                System.out.println("Replace the key-value with Replace");
                cb.replace("mytest", 0, "my value replaced");
                System.out.println("New value : " + cb.get("mytest"));
                System.out.println("");
            }


            {
                System.out.println("Try to replace non-existent key, raises exception");
                OperationFuture op = cb.replace("mytest4", 0, "my value4 replaced");
                if (op.getStatus().isSuccess()) {
                    System.out.println("Document saved.. should not be possible");
                } else {
                    System.out.println("Message " + op.getStatus() );

                }

                System.out.println("");
            }


            {
                System.out.println("Add another Key-Value pair");
                cb.add("mytest3",0 ,"my value3");
                System.out.println("cb.get(\"mytest3\")" + " => " + cb.get("mytest3") );

                System.out.println("");

            }


            {
                System.out.println("Delete Both Store Key");
                cb.delete("mytest");
                cb.delete("mytest3");
                System.out.println("");

            }


            {
                System.out.println("Delete Both Store Key");
                Object o = cb.get("mytest");
                System.out.println("Get deleted key :"+ o );

                GetFuture op = cb.asyncGet("mytest");
                if (op.getStatus().isSuccess()) {
                    System.out.println("Document saved.. should not be possible");
                } else {
                    System.out.println("Message " + op.getStatus() );

                }

                System.out.println("");

            }


            System.out.println("\n\n\n\n");

            cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
        }

    }


}
