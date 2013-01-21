<?php

// Connecto to beer sample bucket
$beers = new Couchbase("127.0.0.1:8091", "", "", "beer-sample");


// Connext to default bucket
$default = new Couchbase();

// will raised an error
$dummy = new Couchbase("127.0.0.1:8091", "beer-sample", "password", "beer-sample");

echo("--- Beers Bucket ---\n");
echo( $beers->getStats() ); //dump some infos from the bucket

echo("\n--- Default Bucket ---\n");
echo( $default->getStats() ); //dump some infos from the bucket

echo("\n--- 'No' Bucket ---\n");
echo( $dummy->getStats() );  //error

echo("\n----------\n");

?>