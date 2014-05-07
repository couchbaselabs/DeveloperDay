from couchbase import Couchbase
from couchbase.views.params import Query

cb = Couchbase.connect(bucket='beer-sample')

# Note, this requires the 'by_name' view:
#
# function (doc, meta) {
#   emit(doc.name, null);
# }

print "Querying a view"
rows = cb.query('beer', 'by_name', limit=10)
for row in rows:
    print row

print "Querying views, with documents"
rows = cb.query('beer', 'by_name', include_docs=True, limit=5)
for row in rows:
    print row


print "Querying view with range"
rows = cb.query('beer', 'by_name',
                mapkey_range=['y', 'z'], include_docs=True, limit=5)
for row in rows:
    print row
