using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Couchbase.Configuration;
using Couchbase;

namespace CouchbaseTraining.Labs
{
	class ViewsLab : LabBase
	{
		public override void Run()
		{
			var config = new CouchbaseClientConfiguration();
			config.Urls.Add(new Uri("http://localhost:8091/pools/"));
			config.Bucket = "default";

			var client = new CouchbaseClient(config);

			Console.WriteLine("Breweries by_name, limited to 10 records");
			var view = client.GetView("breweries", "by_name").Limit(10);

			Console.WriteLine("Breweries by_name, output the keys");
			view = client.GetView("breweries", "by_name").Limit(10);
			foreach (var item in view)
			{
				Console.WriteLine("Key: " + item.ViewKey);
			}

			Console.WriteLine("Breweries by_name, query by key range (y or Y)");
			view = client.GetView("breweries", "by_name").StartKey("y").EndKey("z");
			foreach (var item in view)
			{
				Console.WriteLine("Key: " + item.ViewKey);
			}

			Console.WriteLine("Breweries by_name, output the keys (y only)");
			view = client.GetView("breweries", "by_name").StartKey("y").EndKey("Y");
			foreach (var item in view)
			{
				Console.WriteLine("Key: " + item.ViewKey);
			}
		}
	}
}
