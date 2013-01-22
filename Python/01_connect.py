#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os 
import inspect
from couchbase.client import Couchbase
from blessings import Terminal

t = Terminal()

os.system('clear')
print t.bold_red("--------------------------------------------------------------------------")
print t.bold_red("Couchbase Connections")
print t.bold_red("--------------------------------------------------------------------------")
print


# establish connection
couchbase = Couchbase("127.0.0.1:8091", "default", "")

# set bucket object
cb = couchbase["default"]

print couchbase.servers

# establish connection to beer-sample
couchbase = Couchbase("127.0.0.1:8091", "beer-sample", "")

# set bucket instance
beers = couchbase["beer-sample"]

print couchbase.servers
print