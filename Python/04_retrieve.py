#!/usr/bin/env python
# -*- coding" utf-8 -*-

import os 
from couchbase import Couchbase
from blessings import Terminal
import json
import hashlib

# establish connection
cb = Couchbase.connect(bucket="default")

def parse_json( val ):
	vtype = type(val[2])
	if vtype is str:
		try:
			json_doc = json.loads(val[2])
			return json_doc
		except:
			print
			return val[2]
	
t = Terminal()

os.system('clear')
print t.bold_red("--------------------------------------------------------------------------")
print t.bold_red("Couchbase JSON Document Retrieve Operations")
print t.bold_red("--------------------------------------------------------------------------")
print



user_data1 = \
{
  "doctype": "learn",
  "username": "jsmith",
  "name": "John Smith",
  "email": "jsmith@email.com",
  "password": "p4ssw0rd",
  "logins": 0
}

user_data2 = \
{
  "doctype": "learn",
  "username": "xsmith",
  "name": "Xavier Smith",
  "email": "xsmith@email.com",
  "password": "p4ssw0rd",
  "logins": 0
}

print t.bold("Set 2 User Docs")
print

# initialize the documents
cb.set(user_data1["email"], user_data1)
cb.set(user_data2["email"], user_data2)


# retrieve the document and output
print t.bold("Retrieve Doc and Inspect CAS")
print
kv = cb.get(user_data1["email"])
print kv
print "cas = " + str(kv.cas)

print t.bold_red("--------------------------------------------------------------------------")
print t.bold_red("Update doc and look at cas")
print

kv.value["logins"] += 1
cb.replace(kv.value["email"], kv.value)

kv = cb.get(user_data1["email"])
print kv
print "cas = " + str(kv.cas)

print t.bold_red("--------------------------------------------------------------------------")
