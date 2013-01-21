#!/usr/bin/env python
# -*- coding: utf-8 -*-

from couchbase.client import Couchbase
import json
import hashlib

# establish connection
couchbase = Couchbase("127.0.0.1:8091", "default", "")

# connect to default bucket
cb = couchbase["default"]

mydoc = \
{
	 "doctype": "test",
   "name": "John Smith"
}

# store a json doc (encode it)
cb.set("mydoc", 0, 0, json.dumps(mydoc))

# retrieve and decode json doc
doc = json.loads(cb.get("mydoc")[2])
print doc["name"]

# delete the test docs to repeat tests 
cb.delete("mydoc")
