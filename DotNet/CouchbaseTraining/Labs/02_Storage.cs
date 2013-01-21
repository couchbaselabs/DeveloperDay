using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Couchbase.Configuration;
using Couchbase;
using Enyim.Caching.Memcached;

namespace CouchbaseTraining.Labs
{
	public class StorageLab : LabBase
	{
		public override void Run()
		{
			var config = new CouchbaseClientConfiguration();
			config.Urls.Add(new Uri("http://localhost:8091/pools/"));
			config.Bucket = "default";

			var client = new CouchbaseClient(config);

			//add or replace a key
			client.Store(StoreMode.Set, "key_1", 1);
			Console.WriteLine(client.Get("key_1"));

			var success = client.Store(StoreMode.Add, "key_1", 2);
			Console.WriteLine(success); //will return false

			success = client.Store(StoreMode.Replace, "key_1", 2);
			Console.WriteLine(success); //will return true

			success = client.Store(StoreMode.Replace, "key_2", 2);
			Console.WriteLine(success); //will return false

			//add a new key
			client.Store(StoreMode.Set, "key_3", 1);
			Console.WriteLine(client.Get("key_3"));

			client.Remove("key_1");
			client.Remove("key_2");
		}
	}
}
