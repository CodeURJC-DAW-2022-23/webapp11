#!/usr/bin/env bash

docker run --rm -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=3techmarket -p 5432:5432 -d mysql:8.0