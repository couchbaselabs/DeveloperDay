package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import net.spy.memcached.CASValue;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


class UserData {
    String doctype;
    String username;
    String name;
    String email;
    String password;
    int logins = 0;
}

public class Ex04Retrieve {


    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase Retrieve Operations");
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

            UserData user2 = new UserData();
            user2.doctype = "learn";
            user2.username = "xsmith";
            user2.name = "Xavier Smith";
            user2.email = "xsmith@email.com";
            user2.password = "p4ssw0rd";
            user2.logins = 0;

            //set the doc
            cb.set( user1.email, 0 ,  json.toJson( user1 ) );
            cb.set( user2.email, 0 ,  json.toJson( user2 ) );


            {
                System.out.println("Retrieve the data");
                Object doc = cb.get( user1.email );
                System.out.println( doc );
                System.out.println("");
            }



            {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Retrieve the data async with CAS");
                UserData testUser = null;
                CASValue casValue = cb.gets(user1.email);
                System.out.println("Value : "+ casValue.getValue() );
                System.out.println("CAS : "+ casValue.getCas() );
                testUser = json.fromJson( casValue.getValue().toString(), UserData.class ) ;

                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Update doc and look at cas");
                testUser.logins += 1;

                cb.replace(testUser.email, 0, json.toJson(testUser));

                CASValue casValue2 = cb.gets(user1.email);
                System.out.println("New Cas Value :"+ casValue2.getCas());

                System.out.println("");
            }



            {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Create 9 Keys and do Multi-Get");

                List<String> keys = new ArrayList();
                for (int i = 1 ; i<10 ; i++) {
                    // put a gap in the list to show missing item
                    if ( i != 3 ) {
                        cb.set("key-"+ i , 30, "{ \"doctype\" : \"learn\", \"value\" : "+ i +"}");
                    }
                    keys.add( "key-"+ i );
                }

                Map<String, Object> values = cb.getBulk(keys);
                System.out.println( values );
                System.out.println("");

            }


            System.out.println("\n\n");

            Thread.sleep(500);
            cb.shutdown(5, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
            System.exit(0);
        }

    }
}
