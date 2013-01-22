var couchnode = require('couchbase');

console.log("--------------------------------------------------------------------------");
console.log("Couchbase JSON Doc Storage");
console.log("--------------------------------------------------------------------------");

couchnode.connect({
	"password": "",
	"hosts": ["localhost:8091"],
	"bucket": "default"
}, function(err, cb) {
	if (err) {
		throw (err)
	}


	var myDoc = {
		doctype : "test",
	   	name : "John Smith"
	};
	
	console.log("Set Document");
	cb.set("mydoc", myDoc, function(err, meta) {
		// in the call back
		cb.get("mydoc", function(err, value, meta) {
			console.log('mydoc.name : '+ value.name);

			cb.touch("mydoc", 10, function(err, meta) {
				console.log(" Touch the doc with TTL");
				console.log("\n--------------------------------------------------------------------------");
				process.exit(0);
			});


		});
	});




});

