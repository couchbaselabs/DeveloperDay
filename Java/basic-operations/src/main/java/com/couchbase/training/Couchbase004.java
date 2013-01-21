package com.couchbase.training;


import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.*;
import com.google.gson.Gson;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


class BeerDTO
{
    String id;
    String type;
    String breweryId;
    String name;
    float abv;
}


public class Couchbase004 {

    public static void main(String[] args) throws Exception {

        try {

            URI local = new URI("http://localhost:8091/pools");
            List<URI> baseURIs = new ArrayList<URI>();
            baseURIs.add(local);

            CouchbaseClient client = new CouchbaseClient(baseURIs, "beer-sample", "");
            View view = client.getView("beer", "by_name");

            if (view == null) {
                System.err.println("View is null");
                System.exit(0);
            }

            Query query = new Query();
            query.setReduce(false).setLimit(9);
            query.setIncludeDocs(true);
            query.setStale(Stale.FALSE);
            query.setDescending(true);

            ViewResponse result = client.query(view, query);
            Iterator<ViewRow> itr = result.iterator();

            ViewRow row;
            while (itr.hasNext()) {
                row = itr.next();
                BeerDTO beer = new Gson().fromJson( row.getDocument().toString() , BeerDTO.class);

                System.out.println( beer.name );

            }

            client.shutdown(3, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        System.exit(0);
    }



}

