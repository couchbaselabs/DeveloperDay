#!/usr/bin/env ruby

require "rubygems"
require "couchbase"
require "rainbow"
require "pp"

system 'clear'
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "## Couchbase Atomic Counters".bright.foreground(:red)
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts


# establish connection, all different ways
cb = Couchbase.connect

puts "Set Counter to 0".bright
puts


cb.set("counter", 0)
puts "cb.get(\"counter\")" + " => ".bright + cb.get("counter").to_s
puts


puts "incr by 1".bright
puts
# increase by 1
cb.incr("counter")
puts "cb.get(\"counter\")" + " => ".bright + cb.get("counter").to_s
puts


puts "incr by 10".bright
puts
cb.incr("counter", 10)
puts "cb.get(\"counter\")" + " => ".bright + cb.get("counter").to_s
puts


# delete it
cb.delete("counter")

puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Using incr + initial values for counters ".bright.foreground(:red)
puts


puts "incr with initial value, created since it doesn't exist".bright
puts
# creates and sets to initial (doesn't increase)
cb.incr("counter", :initial => 1)
puts "cb.get(\"counter\")" + " => ".bright + cb.get("counter").to_s
puts


puts "incr with initial value again, initial ignored since it exists".bright
puts
# since it exists, adds 1 to counter
cb.incr("counter", :initial => 1)
puts "cb.get(\"counter\")" + " => ".bright + cb.get("counter").to_s
puts


puts "decr counter doesn't go below 0".bright
puts
# doesn't decrease below 0
cb.decr("counter", 1000)
puts "cb.get(\"counter\")" + " => ".bright + cb.get("counter").to_s
puts

puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "set counter to -1, decr, then incr sets to max value".bright.foreground(:red)
puts

# sets to max value
cb.set("counter", -1)
cb.decr("counter")
cb.incr("counter")
# returns 18446744073709551615
puts "cb.get(\"counter\")" + " => ".bright + cb.get("counter").to_s
puts



puts "incr on max value returns it to 0".bright
puts
# add one to max
cb.incr("counter")
puts "cb.get(\"counter\")" + " => ".bright + cb.get("counter").to_s
puts


cb.delete("counter")

puts
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts