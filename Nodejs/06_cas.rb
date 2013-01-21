require "couchbase"

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

puts
puts
puts

### ------------------------------------------------------------------------------------------
### Part 1 - Initialize and Retrieve CAS
### ------------------------------------------------------------------------------------------

puts "-------------------------------------"
puts "Set the Data, then Retrieve Two Copies of the Document"
puts 

# Initialize the documents
cb.set(user_data1[:email], user_data1)

# Retrieve two copies of the same document with the CAS
v1, flags1, cas1 = cb.get(user_data1[:email], :extended => true)
v2, flags2, cas2 = cb.get(user_data1[:email], :extended => true)

puts "Both v1 and v2 are identical and have identical CAS [(cas1) #{cas1} == #{cas2} (cas2)]"
puts


### ------------------------------------------------------------------------------------------
### Part 2 - Compare CAS
### ------------------------------------------------------------------------------------------

puts "-------------------------------------"
puts "Now We'll update the document which results in a new CAS"
puts

# add 1 to the logins count in document
v1["logins"] += 1

# Replace the store document with the new doc using the CAS, and retrieve the new cas if it succeeds
cas1 = cb.replace(user_data1[:email], v1, :cas => cas1)

# CAS has changed due to last update operation
puts "Now CAS has changed [(cas1) #{cas1} != #{cas2} (cas2)]"
puts



### ------------------------------------------------------------------------------------------
### Part 3 - Try another operation with original CAS
### ------------------------------------------------------------------------------------------

puts "-------------------------------------"
puts "If we try to update the document from the second copy, it will fail"
puts

# this should fail
begin
  cas2 = cb.replace(user_data1[:email], v2, :cas => cas2)
  puts "Success: Replace Operation succeeded (shouldn't display)"
rescue Couchbase::Error::KeyExists
  puts "ERROR: Replace Operation failed due to CAS mismatch"
  puts
end


puts
puts
puts