<?php


echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase Connections\n";
echo "--------------------------------------------------------------------------\n";


// Connecto to beer sample bucket
$beers = new Couchbase("127.0.0.1:8091", "", "", "beer-sample");

// Connext to default bucket
$default = new Couchbase();

// will raise an error
$dummy = new Couchbase("127.0.0.1:8091", "beer-sample", "password", "beer-sample");

echo("--- Beers Bucket ---\n");
var_dump( $beers->getStats() ); //dump some infos from the bucket

echo("\n--- Default Bucket ---\n");
var_dump( $default->getStats() ); //dump some infos from the bucket

echo("\n--- 'No' Bucket ---\n");
var_dump( $dummy->getStats() );  //error

echo "--------------------------------------------------------------------------\n";

?>