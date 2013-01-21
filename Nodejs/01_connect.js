var couchnode = require('couchbase');

var port = 8080;

couchnode.connect({
  "username": "",
  "password": "",
  "hostname": "localhost:8091",
  "bucket": "default"}, 
  function(err, cb) {
    if (err) {
      throw (err)
    }

 console.log( "Information about Couchase Object" );
 console.log( cb );


 console.log( "\n\nPress ctrl+c to quit" );
		
		
});



