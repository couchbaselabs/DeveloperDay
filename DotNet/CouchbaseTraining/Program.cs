using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace CouchbaseTraining
{
	class Program
	{
		static void Main(string[] args)
		{
			var options = new string[] { "Connect", "Storage", "JSON Storage", "Retrieve", "Atomic Counters", "CAS", "Observe", "Views" };
			
			for (var i = 1; i <= options.Length; i++)
			{
				Console.WriteLine(i + ": " + options[i-1]);
			}

			Console.Write("Enter a lab number from the list above: ");
			var choice = Console.ReadLine();
			var lab = options[int.Parse(choice)-1].Replace(" ", "");

			var obj = Type.GetType("CouchbaseTraining.Labs." + lab + "Lab").GetConstructor(Type.EmptyTypes).Invoke(null);
			var method = obj.GetType().GetMethod("Run").Invoke(obj, null);
		}
	}
}
