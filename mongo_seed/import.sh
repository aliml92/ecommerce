#!/bin/sh
mongoimport --host mongo --db test --collection users --type json --file ./mongo_seed/users.json --jsonArray
mongoimport --host mongo --db test --collection products --type json --file ./mongo_seed/products.json --jsonArray
