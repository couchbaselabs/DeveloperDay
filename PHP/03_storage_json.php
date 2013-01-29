<?php


echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase JSON Doc Storage\n";
echo "--------------------------------------------------------------------------\n";


$cb = new Couchbase();


$mydoc =  array("doctype" => "test", "name" => "John Smith");

echo "Original Document:\n";
var_dump($mydoc);

echo "\nSet Document (Hash -> JSON)\n";
// store a json doc (encode it)
$cb->set("mydoc", json_encode($mydoc));


echo "Retrieve Document (JSON -> Hash), also set a 10 second TTL\n";
// retrieve and decode json doc
$doc = $cb->getAndTouch("mydoc",10);

echo "\n--------------\n";
echo "JSON String: " . $doc;
echo "\n--------------\n";
var_dump(json_decode( $doc,true)); // (as array, not object)

echo "--------------------------------------------------------------------------\n";

?>