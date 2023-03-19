#!/bin/bash

railway up

railway variables set SERVER_SSL_ENABLED=false
railway variables set SPRING_JPA_HIBERNATE_DDL-AUTO=update

railway up