# How to use the official JChem Microservices Docker images

> **Disclaimer**
>
> This is just a quick example. If you use this example to build your system,
then be prepared to do your own security settings. Feel free to change any parts
to make your life easier, this version only solves the purpose to get you started.

## Prerequisites

In order to download the official Docker images of JChem Microservices, you need to install [Docker](https://www.docker.com/) on your system and configure it to access the [Chemaxon Public Repository](https://docs.chemaxon.com/display/docs/general_public-repository.md). After acquiring your credentials, you can simply call

```
docker login chemaxon.jfrog.io
```

to set up Docker to access the repository.

For this example, you will also need licenses for the JChem Microservices modules. Put your `license.cxl` file to this folder, next to the docker compose file.

## How to run

1. Check the JChem Microservices version in the `.env` file, and change it to the one you need
2. Execute `docker-compose up`

## What is happening in this example?

The docker-compose file has a description of the JChem Microservices system
wiht a central license server. The license server is a module we build during
__UP__ command, and it is based on [Nginx](https://hub.docker.com/_/nginx) image.

## How to access the system?

In the end the port 8080 will be open and forwarded towards the outer world. 

## How to update the license file?

If you already have a license server then the related docker image (`cxn/jms/nginx-license-server:latest`) should be deleted first.
The new license file should be placed in the folder and docker compose can be started &ndash; it re-builds the license server automatically.

## What could be on volumes?

* `/app/jws/jws-db/data` folder contains all the data you upload to the Database Search Service
* `/app/jws/jws-config/common-config` folder contains all the configurations for the services
* `/app/jws/logs/` folder contains all the log files

These folders in a production environment could be on volumes depending on your 
needs, but this is only a kick-starter example, it does not aim to teach docker 
basics. If you want to restructure the containers, feel free. If you would like 
to save more persistent data, than it is up to you.

## How to start with HTTPS

The files in the [https](./https) folder are a variant of this example where `jms-gateway` is configured with HTTPS access.
Necessary configurations in the [https/.env](https/.env) file should be updated before execution:
* KEY_STORE_FILE - Key store file name. It should be placed next to the `https/docker-compose.yml` file
* KEY_STORE_PASSWORD - Password of keystore
* KEY_STORE_TYPE - Key store type (e.g. PKCS12)

