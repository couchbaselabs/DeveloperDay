#!/usr/bin/env ruby

require "rubygems"
require "couchbase"
require "rainbow"
require "pp"

system 'clear'
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Couchbase Observe"
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts

# establish connection, all different ways
cb = Couchbase.connect

user_data1 = {
  :doctype => "learn",
  :username => "jsmith",
  :name => "John Smith",
  :email => "jsmith@email.com",
  :password => "p4ssw0rd",
  :logins => 0
}

puts "Set Document with Observe, Returns when Persisted...".bright
puts
# observe that the document has persisted to disk on at least one node (can't exceed the number of nodes)
cb.set("mydoc", user_data1, :observe => { :persisted => 1 })

puts "Set Document with Observe Asynchronously, Returns when Persisted, and output return values".bright
puts
# same operation done asyncronously
cb.run do 
  cb.set("mydoc", user_data1, :observe => { :persisted => 1 }) do |rv|
    puts rv.operation
    puts rv.success?
    puts rv.key
    puts rv.cas
  end 
end
puts

# sample of call for persisted to 2 nodes and replicated to 1 other node (won't work on single node local installs)
#cb.set("mydoc", user_data1, :observe => { :persisted => 2, :replicated => 1 })

puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Couchbase Straight Observe".bright.foreground(:red)

result = cb.observe("mydoc")
puts "key = " + result[0].key
puts "status = " + result[0].status.to_s
puts "cas = " + result[0].cas.to_s


puts
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts