var Connection = require('couchbase').Connection;

console.log("--------------------------------------------------------------------------");
console.log("Couchbase Retrieval Operations");
console.log("--------------------------------------------------------------------------");

function formatCas(cas) {
    // pretty-print the CAS properly
    return cas[0] + "" + cas[1];
}

var userData1 = {
  doctype : "learn",
  username : "jsmith",
  name : "John Smith",
  email : "jsmith@email.com",
  password : "p4ssw0rd",
  logins : 0
}

var userData2 = {
  doctype : "learn",
  username : "xsmith",
  name : "Xavier Smith",
  email : "xsmith@email.com",
  password : "p4ssw0rd",
  logins : 0
}

var cb = new Connection({
	"password": "",
	"host": "localhost",
	"bucket": "default"
}, function(err) {
	if (err) {
		throw (err)
	}



	cb.set( userData1.email , userData1, function(err, result) {
		var resultUser1 = result;
		cb.set( userData2.email , userData2, function(err, result) {
			console.log( "User 1 and User2 saved" );
			var resultUser2 = result;

			console.log("CAS User 1:" + formatCas(resultUser1.cas));
			console.log("CAS USer 2:" + formatCas(resultUser2.cas));

			console.log("\nUpdate the doc and check the CAS");
			userData1.logins += 1;

			cb.set( userData1.email , userData1, function(err, result) {
				console.log("Different CAS  ");
				console.log("\tBefore update : " + formatCas(resultUser1.cas));
				console.log("\tAfter CAS : " + formatCas(result.cas));
				
				
				console.log("\n--------------------------------------------------------------------------");
				process.exit(0);
			});




		});
		
	});




});

