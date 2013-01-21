using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Couchbase.Configuration;
using Couchbase;

namespace CouchbaseTraining.Labs
{
	public class ConnectLab : LabBase
	{
		public override void Run()
		{
			//-> Code config 
			//var config = new CouchbaseClientConfiguration();
			//config.Urls.Add(new Uri("http://localhost:8091/pools/"));
			//config.Bucket = "default";
			//config.BucetPassword = "";

			//uncomment code above and pass to constructor to 
			//use code config instead of app.config
			var client = new CouchbaseClient();
		}
	}
}
