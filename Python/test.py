import uuid
import json
import time
from copy import deepcopy
from threading import Thread, Lock
import warnings
#from collections import Set

import requests

from couchbase.logger import logger
from couchbase.rest_client import RestConnection
from couchbase.couchbaseclient import CouchbaseClient
from couchbase.exception import BucketUnavailableException

server_config_uri = "http://127.0.0.1:8091/pools/default"
config = requests.get(server_config_uri)
print config.text
#couchApiBase will not be in node config before Couchbase Server 2.0
print config

r = requests.get(server_config_uri)
print r.status_code
print r.headers['content-type']
print r.encoding
#print r.text
#print r.json()

config = r.json()
couch_api_base = config["nodes"][0].get("couchApiBase")

print couch_api_base
