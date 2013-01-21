#!/usr/bin/env ruby

require "couchbase"

# establish connection to beers bucket
cb = Couchbase.connect(:bucket => "beer-sample")

ddocs = cb.design_docs

# list out each design doc in the bucket
ddocs.each do |dd|
  puts dd[0].to_s
end