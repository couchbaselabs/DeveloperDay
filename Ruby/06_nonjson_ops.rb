#!/usr/bin/env ruby

require "rubygems"
require "couchbase"
require "rainbow"
require "pp"

system 'clear'
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "## Couchbase Non-JSON Ops".bright.foreground(:red)
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts

cb = Couchbase.connect

puts "Store Strings, Floats, Integers".bright.foreground(:red)
puts

cb.set("store_strings", "The quick brown fox jumped over the lazy dog.", :ttl => 10)
cb.set("store_floats", 3.14159265358979, :ttl => 10)
cb.set("store_integers", -42, :ttl => 10)

puts "cb.get(\"store_strings\")" + " => ".bright + "\"" + cb.get("store_strings") + "\""
puts cb.get("store_strings").class.to_s
puts

puts "cb.get(\"store_floats\")" + " => ".bright + cb.get("store_floats").to_s
puts cb.get("store_floats").class.to_s
puts

puts "cb.get(\"store_integers\")" + " => ".bright + cb.get("store_integers").to_s
puts cb.get("store_integers").class.to_s
puts

puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Append/Prepend Ops".bright.foreground(:red)
puts

puts "Create a list, start with one item".bright
puts

cb.set("mylist", "oranges", :format => :plain, :ttl => 30)
puts "cb.get(\"mylist\")" + " => ".bright + cb.get("mylist").to_s
puts

puts "Add an item...\n\n"
cb.prepend("mylist", "apples,", :format => :plain)
puts "cb.get(\"mylist\")" + " => ".bright + cb.get("mylist").to_s
puts

puts "Add an item...\n\n"
cb.append("mylist", ",bananas", :format => :plain)
puts "cb.get(\"mylist\")" + " => ".bright + cb.get("mylist").to_s
puts

puts "Add an item...\n\n"
cb.append("mylist", ",lemons", :format => :plain)
puts "cb.get(\"mylist\")" + " => ".bright + cb.get("mylist").to_s
puts

puts "Add an item...\n\n"
cb.append("mylist", ",grapes", :format => :plain)
puts "cb.get(\"mylist\")" + " => ".bright + cb.get("mylist").to_s
puts


puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts "Store Binary Blobs".bright.foreground(:red)
puts

# delete output if it exists
if File.file?("./output.jpg")
  File.delete("./output.jpg")
end

source = "../../Binary/lolcat.jpg"
binary_img = nil

puts "Read jpg from disk".bright
puts
# read from disk
File.open(source, "r") {|f|
  binary_img = f.read
}
# alternate way to read binary files
# binary_img = File.open(source,"rb") {|io| io.read}

puts "Store file in Couchbase, :format => :plain is important to set for binary".bright
puts
cb.set("img", binary_img, :ttl => 10, :format => :plain)


puts "Retrieve from Couchbase".bright
puts
img_cb = cb.get("img")


puts "Save image from Couchbase to disk (checkout output.jpg)".bright
f = File.new("./output.jpg", "w")
f.write(img_cb)
f.close

puts
puts "--------------------------------------------------------------------------".bright.foreground(:red)
puts