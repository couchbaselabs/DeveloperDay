#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import inspect

from couchbase.client import Couchbase

from blessings import Terminal

import secret

t = Terminal()

os.system('clear')
print t.bold_red("--------------------------------------------------------------------------")
print t.bold_red("Couchbase Connections")
print t.bold_red("--------------------------------------------------------------------------")
print


# establish connection
couchbase = Couchbase(secret.HOSTPORT,
    username=secret.USERNAME, password=secret.PASSWORD)

# set bucket object
cb = couchbase["default"]

print couchbase.servers

# establish connection to beer-sample
couchbase = Couchbase(secret.HOSTPORT,
    username=secret.USERNAME, password=secret.PASSWORD)

# set bucket instance
beers = couchbase["beer-sample"]

print couchbase.servers
print
