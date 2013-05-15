# -*- Mode: Python -*-
# vi:si:et:sw=4:sts=4:ts=4

# customize these variables for your deployment in your local.py

HOSTPORT = 'localhost:8081'
USERNAME = 'default'
PASSWORD = ''

try:
    from local import *
except:
    pass
