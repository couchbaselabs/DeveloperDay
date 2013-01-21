#!/usr/bin/env ruby

require "rubygems"
require "couchbase"
require "rainbow"
require "pp"

system 'clear'
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Couchbase Connections".bright.foreground(:red)
puts "--------------------------------------------------------------------------".bright.foreground(:red)
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

# Recommended with clusters, assign a list of nodes to attempt connection with instead of a single node in case of node failure
cb = Couchbase.connect({
  :bucket => "default",
  :nodelist => ["localhost:8091", "localhost"], # since we have a single node, just using the same for both
  :quiet => true  # return nils instead of Exceptions for Couchbase::Error::KeyNotFound  
})

cb = Couchbase.bucket
puts "First Connection".bright
puts
pp cb
puts

puts "Beer Bucket".bright
puts
pp beers

puts
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts