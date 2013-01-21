using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Couchbase.Configuration;
using Couchbase;
using Enyim.Caching.Memcached;

namespace CouchbaseTraining.Labs
{
	public class RetrieveLab : LabBase
	{
		[Serializable] //necessary when not storing JSON strings
		private class User
		{
			public string Username { get; set; }

			public string Password { get; set; }

			public string Name { get; set; }

			public string Email { get; set; }

			public int Logins { get; set; }

			public string Type { get; set; }

		}

		public override void Run()
		{
			var config = new CouchbaseClientConfiguration();
			config.Urls.Add(new Uri("http://localhost:8091/pools/"));
			config.Bucket = "default";

			var client = new CouchbaseClient(config);

			var user1 = new User
			{
				Username = "cmathison",
				Name = "Carrie Mathison",
				Email = "carrie.mathison@cia.gov",
				Password = "IHeartNick",
				Logins = 0
			};

			var user2 = new User
			{
				Username = "nbrody",
				Name = "Nicholas Brody",
				Email = "nicholas.brody@house.gov",
				Password = "issa",
				Logins = 0
			};

			//store the user - ExecuteStore returns detailed error info, if any
			var result1 = client.ExecuteStore(StoreMode.Set, user1.Email, user1);
			if (!result1.Success)
			{
				Console.WriteLine("Store failed with message {0} and status code {1}", result1.Message, result1.StatusCode);

				if (result1.Exception != null)
				{
					throw result1.Exception;
				}
			}

			var result2 = client.ExecuteStore(StoreMode.Set, user2.Email, user2);
			//same check as result1 would be useful

			var doc = client.Get<User>(user1.Email);
			Console.WriteLine(doc.Name);

			//get doc with extended info
			var result = client.ExecuteGet<User>(user1.Email);

			//update login count
			doc.Logins += 1;

			//update document (ignore errors for lab)
			client.ExecuteStore(StoreMode.Replace, user1.Email, doc);

			doc = client.Get<User>(user1.Email);
			Console.WriteLine("User {0} had {1} logins", doc.Name, doc.Logins);

			client.Remove(user1.Email);
			client.Remove(user2.Email);
		}
	}
}
