// vim:ts=2 sw=2:

var couchnode = require('couchbase');

console.log("--------------------------------------------------------------------------");
console.log("Couchbase Connections");
console.log("--------------------------------------------------------------------------");


var cb = new couchnode.Connection({
    "password": "",
    "host": "localhost",
    "bucket": "default"
  }, 
  function(err) {
    if (err) {
      throw (err)
    }
    console.log( "Information about Couchase Object" );
    console.log( cb );
    console.log("\n\n--------------------------------------------------------------------------");
    process.exit(0); 
  }
);
