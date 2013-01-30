package main

import (
	"fmt"
	"github.com/couchbaselabs/go-couchbase"
	"log"
	"reflect"
)

var db *couchbase.Bucket

func outputValue(k string) interface{} {

	var data interface{}
	err := db.Get(k, &data)

	fmt.Printf("mykey value type = %v\n\n", reflect.TypeOf(data))
	fmt.Printf("mykey = %v\n\n", data)

	return err
}

func main() {
	var err interface{}
	db, err = couchbase.GetBucket("http://localhost:8091/", "default", "default")
	if err != nil {
		log.Fatalf("Error connecting:  %v", err)
	}

	err = db.Set("mykey", 0, 1)
	if err != nil {
		log.Fatalf("Error setting key-value:  %v", err)
	}

	err = outputValue("mykey")
	if err != nil {
		log.Fatalf("Error setting key-value:  %v", err)
	}

}
