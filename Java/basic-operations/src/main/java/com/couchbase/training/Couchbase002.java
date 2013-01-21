package com.couchbase.training;

import com.couchbase.client.CouchbaseClient;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Couchbase002 {

    public static void main(String args[]) {
        CouchbaseClient client = connectToCouchbase();

        basicOperationWithCheck(client);

        client.shutdown(3, TimeUnit.SECONDS);
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


    private static void basicOperationWithCheck(CouchbaseClient client) {

        System.out.println("--- --- Basic Operations With Check --- ---");


        String myKey = "KEY-001";
        String value = "Another very interesting value";

        OperationFuture<Boolean> setOperation = client.set(myKey, 0, value);


        try {
            if (setOperation.get().booleanValue()) {
                System.out.println("Set Succeeded");
            } else {
                System.err.println("Set failed: " + setOperation.getStatus().getMessage());
            }
        } catch (Exception e) {
            System.err.println("Exception while doing set: " + e.getMessage());
        }


        System.out.println("--- --- --- --- --- --- ---\n\n");



        // Do an asynchronous get
        GetFuture getOperation = client.asyncGet(myKey);
        Object getObject = null;
        try {
            if ((getObject = getOperation.get()) != null) {
                System.out.println("Asynchronous Get Succeeded: " + getObject);
            } else {
                System.err.println("Asynchronous Get failed: " + getOperation.getStatus().getMessage());
            }
        } catch (Exception e) {
            System.err.println("Exception while doing get: " + e.getMessage());
        }

        System.out.println("--- --- --- --- --- --- ---\n\n");



        OperationFuture<Boolean> delOperation = client.delete(myKey);

        try {
            if (delOperation.get().booleanValue()) {
                System.out.println("Delete Succeeded");
            } else {
                System.err.println("Delete failed: " + delOperation.getStatus().getMessage());
            }
        } catch (Exception e) {
            System.err.println("Exception while doing delete: " + e.getMessage());
        }
        System.out.println("--- --- --- --- --- --- ---\n\n");



    }

}