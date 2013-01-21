#!/usr/bin/env ruby

require "couchbase"

system 'clear'
puts "--------------------------------------------------------------------------"
puts "Couchbase Connections"
puts

# establish connection, all different ways
cb = Couchbase.connect
beers = Couchbase.connect(:bucket => "beer-sample")

cb = Couchbase.connect("http://localhost:8091/")

# this will fail unless you set a SASL password
begin
  cb = Couchbase.connect({
    :hostname => "127.0.0.1",
    :port => 8091,
    :bucket => "default",
    :password => "password"  
  })
rescue
  
end

cb = Couchbase.bucket
puts "First Connection"
puts cb.inspect
puts

puts "Beer Bucket"
puts beers.inspect


# add nodelist connections
# too many connection objects
# round robin connection objects


puts
puts "--------------------------------------------------------------------------"
puts