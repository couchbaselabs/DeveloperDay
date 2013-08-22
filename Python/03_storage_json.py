#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os 
from couchbase import Couchbase
from blessings import Terminal
from pprint import pprint
import json
import hashlib

t = Terminal()

os.system('clear')
print t.bold_red("--------------------------------------------------------------------------")
print t.bold_red("Couchbase JSON Document Storage Operations")
print t.bold_red("--------------------------------------------------------------------------")
print

# connect to the default bucket
cb = Couchbase.connect(bucket='default')

# Create a new dictionary
mydoc = \
{
   "doctype": "test",
   "name": "John Smith"
}

print t.bold("Set a Document")
print

# store a json doc (automatically converted from dictionary to JSON)
print cb.set("mydoc", mydoc)
print

print t.bold("Get a Document")
print

# retrieve, get type of value, and attempt parse of json if string
val = cb.get("mydoc")

print t.bold("Pretty print a document")
print

# pretty print the document
pprint(val.value, indent=4)
print

print t.bold("Print the document as JSON")
print

print json.dumps(val.value)
print

print t.bold("Delete the document")
print

# delete the test docs to repeat tests 
cb.delete("mydoc")

print
print t.bold_red("--------------------------------------------------------------------------")
print
