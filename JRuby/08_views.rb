#!/usr/bin/env ruby

require "couchbase"

system 'clear'
puts "--------------------------------------------------------------------------"
puts "Couchbase Views - List Design Documents and Views"
puts

# establish connection to beers bucket
cb = Couchbase.connect(:bucket => "beer-sample")

ddocs = cb.design_docs

# list out each design doc and views in the bucket
ddocs.each do |dd|
  puts "DDoc: " + dd[0].to_s
  this_dd = cb.design_docs[dd[0]]  
  puts "\t" + this_dd.views.inspect  
end
puts
puts

puts "--------------------------------------------------------------------------"
# Select a View and Query It, output the Document Keys 
puts "Breweries (by_name)"
puts
breweries = cb.design_docs["breweries"]
breweries.by_name(:limit => 10).each do |doc|
  puts doc.key
end
puts
puts

puts "--------------------------------------------------------------------------"
# Select a View and Query It, output the Document Keys 
puts "Breweries (by_name), include docs and output country"
puts
breweries = cb.design_docs["breweries"]
breweries.by_name(:limit => 10, :include_docs => true).each do |doc|
  puts doc.key
  puts "\t" + doc["country"]
end
puts
puts

puts "--------------------------------------------------------------------------"
# Select a View and Query It, output the Document Keys 
puts "Breweries (by_name) starting with letter y or Y"
puts
breweries = cb.design_docs["breweries"]
breweries.by_name(:startkey => "y", :endkey => "z").each do |doc|
  puts doc.key
end
puts

puts "--------------------------------------------------------------------------"
# Select a View and Query It, output the Document Keys 
puts "Breweries (by_name) starting with letter y only, no Y (results should be empty)"
puts
breweries = cb.design_docs["breweries"]
breweries.by_name(:startkey => "y", :endkey => "Y").each do |doc|
  puts doc.key
end
puts

puts
puts "--------------------------------------------------------------------------"
puts