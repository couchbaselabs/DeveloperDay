#!/usr/bin/env ruby

require "couchbase"

system 'clear'
puts "--------------------------------------------------------------------------"
puts "Couchbase Storage Operations"
puts

# establish connection
cb = Couchbase.connect

# create a key
cb.set("mytest", "my value")
puts "cb.get('mytest') = " + cb.get("mytest").to_s

# this raises key exists exception, uncomment to see error
#cb.add("mytest", "my value added")

# success
cb.replace("mytest", "my value replaced")
puts cb.get("mytest")

# this raises exception (doesn’t exist)
#cb.replace("mytest4", "my value4 replaced") 

# success
cb.add("mytest3", "my value3") 
puts "cb.get('mytest3') = " + cb.get("mytest3")
puts

puts "Delete both stored keys"
puts

cb.delete("mytest")
cb.delete("mytest3")

puts "Retrieve deleted keys"
puts

# both of these return nil class
puts cb.get("mytest", :quiet => true ).class.to_s

cb.quiet = true

puts "cb.get('mytest') = " + cb.get("mytest").class.to_s
puts

puts "--------------------------------------------------------------------------"
puts "Adding Expirations"
puts

puts "Set TTL for 3 seconds"
cb.set("mytest", "my value", :ttl => 3)
puts

puts "Immediate Get"
puts "cb.get('mytest') = " + cb.get("mytest")
puts

puts "Sleeping for 4 seconds..."
sleep(4)
puts

puts "Get Key again"
puts cb.get("mytest").class.to_s

puts "--------------------------------------------------------------------------"
puts "Adding Expiration and Updating Expirations on Get"
puts

puts "Set TTL for 3 seconds"
cb.set("mytest", "my value", :ttl => 3)
puts

puts "Hit CTRL-C to end this loop of resetting TTL"
puts

loop do 
  puts "cb.get('mytest', :ttl => 3) = " + cb.get("mytest", :ttl => 3)
  puts "Sleeping for 2 seconds..."
  sleep(2)
end

puts
puts "--------------------------------------------------------------------------"
puts