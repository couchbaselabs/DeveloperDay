#!/usr/bin/env ruby

require "couchbase"

system 'clear'
puts "--------------------------------------------------------------------------"
puts "## Couchbase Atomic Counters"
puts "--------------------------------------------------------------------------"
puts


# establish connection, all different ways
cb = Couchbase.connect

cb.set("counter", 0)

# increase by 1
cb.incr("counter")

# returns 1
puts cb.get("counter")

# delete it
cb.delete("counter")

# creates and sets to initial (doesn't increase)
cb.incr("counter", :initial => 1)

# returns 1
puts cb.get("counter")

# since it exists, adds 1 to counter
cb.incr("counter", :initial => 1)

# returns 1
puts cb.get("counter")

# doesn't decrease below 0
cb.set("counter", 0)
cb.decr("counter")

# returns 0
puts cb.get("counter")

# sets to max value
cb.set("counter", -1)
cb.decr("counter")
cb.incr("counter")

# returns 18446744073709551615
puts cb.get("counter")

# add one to max
cb.incr("counter")

# returns 0
puts cb.get("counter")

cb.delete("counter")

puts
puts "--------------------------------------------------------------------------"
puts