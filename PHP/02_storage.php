<?php

echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase Storage Operations\n";
echo "--------------------------------------------------------------------------\n";


$cb = new Couchbase();

// Create a key
$cb->set("mytest", 1);
echo("\n--------------\n");
echo("Get mytest :");
echo( $cb->get("mytest") );


// try to add a key that exists
$result = $cb->add("mytest", 2);
if ($result) {
	echo("\n--------------\n");
	echo("Add succeeded");
} else {
	echo("\n--------------\n");
	echo("Add failed : key exists");	
}


// success
$result = $cb->replace("mytest", 2);
echo("\n--------------\n");
echo("Get mytest :");
echo( $cb->get("mytest") );


// try to replace a key that does not exist
$result = $cb->add("mytest4", 2);
if ($result) {
	echo("\n--------------\n");
	echo("Replace succeeded");
} else {
	echo("\n--------------\n");
	echo("Replace failed : key does not exist");	
}

$cb->delete("mytest");

$result = $cb->delete("mytest3");
if ($result) {
	echo("\n--------------\n");
	echo("Delete succeeded");
} else {
	echo("\n--------------\n");
	echo("Delete failed : key does not exist");	
}

echo("\n--------------\n");

?>