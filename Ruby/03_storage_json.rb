#!/usr/bin/env ruby

require "rubygems"
require "couchbase"
require "json"

require "rainbow"
require "pp"


system 'clear'
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Couchbase JSON Doc Storage".bright.foreground(:red)
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts

# establish connection
cb = Couchbase.connect

mydoc = {
	 :doctype => "test",
   :name => "John Smith"
}

puts "Set Document (Hash -> JSON)".bright
puts

# store a json doc (encode it)
cb.set("mydoc", mydoc)

puts "Retrieve Document (JSON -> Hash), also set a 10 second TTL".bright
puts

# retrieve and decode json doc
doc = cb.get("mydoc", :ttl => 10)
pp doc
puts "doc[\"name\"] = " + doc["name"]

puts
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts