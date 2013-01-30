package main

import (
	"fmt"
	"github.com/couchbaselabs/go-couchbase"
	"log"
)

func main() {
	c, err := couchbase.Connect("http://localhost:8091/")
	if err != nil {
		log.Fatalf("Error connecting:  %v", err)
	}

	fmt.Printf("Connected to ver=%s\n", c.Info.ImplementationVersion)

	pool, err := c.GetPool("default")
	if err != nil {
		log.Fatalf("Error getting pool:  %v", err)
	}

	bucket, err := pool.GetBucket("default")
	if err != nil {
		log.Fatalf("Error getting bucket:  %v", err)
	}

	fmt.Printf("Bucket Name: %v\n", bucket.Name)

	bucket, err = pool.GetBucket("beer-sample")
	if err != nil {
		log.Fatalf("Error getting bucket:  %v", err)
	}

	fmt.Printf("Bucket Name: %v\n", bucket.Name)

}
