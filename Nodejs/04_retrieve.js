var couchnode = require('couchbase');

console.log("--------------------------------------------------------------------------");
console.log("Couchbase Retrieval Operations");
console.log("--------------------------------------------------------------------------");


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

couchnode.connect({
	"password": "",
	"hosts": ["localhost:8091"],
	"bucket": "default"
}, function(err, cb) {
	if (err) {
		throw (err)
	}



	cb.set( userData1.email , userData1, function(err, meta) {
		var metaUser1 = meta;
		cb.set( userData2.email , userData2, function(err, meta) {
			console.log( "User 1 and User2 saved" );
			var metaUser2 = meta;

			console.log("CAS User 1:"+ metaUser1.cas.str);
			console.log("CAS USer 2:"+ metaUser2.cas.str);


			console.log("\nUpdate the doc and check the CAS");
			userData1.logins += 1;

			cb.set( userData1.email , userData1, function(err, meta) {
				console.log("Different CAS  ");
				console.log("\tBefore update : "+metaUser1.cas.str);
				console.log("\tAfter CAS : "+ meta.cas.str);
				
				
				console.log("\n--------------------------------------------------------------------------");
				process.exit(0);
			});




		});
		
	});




});

