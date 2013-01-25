<?php

echo "--------------------------------------------------------------------------\n";
echo "\t Couchbase Atomic Counters\n";
echo "--------------------------------------------------------------------------\n";


$cb = new Couchbase();

echo "Set Counter to 0\n";

$cb->set("counter", 0);
echo "Get counter: ";
echo $cb->get("counter");
echo "\n";

$cb->increment("counter");
echo "Get counter: ";
echo $cb->get("counter");
echo "\n";


echo "Increment by 10\n";
$cb->increment("counter",10);
echo "Get counter: ";
echo $cb->get("counter");
echo "\n";


echo "\nDelete the counter\n";
$cb->delete("counter");


echo "\nInit the value to 10\n";
// create an set an initial 
$cb->increment("counter", 1 , true , 0 , 10);
echo "Get counter: ";
echo $cb->get("counter");
echo "\n";

// does not decrease below 0
$cb->set("counter", 0);
$cb->decrement("counter");
echo "Get counter: ";
echo $cb->get("counter");
echo "\n";

// set to max value
$cb->set("counter", -1);
$cb->decrement("counter");
$cb->increment("counter");
echo "Get counter: ";
echo $cb->get("counter");
echo "\n";

// add one to max
$cb->increment("counter");
// returns 0
echo "Get counter: ";
echo $cb->get("counter");
echo "\n";

echo "--------------------------------------------------------------------------\n";


?>