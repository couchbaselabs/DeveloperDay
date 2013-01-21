package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.*;
import com.google.gson.Gson;
import net.spy.memcached.PersistTo;
import net.spy.memcached.ReplicateTo;
import net.spy.memcached.internal.OperationFuture;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class Ex09View {

    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase - Views");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));
        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "beer-sample", "");

            Gson json = new Gson();

            DesignDocument designDocument = cb.getDesignDocument("beer");
            List<ViewDesign> views = designDocument.getViews();

            for (ViewDesign view : views) {
                System.out.println(view.getName());
                System.out.println("\t" + view.getMap());
            }


            // create a new Document and view
            DesignDocument breweryView = new DesignDocument("brewery");

            String mapFunction = "function (doc, meta) {\n" +
                    "  if (doc.type == \"brewery\") {\n" +
                    "  \temit(doc.name);\n" +
                    "  }\n" +
                    "}";

            ViewDesign viewDesign = new ViewDesign("by_name", mapFunction);
            breweryView.setView(viewDesign);

            cb.createDesignDoc(breweryView);


            {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Breweries (by_name)");
                View view = cb.getView("brewery", "by_name");
                Query query = new Query();
                query.setLimit(10);

                ViewResponse viewResponse = cb.query(view, query);
                for (ViewRow row : viewResponse) {
                    System.out.println(row.getKey());
                }
                System.out.println("");
            }



            {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Breweries (by_name) with docs");
                View view = cb.getView("brewery", "by_name");
                Query query = new Query();
                query.setLimit(10);
                query.setIncludeDocs(true);

                ViewResponse viewResponse = cb.query(view, query);
                for (ViewRow row : viewResponse) {
                    System.out.println(row.getKey() + " | "+ row.getDocument());
                }
                System.out.println("");
            }


            {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Breweries (by_name) with docs & JSON parsing");
                View view = cb.getView("brewery", "by_name");
                Query query = new Query();
                query.setLimit(10);
                query.setIncludeDocs(true);

                ViewResponse viewResponse = cb.query(view, query);
                for (ViewRow row : viewResponse) {
                    HashMap<String, String> parsedDoc = json.fromJson((String)row.getDocument(), HashMap.class);
                    System.out.println(row.getKey() + " : "+  parsedDoc.get("country") );
                }
                System.out.println("");
            }




            {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Breweries (by_name) starting with letter y or Y");
                View view = cb.getView("brewery", "by_name");
                Query query = new Query();
                query.setRangeStart("y");
                query.setRangeEnd("z");
                query.setIncludeDocs(true);

                ViewResponse viewResponse = cb.query(view, query);
                for (ViewRow row : viewResponse) {
                    HashMap<String, String> parsedDoc = json.fromJson((String)row.getDocument(), HashMap.class);
                    System.out.println(row.getKey() + " : "+  parsedDoc.get("country") );
                }
                System.out.println("");
            }

            {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Breweries (by_name) starting with letter y only, no Y (results should be empty)");
                View view = cb.getView("brewery", "by_name");
                Query query = new Query();
                query.setRangeStart("y");
                query.setRangeEnd("Y");
                query.setIncludeDocs(true);

                ViewResponse viewResponse = cb.query(view, query);
                for (ViewRow row : viewResponse) {
                    HashMap<String, String> parsedDoc = json.fromJson((String)row.getDocument(), HashMap.class);
                    System.out.println(row.getKey() + " : "+  parsedDoc.get("country") );
                }
                System.out.println("");
            }

            System.out.println("\n\n");

            cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
        }

    }


}
