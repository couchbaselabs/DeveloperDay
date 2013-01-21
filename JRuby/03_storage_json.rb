#!/usr/bin/env ruby

require "couchbase"
require "json"

system 'clear'
puts "--------------------------------------------------------------------------"
puts "Couchbase JSON Doc Storage"
puts

# establish connection
cb = Couchbase.connect

mydoc = {
	 :doctype => "test",
   :name => "John Smith"
}

puts "Set Document (Hash -> JSON)"
puts

# store a json doc (encode it)
cb.set("mydoc", mydoc)

puts "Retrieve Document (JSON -> Hash)"
puts

# retrieve and decode json doc
doc = cb.get("mydoc")
puts "doc = " + doc.inspect
puts "doc[\"name\"] = " + doc["name"]

# delete the test docs to repeat tests 
cb.delete("mydoc")

puts
puts "--------------------------------------------------------------------------"
puts