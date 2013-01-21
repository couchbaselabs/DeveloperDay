<?php

echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase Observe/Durability\n";
echo "--------------------------------------------------------------------------\n";


$cb = new Couchbase();

$user_data1 = new stdClass;
$user_data1->doctype = "learn";
$user_data1->username = "jsmith";
$user_data1->name = "John Smith";
$user_data1->email = "jsmith@email.com";
$user_data1->password = "p4ssw0rd";
$user_data1->logins = 0;


$key = $user_data1->email;
$document = json_encode($user_data1);

//Store a key, but don't return success until it is written
//to disk on the master (or times out)
echo "\n--------------\n";
echo "Set Document with Observe Asynchronously, Returns when Persisted, and output return values\n"; 
try {
	$status = $cb->set($key, $document,  0, $cas = "", $persist_to = 1);
	echo("status/cas: $status");
} catch (Exception $e) {
    echo 'Caught exception: ',  $e->getMessage(), "";
}

//Store a key, but don't return success until it is 
//replicated to 2 nodes (or times out)
//will fail on a single or two node cluster
echo("\n--------------\n");
try {
	$status = $cb->set($key, $document,  0, $cas = "", $replicate_to = 2);
	echo($status);
} catch (Exception $e) {
    echo 'Caught exception: ',  $e->getMessage(), "";
}

//Store a key, but don't return success until it is written
//to disk on the master and replicated to 2 nodes (or times out)
//will fail on a single or two node cluster
echo("\n--------------\n");
try {
	$status = $cb->set($key, $document,  0, $cas = "", $persist_to = 2, $replicate_to = 2);
	echo("\n--------------\n");
	echo($status);
} catch (Exception $e) {
    echo 'Caught exception: ',  $e->getMessage(), "";
}


echo "--------------------------------------------------------------------------\n";



?>