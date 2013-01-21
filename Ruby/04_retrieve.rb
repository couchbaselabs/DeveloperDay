#!/usr/bin/env ruby

require "rubygems"
require "couchbase"
require "rainbow"
require "pp"

system 'clear'
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "## Couchbase Retrieval Operations".bright.foreground(:red)
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts


# establish connection, all different ways
cb = Couchbase.connect
cb.quiet = true

user_data1 = {
  :doctype => "learn",
  :username => "jsmith",
  :name => "John Smith",
  :email => "jsmith@email.com",
  :password => "p4ssw0rd",
  :logins => 0
}

user_data2 = {
  :doctype => "learn",
  :username => "xsmith",
  :name => "Xavier Smith",
  :email => "xsmith@email.com",
  :password => "p4ssw0rd",
  :logins => 0
}

# initialize the documents
cb.set(user_data1[:email], user_data1)
cb.set(user_data2[:email], user_data2)

# retrieve the document and output
puts "Retrieve Doc and Inspect".bright.foreground(:red)
puts
doc = cb.get(user_data1[:email])
pp doc
puts
puts

# retrieve extended info
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Retrieve Doc Extended Info and Inspect".bright.foreground(:red)
puts
doc, flags, cas = cb.get(user_data1[:email], :extended => true)
pp doc
puts
puts "cas = #{cas}"
puts

puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Update doc and look at cas".bright.foreground(:red)
puts

# update some info in doc
doc["logins"] += 1

# replace the doc in couchbase
cb.replace(doc["email"], doc)

# retrieve and re-output
doc, flags, cas = cb.get(user_data1[:email], :extended => true)
pp doc
puts
puts "cas = #{cas}"
puts

puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Create 9 Keys and do Multi-Get".bright.foreground(:red)
puts

key_array = []

9.times do |i|
  # put a gap in the list to show missing item
  if i != 3
    cb.set("key-#{i}", { :doctype => "learn", :value => i}, :ttl => 30)
  end
  key_array << "key-#{i}"
end

puts "Values as Array (default)".bright
values = cb.get(key_array)
puts
pp values
puts
puts

puts "Values as Hash, with Document key as Hash key".bright
values = cb.get(key_array, :assemble_hash => true)
puts
pp values
puts

puts
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts