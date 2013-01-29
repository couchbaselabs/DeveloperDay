<?php


echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase Retrieval Operations\n";
echo "--------------------------------------------------------------------------\n";


$cb = new Couchbase();

/** 
 * Simple Entity with decode logic.
 */
class Person {
	public $doctype;
	public $username;
	public $name;
	public $email;
	public $password;
	public $logins;

	public static function fromJSON($json) {
		$decoded = json_decode($json, true);

		$person = new Person();
		foreach($decoded as $type => $value) {
			$person->{$type} = $value;
		}
		return $person;
	}
}


$user_data1 = new Person;
$user_data1->doctype = "learn";
$user_data1->username = "jsmith";
$user_data1->name = "John Smith";
$user_data1->email = "jsmith@email.com";
$user_data1->password = "p4ssw0rd";
$user_data1->logins = 0;

$user_data2 = new Person;
$user_data2->doctype = "learn";
$user_data2->username = "xdoe";
$user_data2->name = "Xavier Doe";
$user_data2->email = "xdoe@email.com";
$user_data2->password = "p4ssw0rd";
$user_data2->logins = 0;

// initialize the documents
$cb->set($user_data1->email, json_encode($user_data1) );
$cb->set($user_data2->email, json_encode($user_data2) );

// retrieve the document and output
$doc = $cb->get($user_data1->email);
echo "\n--------------\nRetrieve Doc\n";
echo $doc;
echo "\n--------------\n";
var_dump(Person::fromJSON($doc));


$first_cas = null;
$doc = json_decode( $cb->get( $user_data1->email, null, $first_cas) ); 
echo "\n--------------\nRetrieve Doc and get CAS\n";
echo "1st CAS: $first_cas";

echo "\n--------------\n";
echo "update doc and look at cas";
$doc->logins += 1;
echo "\n--------------\n";
echo "Logins:  $doc->logins \n";
$cb->replace($user_data1->email, json_encode($user_data1) );
$second_cas = null;
$doc = json_decode( $cb->get( $user_data1->email, null, $second_cas) ); 
echo "\n--------------\n";
echo "2nd CAS: $second_cas";
echo "\n--------------\n";


$i=1;
$keys = array();
while($i<=9) {
	// put a gap in the list to show missing item
	if ($i!=3) {
		$cb->set("key-$i", json_encode( array("doctype" => "learn","value" => $i) ), 30 );	
	}
	$keys[$i] = "key-$i";
	$i++;
}

echo "Values as Array\n";

$values = $cb->getMulti( $keys );

var_export($values);

echo "\n\n--------------------------------------------------------------------------\n";

?>