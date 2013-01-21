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

# observe that the document has persisted to disk on at least one node (can't exceed the number of nodes)
cb.set("mydoc", user_data1, :observe => { :persisted => 1 })

# same operation done asyncronously
cb.run do 
  cb.set("mydoc", user_data1, :observe => { :persisted => 1 }) do |rv|
    puts rv.operation
    puts rv.success?
    puts rv.key
    puts rv.cas
  end 
end

# sample of call for persisted to 2 nodes and replicated to 1 other node (won't work on single node local installs)
#cb.set("mydoc", user_data1, :observe => { :persisted => 2, :replicated => 1 })