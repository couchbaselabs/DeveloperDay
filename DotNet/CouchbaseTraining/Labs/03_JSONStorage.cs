using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Couchbase.Configuration;
using Couchbase;
using Couchbase.Extensions;
using Enyim.Caching.Memcached;
using Newtonsoft.Json;

namespace CouchbaseTraining.Labs
{
	
	public class JSONStorageLab : LabBase
	{
		private class City
		{
			[JsonProperty("name")]
			public string Name { get; set; }

			[JsonProperty("state")]
			public string State { get; set; }

			[JsonProperty("type")]
			public string Type { get { return "city"; } }
		}

		public override void Run()
		{
			var config = new CouchbaseClientConfiguration();
			config.Urls.Add(new Uri("http://localhost:8091/pools/"));
			config.Bucket = "default";

			var client = new CouchbaseClient(config);

			//Store JSON
			client.StoreJson(StoreMode.Set, "CT_Hartford", new City { Name = "Hartford", State = "CT" });

			//Get City instance back from JSON
			var city = client.GetJson<City>("CT_Hartford");
			Console.WriteLine(city.Name);

			client.Remove("CT_Hartford");
		}
	}
}
