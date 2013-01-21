#!/usr/bin/env ruby

require "rubygems"
require "couchbase"
require "rainbow"
require "pp"

system 'clear'
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Couchbase Storage Operations".bright.foreground(:red)
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts

# establish connection
cb = Couchbase.connect


puts "Set a Key-Value and Get the Key-Value".bright
puts
# create a key
cb.set("mytest", "my value")
puts "cb.get(\"mytest\")" + " => ".bright + "\"" + cb.get("mytest") + "\""
puts




puts "Try to Add the same key, raises exception".bright
puts
# this raises key exists exception
begin
  cb.add("mytest", "my value added")
rescue Couchbase::Error::KeyExists
  puts "Couchbase::Error::KeyExists exception raised"
end
puts




puts "Replace the key-value with Replace".bright
puts
# success
cb.replace("mytest", "my value replaced")
puts cb.get("mytest")
puts


puts "Try to replace non-existent key, raises exception".bright
puts
# this raises exception (doesn’t exist)
begin
  cb.replace("mytest4", "my value4 replaced") 
rescue Couchbase::Error::NotFound
  puts "Couchbase::Error::NotFound exception raised"
end
puts


puts "Add another Key-Value pair".bright
puts
# success
cb.add("mytest3", "my value3") 
puts "cb.get(\"mytest3\")" + " => ".bright + "\"" + cb.get("mytest3") + "\""
puts




puts "Delete both stored keys".bright
puts
cb.delete("mytest")
cb.delete("mytest3")



puts "Retrieve deleted keys".bright
puts
# both of these return nil class, pass in quiet to return nil
puts cb.get("mytest", :quiet => true ).class.to_s

# set the connection to always use quiet
cb.quiet = true 
puts "cb.get(\"mytest\") = " + " => ".bright + cb.get("mytest").class.to_s
puts



puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Adding Expirations".bright.foreground(:red)
puts

puts "Set TTL for 3 seconds".bright
cb.set("mytest", "my value", :ttl => 3)
puts

puts "Immediate Get".bright
puts "cb.get(\"mytest\")"+ " => ".bright + "\"" + cb.get("mytest") + "\""
puts

puts "Sleeping for 4 seconds...".bright.foreground(:blue)
sleep(4)
puts

puts "Get Key again".bright
puts cb.get("mytest").class.to_s

puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Adding Expiration and Updating Expirations on Get".bright.foreground(:red)
puts

puts "Set TTL for 3 seconds".bright
cb.set("mytest", "my value", :ttl => 3)
puts

puts "Hit CTRL-C to end this loop of resetting TTL".bright.foreground(:blue)
puts

loop do 
  puts "cb.get(\"mytest\", :ttl => 3)" + " => ".bright + "\"" + cb.get("mytest", :ttl => 3) + "\""
  puts
  puts "Sleeping for 2 seconds...".bright
  puts
  sleep(2)
end

puts
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts