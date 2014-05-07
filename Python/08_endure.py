#!/usr/bin/env python
from couchbase import Couchbase
from couchbase.exceptions import CouchbaseError

cb = Couchbase.connect(bucket='default')

print "Set document with persisting to a single node"
doc = { "message" : "hello world!" }
rv = cb.set('foo', doc, persist_to=1)

print "Set document with replicating to a node (may fail)"
print "on clusters without replicas"
try:
    cb.set('foo', doc, replicate_to=1)
except CouchbaseError as e:
    print e


print "Get statistics about persistent/replication"
print cb.observe('foo')
