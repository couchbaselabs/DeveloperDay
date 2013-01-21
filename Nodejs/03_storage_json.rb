#!/usr/bin/env ruby

require "couchbase"
require "json"

# establish connection
cb = Couchbase.connect

mydoc = {
	 :doctype => "test",
   :name => "John Smith"
}

# store a json doc (encode it)
cb.set("mydoc", mydoc)

# retrieve and decode json doc
doc = cb.get("mydoc")
puts doc["name"]

# delete the test docs to repeat tests 
cb.delete("mydoc")
