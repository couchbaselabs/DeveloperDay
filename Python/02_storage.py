#!/usr/bin/env python
# -*- coding: utf-8 -*-

# sudo pip install blessings (for color terminal output)
import os 
from couchbase.client import Couchbase
from blessings import Terminal

t = Terminal()

os.system('clear')
print t.bold_red("--------------------------------------------------------------------------")
print t.bold_red("Couchbase Storage Operations")
print t.bold_red("--------------------------------------------------------------------------")
print

# establish connection
couchbase = Couchbase("127.0.0.1:8091", "default", "")

# connect to default bucket
cb = couchbase["default"]

print t.bold("Set a Key-Value")
print
# create a key-value pair, set("key", ttl, flags, value)
cb.set("mytest", 0, 0, 1)

print t.bold("Retrieve Key-Value")
print
print "cb.get(\"mytest\")[2] = " + str(cb.get("mytest")[2])
print



print t.bold("Add key that exists already")
print
try:
	# this raises key exists exception, uncomment to see error
	cb.add("mytest", 0, 0, 2)
except 	Exception as e:
	print 'Exception Raised:', e.args[0]#, type(e), e
	print


print t.bold("Replace key-value with a new one")
print
# success
cb.replace("mytest", 0, 0, 2)
print "cb.get(\"mytest\")[2] = " + str(cb.get("mytest")[2])
print


print t.bold("Try to replace doc that doesn't exist")
print
try:
	# this raises key exists exception, uncomment to see error
	cb.replace("mytest4", 0, 0, 4) 
except 	Exception as e:
	print 'Exception Raised:', e.args[0]
	print
	


print t.bold("Add another key-value pair")
print
# success
cb.add("mytest3", 0, 0, 3)
print "cb.get(\"mytest3\")[2] = " + str(cb.get("mytest3")[2])
print


cb.delete("mytest")
cb.delete("mytest3")
