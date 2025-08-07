# High availability with Database Search Service

This is an example of how to set up JChem Microservices DB with high-availability mode.

## Before version 25.3.0

High availability of Database Search Service mode is simplified in version 25.3.0. If you have an older version, please find instructions [here](https://github.com/ChemAxon/jchem-microservices-examples/blob/master/loadbalance/before-25.3.0/README.md)

## Prerequisites

For this example, you will need the "JChem Microservices DB" license (downloaded to this folder as `license.cxl`) and __docker__ installed on your system.

Check the JChem Microservices version in the `.env` file, and change it to the one you need.

## Execution

`docker-compose up`

## What is happening in this example?

The docker-compose file has a description of the JChem Microservices system
with a central license server. The license server is a module we build during
__UP__ command, and it is based on [Nginx](https://hub.docker.com/_/nginx) image.

It also starts two instances of Database Search Service which connect to a common PostgreSQL database.

## How to access the system?

In the end the port 8080 will be open and forwarded towards the outer world.
