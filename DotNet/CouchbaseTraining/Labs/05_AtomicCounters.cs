using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Couchbase.Configuration;
using Couchbase;

namespace CouchbaseTraining.Labs
{
	public class AtomicCountersLab : LabBase
	{
		public override void Run()
		{
			var config = new CouchbaseClientConfiguration();
			config.Urls.Add(new Uri("http://localhost:8091/pools/"));
			config.Bucket = "default";

			var client = new CouchbaseClient(config);

			Console.WriteLine("Set the counter to 0");
			client.Increment("counter", 0, 0);

			Console.WriteLine("Increment by 1, set initial value to 0");
			client.Increment("counter", 0, 1);

			Console.WriteLine(client.Get("counter"));

			Console.WriteLine("Increment by 10, initial value is ignored since it exists");
			client.Increment("counter", 0, 10);

			Console.WriteLine(client.Get("counter"));

			client.Remove("counter");

			Console.WriteLine("Set the counter to 1000");
			client.Decrement("counter", 1000, 0);

			Console.WriteLine("Decrement by 1");
			client.Decrement("counter", 0, 1);

			Console.WriteLine(client.Get("counter"));

			Console.WriteLine("Decrement by 10, initial value is ignored since it exists");
			client.Decrement("counter", 0, 10);

			Console.WriteLine(client.Get("counter"));

		}
	}
}

