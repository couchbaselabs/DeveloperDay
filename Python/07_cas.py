#!/usr/bin/python
from couchbase import Couchbase
from couchbase.exceptions import CouchbaseError

cb = Couchbase.connect(bucket='default')

udata = {
    'doctype': 'learn',
    'username': 'jsmith',
    'name': 'John Smith',
    'email': 'jsmith@email.com',
    'password': 'p4ssw0rd',
    'logins': 0
}

rv = cb.set(udata['email'], udata)
print "Stored initial item with cas", rv.cas

rv = cb.get(udata['email'])
print "Retrieved item with cas", rv.cas
udata = rv.value
first_cas = rv.cas

print "Will update the document to generate a new CAS"
udata['logins'] += 1
rv = cb.replace(udata['email'], udata, cas=rv.cas)
print "Updated CAS is now", rv.cas

print "Will try a new operation with previous CAS"
try:
    rv = cb.replace(udata['email'], udata, cas=first_cas)
except CouchbaseError as e:
    print "Got error while using stale CAS:", e

print "Current login count is now", cb.get(udata['email']).value['logins']
