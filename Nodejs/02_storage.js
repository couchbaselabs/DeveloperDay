// vim: ts=2 sw=2:
var Connection = require('couchbase').Connection;

console.log("--------------------------------------------------------------------------");
console.log("Couchbase Storage Operations");
console.log("--------------------------------------------------------------------------");

var cb = new Connection({
  "password": "",
  "host": "localhost",
  "bucket": "default"
  }, function(err) {
  if (err) {
    throw (err)
  }
  console.log("Set a Key-Value and Get the Key-Value");
  cb.set("mytest", "my value", function(err, result) {
    // in the call back
    cb.get("mytest", function(err, result) {
      console.log("cb.get(\"mytest\") : " + result.value);

      console.log("\nTry to Add the same key, raises exception");
      cb.add("mytest", "my value", function(err, result) {
        if (err) {
          console.log(err);
        } else {
          console.log("It should have failed..");
        }

        cb.replace("mytest", "my value replaced", function(err, result) {
          console.log("\nReplace the key-value with Replace");
          cb.get("mytest", function(err, result) {
            console.log("cb.get(\"mytest\") : " + result.value);

            console.log("\nTry to replace non existant key");
            cb.replace("mytest4", "my value4 replaced", function(err, result) {
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
