<?php


echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase - Compare and Swap\n";
echo "--------------------------------------------------------------------------\n";


$cb = new Couchbase();

$user_data1 = new stdClass;
$user_data1->doctype = "learn";
$user_data1->username = "jsmith";
$user_data1->name = "John Smith";
$user_data1->email = "jsmith@email.com";
$user_data1->password = "p4ssw0rd";
$user_data1->logins = 0;



echo "--------------------------------------------------------------------------\n";
echo "Set the Data, then Retrieve Two Copies of the Document\n";

// initialize the documents
$cb->set($user_data1->email, json_encode($user_data1) );

// retrieve the document and output
$v1 = json_decode( $cb->get( $user_data1->email, null, $cas) );

echo "--------------------------------------------------------------------------\n";
echo "CAS : $cas\n";
 
$cas1 = $cas;
$v2 = json_decode( $cb->get( $user_data1->email, null, $cas) ); 
$cas2 = $cas;

echo "--------------------------------------------------------------------------\n";
echo "Both v1 and v2 are identical and have identical CAS [(cas1) $cas1 == (cas2) $cas2]\n";

echo "\n--------------\n";
echo "Now We'll update the document which results in a new CAS";
$v1->logins += 1;
$cas1 = $cb->set($v1->email, json_encode($v1) );
echo "Now CAS as changed [(cas1) $cas1 != (cas2) $cas2]";

echo "\n\n--------------\n";
$result = $cb->replace($v2->email, json_encode($v2), 0, $cas2 );
if ( $result ) {
	echo "Success: Replace Operation succeeded (shouldn't display)";
	
} else {
	echo "ERROR: Replace Operation failed due to CAS mismatch";
	
}

echo "\n--------------------------------------------------------------------------\n";



?>