<?php

echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase JSON Doc Storage\n";
echo "--------------------------------------------------------------------------\n";


$cb = new Couchbase();


$mydoc =  array("doctype" => "test", "name" => "John Smith");

echo "Set Document (Hash -> JSON)\n";

// store a json doc (encode it)
$cb->set("mydoc", json_encode($mydoc) );


echo "Retrieve Document (JSON -> Hash), also set a 10 second TTL\n";
// retrieve and decode json doc
$doc = $cb->getAndTouch("mydoc",10);

echo("\n--------------\n");
echo( $doc );
echo("\n--------------\n");
var_export( json_decode( $doc ) );
echo "--------------------------------------------------------------------------\n";

?>