# How to set up high-availability with JChem Microservices DB


## Installation

* Install docker-compose on every machine of the swarm.
* Init docker swarm machine on the manager node and add the desired nodes to the swarm.


## Setup before first run

These steps must be executed on every node of the swarm or alternatively swarm registry can be used.

* unzip a JWS tar.gz here (in the end you should have a `jws` folder with the whole application) (download one from the product download site, preferably a version at least 19.19.)
* copy a valid JWS license to `jws/license/license.cxl`
* download [Postgres JDBC driver](http://central.maven.org/maven2/org/postgresql/postgresql/42.2.5/postgresql-42.2.5.jar) and copy it to `jws/jws-db/jdbc-drivers/` folder
* ensure a running docker swarm and run the following docker commands on the manager node.
* ensure a running postgresql server and set its connection properties in `application.properties` file of the `jws-load-balanced-example` directory. Both the `com.chemaxon.zetor.settings.crdb` and the `com.chemaxon.zetor.additional.crdb` sections need to be set. 
* Set the interface mask of the nodes running in the docker swarm in file `hazelcastDockerEureka.xml` in section 
```
    <interfaces enabled="true">
      <interface>10.0.*.*</interface>
    </interfaces>
```
On the other hand this mask should prevent hazelcast from finding ip addresses outside docker swarm. The jws docker container has 3 ip addresses, this mask defines which one will be chosen. Choose the one that enables hazelcasts to find each others. IP addresses found by hazelcast and signed on discovery service can be monitored in [eureka](http://localhost:8761/eureka/apps). You can also check ip addresses of the container by logging into them, and executing ifconfig: 
```
docker exec -it <container-id> bash
apt-get update;apt-get install net-tools
ifconfig
```
You can check if hazelcast instances have found each other by checking the log file in the jws-db container, it's located in /opt/chemaxon/jws/logs.


# Execution

Build the image, this must be executed on every node in the directory of `jws-load-balanced-example`: 

    docker-compose build

Now the image cxn/jws should be built.

Deploy the docker compose file on the master node: 

    docker stack deploy --compose-file docker-compose.yml demo

This command will create a new service stack with name `demo` based on the docker-compose file.

## Other commands

* `docker stack ls` lists all stacks in swarm
* `docker stack ps <stack-name> --no-trunc` list all services in stack (identified by <stack-name>)
* `docker stack rm <stack-name>` stops and removes all services in stack

## Near cache config

Setting hazelcast near cache option has the effect that read operations are faster due to faster hazelcast caching and caching the java objects instead of the serialized format. 

Consequences:

* Synchronization between nodes is slower. 
* This has the effect that to keep jchem-db data consistent only one writer node is allowed, further jws-db nodes can only read the shared db.
* Gateway has separate endpoints for reading and read-write operations:
    * read: `jwsdbreadonly`
    * read-write: `jwsdb`

Near cache load-balanced example needs the following modifications in the above launch process:

* Replace `Dockerfile` with `DockerfileNearCache` and re-build the docker images on the nodes.
* use the relevant docker compose file: 

    `docker stack deploy --compose-file docker-composeNearCache.yml demo`


## Good to know

Table names longer than 18 character cannot be created because internal tables are created based on this name and their name exceeds the 63 character limit in this case.
