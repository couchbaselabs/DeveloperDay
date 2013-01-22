using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Couchbase.Configuration;
using Couchbase;
using Enyim.Caching.Memcached;
using System.Diagnostics;

namespace CouchbaseTraining.Labs
{
	public class CASLab : LabBase
	{
		[Serializable]
		private class Post
		{
			public string Title { get; set; }

			public string Body { get; set; }

			public List<string> Comments { get; set; }

			public string Slug { get; set; }

			public string Type { get { return "post"; } }
		}

		public override void Run()
		{
			var config = new CouchbaseClientConfiguration();
			config.Urls.Add(new Uri("http://localhost:8091/pools/"));
			config.Bucket = "default";

			var client = new CouchbaseClient(config);

			var post = new Post
			{
				Title = "Using Couchbase and .NET",
				Body = "Start by getting the client at Nuget.",
				Comments = new List<string> { "Great post!" },
				Slug = "Using_Couchbase_Net"
			};

			client.ExecuteStore(StoreMode.Set, post.Slug, post);

			var get1 = client.ExecuteGet<Post>(post.Slug);
			var get2 = client.ExecuteGet<Post>(post.Slug);

			Console.WriteLine("-------------------------------------");
			Console.WriteLine("Set the Data, then Retrieve Two Copies of the Document");
			Console.WriteLine();
			Console.WriteLine("Both v1 and v2 are identical and have identical CAS [(cas1) {0} == {1} (cas2)]", get1.Cas, get2.Cas);
			Console.WriteLine();
			Console.WriteLine("-------------------------------------");
			Console.WriteLine("Now We'll update the document which results in a new CAS");
			Console.WriteLine();

			get1.Value.Comments.Add("Wicked good post!");

			var result = client.ExecuteStore(StoreMode.Set, post.Slug, get1.Value);
			get1 = client.ExecuteGet<Post>(post.Slug);

			Console.WriteLine("Now CAS has changed [(cas1) {0} != {1} (cas2)]", get1.Cas, get2.Cas);
			Console.WriteLine();
			Console.WriteLine("-------------------------------------");
		}
	}
}
