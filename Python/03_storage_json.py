#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os 
from couchbase.client import Couchbase
from blessings import Terminal
import json
import hashlib

t = Terminal()

os.system('clear')
print t.bold_red("--------------------------------------------------------------------------")
print t.bold_red("Couchbase JSON Document Storage Operations")
print t.bold_red("--------------------------------------------------------------------------")
print

# establish connection
couchbase = Couchbase("127.0.0.1:8091", "default", "")

# connect to default bucket
cb = couchbase["default"]

mydoc = \
{
   "doctype": "test",
   "name": "John Smith"
}




print t.bold("Set a Document with json.dumps(hash)")
print

# store a json doc (encode it)
cb.set("mydoc", 0, 0, json.dumps(mydoc))




print t.bold("Retrieve document with json.loads(doc)")
print



# retrieve, get type of value, and attempt parse of json if string
val = cb.get("mydoc")
vtype = type(val[2])

print "Type of the value retrieved: " + str(vtype)
print

if vtype is str:
	print t.bold("Attempt parse of string as JSON...")
	print
	try:
		doc = json.loads(val[2])
		print json.dumps(doc)
		print
		print "doc[\"name\"] = " + str(doc["name"])
	except:
		print "JSON parse failed, must not be a JSON document"
	


# delete the test docs to repeat tests 
cb.delete("mydoc")

print
print t.bold_red("--------------------------------------------------------------------------")
print