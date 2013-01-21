#!/usr/bin/env ruby

require "couchbase"

system 'clear'
puts "--------------------------------------------------------------------------"
puts "## Couchbase Retrieval Operations"
puts "--------------------------------------------------------------------------"
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
doc = cb.get(user_data1[:email])
puts "Retrieve Doc and Inspect"
puts
puts doc.inspect
puts
puts

# retrieve extended info
doc, flags, cas = cb.get(user_data1[:email], :extended => true)
puts "Retrieve Doc Extended Info and Inspect"
puts
puts doc.inspect
puts "cas = #{cas}"
puts
puts

puts "Update doc and look at cas"
puts

# update some info in doc
doc["logins"] += 1

# replace the doc in couchbase
cb.replace(doc["email"], doc)

# retrieve and re-output
doc, flags, cas = cb.get(user_data1[:email], :extended => true)
puts doc.inspect
puts "cas = #{cas}"
puts

puts
puts "--------------------------------------------------------------------------"
puts