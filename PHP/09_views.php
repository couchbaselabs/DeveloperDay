<?php

echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase Views\n";
echo "--------------------------------------------------------------------------\n";

// Create the view as documented here:
// http://www.couchbase.com/docs/couchbase-sdk-php-1.1/tutorial-preparations-cb-server.html


$cb = new Couchbase("127.0.0.1:8091", "", "", "beer-sample");


echo "\n--------------------------------------------------------------------------\n";

$views = $cb->getDesignDoc('brewery');
var_export ( json_decode($views) );


echo "\n--------------------------------------------------------------------------\n";
echo "Breweries (by_name)\n";
$results = $cb->view("brewery", "by_name",  array('limit' => 10 ) );
foreach($results['rows'] as $row) {
    $doc = $cb->get($row['id']);
    if($doc) {
        $doc = json_decode($doc, true);
		echo("\nName : " );
		echo($doc["name"]);
    }

}


echo "\n--------------------------------------------------------------------------\n";
echo "Breweries (by_name), and output country\n";
$results = $cb->view("brewery", "by_name",  array('limit' => 10 ) );
foreach($results['rows'] as $row) {
    $doc = $cb->get($row['id']);
    if($doc) {
        $doc = json_decode($doc, true);
		echo("\nname : " );
		echo($doc["name"]);
		echo("|| country : " );
		echo($doc["country"]);
    }

}


echo "\n--------------------------------------------------------------------------\n";
echo "Breweries (by_name) starting with letter y or Y\n";
$results = $cb->view("brewery", "by_name",  array('startkey' => 'y','endkey' => 'z') );
foreach($results['rows'] as $row) {
    $doc = $cb->get($row['id']);
    if($doc) {
        $doc = json_decode($doc, true);
		echo("\nname : " );
		echo($doc["name"]);
    }

}


echo "\n--------------------------------------------------------------------------\n";
echo "Breweries (by_name) starting with letter y only, no Y (results should be empty)\n";
$results = $cb->view("brewery", "by_name",  array('startkey' => 'y','endkey' => 'Y') );
foreach($results['rows'] as $row) {
    $doc = $cb->get($row['id']);
    if($doc) {
        $doc = json_decode($doc, true);
		echo("\nname : " );
		echo($doc["name"]);
    }

}

echo "\n--------------------------------------------------------------------------\n";

?>