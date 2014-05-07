var Connection = require('couchbase').Connection;

console.log("--------------------------------------------------------------------------");
console.log("Couchbase JSON Doc Storage");
console.log("--------------------------------------------------------------------------");

var cb = new Connection({
	"password": "",
    "host": "localhost",
	"bucket": "default"
}, function(err) {
	if (err) {
		throw (err)
	}


	var myDoc = {
		doctype : "test",
	   	name : "John Smith"
	};
	
	console.log("Set Document");
	cb.set("mydoc", myDoc, function(err, result) {
		// in the call back
		cb.get("mydoc", function(err, result) {
			console.log('mydoc.name : '+ result.value.name);

			cb.touch("mydoc", 10, function(err, result) {
				console.log(" Touch the doc with TTL");
				console.log("\n--------------------------------------------------------------------------");
				process.exit(0);
			});


		});
	});




});

