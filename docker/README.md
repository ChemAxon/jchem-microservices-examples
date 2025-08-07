# How to create Docker images of JChem Microservices manually

> This example is about creating docker images of JChem Microservices _manually_.
If you want to use the official images, see the [official-docker-images](../official-docker-images) example.

## Prerequisites

* install **docker**
* install **docker-compose**
* copy `jws_unix_<<version>>.tar.gz` to folder (Download it from here: [JChem Micorservices](https://chemaxon.com/download?dl=%2Fdata%2Fdownload%2Fjws))
* copy `license.cxl` to folder

## How to run

`docker-compose up`

## Settings

The above setup has the following volumes:
* `dbdata` to save molecules uploaded to `jws-db`
* `logdata` where you can find all the logs
* `config` holds all the configurations for the application for more information please see our [documentation](https://docs.chemaxon.com/display/docs/jchem-microservices_common-modules.md#config)
* `license` is the volume to share the `license.cxl` file between containers

If you want to change any JVM arguments you can pass them to the executables with a `-J` prefix, like:
`-J-Xmx4g` would set the maximum memory of the JVM to __4 GB__. You should write these extra settings to the
end of the `command` in the `docker-compose.yml` file.

## Architecture of the services

JChem Microservices architectural documentation is available [here](https://docs.chemaxon.com/display/docs/jchem-microservices_architecture.md).

## Scaling up the services

All services can be scaled up indefinitely. 
