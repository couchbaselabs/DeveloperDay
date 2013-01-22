<?php

echo "--------------------------------------------------------------------------\n";
echo "\tCouchbase Non-JSON Ops\n";
echo "--------------------------------------------------------------------------\n";

if (file_exists("./output.jpg") ) {
	unlink("./output.jpg");	
}


$cb = new Couchbase();


echo "Store Strings, Floats, Integers\n";
$cb->set("store_strings", "The quick brown fox jumped over the lazy dog.", 10);
$cb->set("store_floats", 3.14159265358979, 10);
$cb->set("store_integers", -42, 10);

echo "get 'store_strings => '";
echo $cb->get("store_strings");
echo "\n\n";

echo "get 'store_floats' => ";
echo $cb->get("store_floats");
echo "\n\n";

echo "get 'store_integers' => ";
echo $cb->get("store_integers");
echo "\n\n";


echo  "--------------------------------------------------------------------------\n";
echo  "Append/Prepend Ops\n";

echo "Create a list, start with one item\n";
$cb->set("mylist", "oranges", 30);

echo "Add an item...\n\n";
$cb->prepend("mylist", "apples,");

echo "Add an item...\n\n";
$cb->append("mylist", ",bananas");

echo "Add an item...\n\n";
$cb->append("mylist", ",lemons");

echo "Add an item...\n\n";
$cb->append("mylist", ",grapes");

echo "get 'mylist' => ";
echo $cb->get("mylist");
echo "\n\n";


echo "--------------------------------------------------------------------------\n";
echo "Store Binary Blobs\n";

// read file
echo "\nRead file\n";
$image = file_get_contents('../Binary/lolcat.jpg');
$image_data = base64_encode($image);
$cb->set("img", $image_data, 10);

$to_save = base64_decode(  $cb->get("img") );

file_put_contents("./output.jpg", $to_save);



echo "\n--------------------------------------------------------------------------\n";


?>