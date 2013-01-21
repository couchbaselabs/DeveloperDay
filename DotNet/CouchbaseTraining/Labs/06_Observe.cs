using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Couchbase.Configuration;
using Couchbase;
using Enyim.Caching.Memcached;
using Couchbase.Operations;

namespace CouchbaseTraining.Labs
{
	public class ObserveLab : LabBase
	{
		public override void Run()
		{
			var config = new CouchbaseClientConfiguration();
			config.Urls.Add(new Uri("http://localhost:8091/pools/"));
			config.Bucket = "default";

			var client = new CouchbaseClient(config);

			//Store a key, but don't return success until it is written
			//to disk on the master (or times out)
			var success = client.ExecuteStore(StoreMode.Set, "key_1", 2, PersistTo.One);
			Console.WriteLine(success.Success); //will return false

			//Store a key, but don't return success until it is 
			//replicated to 2 nodes (or times out)
			//will fail on a single or two node cluster
			success = client.ExecuteStore(StoreMode.Set, "key_1", 2, ReplicateTo.Two);
			Console.WriteLine(success.Success); //will return false

			//Store a key, but don't return success until it is written
			//to disk on the master and replicated to 2 nodes (or times out)
			//will fail on a single or two node cluster
			success = client.ExecuteStore(StoreMode.Set, "key_1", 2, PersistTo.One, ReplicateTo.Two);
			Console.WriteLine(success.Success); //will return false
			
			client.Remove("key_1");
			client.Remove("key_2");
		}
	}
}
