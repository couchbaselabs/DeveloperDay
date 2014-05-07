from couchbase import Couchbase
from couchbase.exceptions import CouchbaseError

cb = Couchbase.connect(bucket='default')

# delete the counter, if it exists
cb.delete('counter', quiet=True)

try:
    cb.incr('counter')
except CouchbaseError as e:
    print "Couldn't increment nonexistent counter", e


# Create a counter with an initial value of 10
rv = cb.incr('counter', initial=10)
print "Current counter value is", rv.value

# 'initial' value is ignored if counter already exists..
print "Decrement by one.."
rv = cb.decr('counter', initial=10)
print "Current counter value is", rv.value

print "Incrementing by 4"
rv = cb.incr('counter', amount=4)
print "Current counter value is", rv.value

print "Decrementing by 2"
rv = cb.decr('counter', amount=2)
print "Current counter value is", rv.value
