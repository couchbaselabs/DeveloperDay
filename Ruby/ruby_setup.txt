################################################################## 
## Mac OS X                                                       


################################################################## 
## Install C Library via Homebrew


[Uninstall Previous Version]
[$ brew uninstall libcouchbase]

$ brew install https://github.com/couchbase/homebrew/raw/stable/Library/Formula/libcouchbase.rb


################################################################## 
## Install Couchbase Gem

$ gem install couchbase


################################################################## 
## Using the Gem

require 'couchbase'


################################################################## 
## Gemfile

gem 'couchbase'


################################################################## 
## References


Couchbase Ruby    - http://www.couchbase.com/develop/ruby/current
API Reference     - http://www.couchbase.com/docs/couchbase-sdk-ruby-1.2/couchbase-sdk-ruby-summary.html
RDoc              - http://www.couchbase.com/autodocs/couchbase-ruby-client-1.2.0/index.html
Github Gem Repo   - https://github.com/couchbase/couchbase-ruby-client

Couchbase Ruby Model      - https://github.com/couchbase/couchbase-ruby-model
Couchbase EventMachine    - https://github.com/couchbase/couchbase-ruby-client-em




