#!/usr/bin/env python
# -*- coding" utf-8 -*-

import os
import json

from couchbase.client import Couchbase
from blessings import Terminal

import secret

# establish connection
couchbase = Couchbase(secret.HOSTPORT,
    username=secret.USERNAME, password=secret.PASSWORD)

# connect to default bucket
cb = couchbase["default"]


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
cb.set(user_data1["email"], 0, 0, json.dumps(user_data1))
cb.set(user_data2["email"], 0, 0, json.dumps(user_data2))


# retrieve the document and output
print t.bold("Retrieve Doc and Inspect CAS")
print
kv = cb.get(user_data1["email"])
print kv
print "cas = " + str(kv[1])
print

print t.bold_red("--------------------------------------------------------------------------")
print t.bold_red("Update doc and look at cas")
print

doc = parse_json(kv)
doc["logins"] += 1
cb.replace(doc["email"], 0, 0, json.dumps(doc))

kv = cb.get(user_data1["email"])
print kv
print "cas = " + str(kv[1])
print


# Python module doesn't currently have working support for multi-get
#
#print t.bold_red("--------------------------------------------------------------------------")
#print t.bold_red("Create 9 Keys and do Multi-Get")
#print

#key_array = []

#for i in range(1,10):

#  key = "key-" + str(i)
#  doc = { "doctype":"learn", "value": i}

  # put a gap in the list to show missing item
#  if i != 3:
#    cb.set(key, 30, 0, json.dumps(doc))

#  key_array.append(key)


#print t.bold("Values as Array (default)")
#print

#kv = cb.getMulti(key_array)
#print kv
#print


print t.bold_red("--------------------------------------------------------------------------")
