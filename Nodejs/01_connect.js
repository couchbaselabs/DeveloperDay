var couchnode = require('couchbase');

console.log("--------------------------------------------------------------------------");
console.log("Couchbase Connections");
console.log("--------------------------------------------------------------------------");


couchnode.connect({
  "password": "",
  "hosts": ["localhost:8091"],
  "bucket": "default"}, 
  function(err, cb) {
    if (err) {
      throw (err)
    }

 console.log( "Information about Couchase Object" );
 console.log( cb );


 console.log("\n\n--------------------------------------------------------------------------");
 console.log( "\tPress ctrl+c to quit" );
		
		
});



