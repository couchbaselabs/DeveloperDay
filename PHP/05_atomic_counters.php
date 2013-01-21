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

echo "Init the value to 10\n";
// create an set an initial (increment)
$cb->increment("counter", $initial = 10);
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