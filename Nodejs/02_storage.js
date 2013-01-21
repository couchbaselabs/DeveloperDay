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


	cb.set( "mytest" ,  "my value" , function (err, meta) {
	  // in the call back
  	cb.get( "mytest", function (err, value, meta) {
      console.log("Value : "+ value);
      
      
    	cb.add( "mytest" ,  "my value" , function (err, meta) {
    	  if (err) {
    	    console.log(err);
    	  } else {
    	    console.log("It should have failed..");
    	  }
    	  
    	});
      
      
  	});
	});


		
		
});

// 
// 
// 
// 
// // create a key
// cb.set("mytest", "my value")
// puts cb.get("mytest")
// 
// // this raises key exists exception, uncomment to see error
// cb.add("mytest", "my value added")
// 
// // success
// cb.replace("mytest", "my value replaced")
// puts cb.get("mytest")
// 
// // this raises exception (doesn’t exist)
// cb.replace("mytest4", "my value4 replaced") 
// 
// // success
// cb.add("mytest3", "my value3") 
// puts cb.get("mytest3")
// 
// cb.delete("mytest")
// cb.delete("mytest3")
// 
// 
// // both of these return nil class
// puts cb.get("mytest").class.to_s
// puts cb.get("mytest3").class.to_s

