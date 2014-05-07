#!/usr/bin/env python
import os.path
from couchbase import Couchbase, FMT_BYTES, FMT_PICKLE, FMT_UTF8

print "Setting strings, floats, and integers"
cb = Couchbase.connect(bucket='default')
cb.set("store_strings", "the quick brown fox over the lazy doc")
cb.set("store_floats", 3.141)
cb.set("store_integers", -42, )

print "Getting a string.. ", cb.get('store_strings').value
print "Getting a float.. ", cb.get('store_floats').value
print "Getting an integer.. ", cb.get('store_integers').value

print "Append/Prepend, using comma delimites"
cb.set("list", "oranges,", format=FMT_UTF8)
cb.append("list", "apples,", format=FMT_UTF8)
cb.prepend("list", "bananas,", format=FMT_UTF8)

print "Items are", cb.get('list').value.split(',')
print "Storing/Retrieving Binary data"
srcfile = os.path.dirname(__file__) + '/../Binary/lolcat.jpg'

blob = open(srcfile, 'r').read()
cb.set('img', blob, format=FMT_BYTES)
blob_in = cb.get('img').value

print "Set {0} bytes. Got {1} bytes".format(len(blob), len(blob_in))
assert blob == blob_in
