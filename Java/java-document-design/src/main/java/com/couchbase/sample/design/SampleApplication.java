/**
 * Copyright (C) 2009-2012 Tugdual Grall.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */
package com.couchbase.sample.design;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.*;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;


public class SampleApplication {

    public static void main(String args[]) {


        System.setProperty("viewmode", "development");


        List<URI> uris = new LinkedList<URI>();
        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient client = null;
        try {
            client = new CouchbaseClient(uris, "beer-sample", "");

            client.delete()

            DesignDocument designDoc = new DesignDocument("dev_beer");

            String viewName = "by_name";
            String mapFunction =
                    "function (doc, meta) {\n" +
                    "  if(doc.type && doc.type == \"beer\") {\n" +
                    "    emit(doc.name, null);\n" +
                    "  }\n" +
                    "}";

            ViewDesign viewDesign = new ViewDesign(viewName,mapFunction);

            designDoc.getViews().add(viewDesign);
            client.createDesignDoc( designDoc );

            View view = client.getView("beer", "by_name");
            Query query = new Query();
            query.setIncludeDocs(true).setLimit(20);
            query.setStale( Stale.FALSE );
            ViewResponse result = client.query(view, query);


            for(ViewRow row : result) {
                System.out.println( row.getDocument() );
            }


                client.shutdown();


        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
            System.exit(0);
        }


    }


}
