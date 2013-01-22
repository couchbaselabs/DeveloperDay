var couchnode = require('couchbase');

console.log("--------------------------------------------------------------------------");
console.log("Couchbase Storage Operations");
console.log("--------------------------------------------------------------------------");

couchnode.connect({
	"password": "",
	"hosts": ["localhost:8091"],
	"bucket": "default"
}, function(err, cb) {
	if (err) {
		throw (err)
	}



	console.log("Set a Key-Value and Get the Key-Value");
	cb.set("mytest", "my value", function(err, meta) {
		// in the call back
		cb.get("mytest", function(err, value, meta) {
			console.log("cb.get(\"mytest\") : " + value);

			console.log("\nTry to Add the same key, raises exception");
			cb.add("mytest", "my value", function(err, meta) {
				if (err) {
					console.log(err);
				} else {
					console.log("It should have failed..");
				}

				cb.replace("mytest", "my value replaced", function(err, meta) {
					console.log("\nReplace the key-value with Replace");
					cb.get("mytest", function(err, value, meta) {
						console.log("cb.get(\"mytest\") : " + value);
						
						console.log("\nTry to replace non existant key");
						cb.replace("mytest4", "my value4 replaced", function(err, meta) {
							if (err) {
								console.log(err);
							} else {
								console.log("It should have failed..");
							}
							
							console.log("\n--------------------------------------------------------------------------");
							process.exit(0);
							
						});
						
					});
				});
			});
		});
	});

});
