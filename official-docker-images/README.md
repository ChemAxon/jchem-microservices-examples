# How to use the official JChem Microservices Docker images

> **Disclaimer**
>
> These are just quick examples. If you use these examples to build your system,
> then be prepared to do your own security settings. Feel free to change any parts
> to make your life easier, the examples are only here to get you started.

## Prerequisites and setup

### Docker

In order to download the official Docker images of JChem Microservices, you need to install [Docker](https://www.docker.com/) on your system and configure it to access the [Chemaxon Public Repository](https://docs.chemaxon.com/display/docs/general_public-repository.md). After acquiring your credentials, you can simply call

```
docker login chemaxon.jfrog.io
```

to set up Docker to access the repository.

### License

For these examples, you will also need licenses for those JChem Microservices modules which you wish to use.
You find more information about the required
licenses [in our documentation](https://docs.chemaxon.com/latest/jchem-microservices_licenses.html).

We show examples for both license key and license file usage.
Follow the step-by-step setup below based on your intended setup.

### How to run

#### With license file

1. Navigate to [license-file](./license-file)
2. Put your `license.cxl` file to this folder, next to the docker compose file.
3. Check the JChem Microservices version in the `.env` file, and change it to the one you need
4. Execute `docker-compose up`

Read [below](#license-key-or-license-file) for more information.

#### With license key

1. Navigate to [license-key](./license-key)
2. Fill `LICENSE_KEY` variable in `.env` file with your license key
3. Check the JChem Microservices version in the `.env` file, and change it to the one you need
4. Execute `docker-compose up`

Read [below](#license-key-or-license-file) for more information.

#### In standalone mode

1. Navigate to [standalone](./standalone)
2. Fill `LICENSE_KEY` variable in `.env` file with your license key
3. Check the JChem Microservices version in the `.env` file, and change it to the one you need
4. Remove any services you do not need from `docker-compose.yml`, along with their related Postgres and Task Manager
   wiring (see [How to run in standalone mode](#how-to-run-in-standalone-mode) for details)
5. Execute `docker-compose up`

Read [below](#how-to-run-in-standalone-mode) for more information.

#### Load balancing

1. Navigate to [load-balancing](./load-balancing)
2. Fill `LICENSE_KEY` variable in `.env` file with your license key
3. Check the JChem Microservices version in the `.env` file, and change it to the one you need
4. Execute `docker-compose up`

Read [below](#how-to-run-with-load-balancing) for more information.

## Configuration and additional information

### How to access the system?

In most examples, the port 8080 will be open and forwarded towards the outer world. The only exception is the
`standalone` example, where each service is exposed on its own port. Read [below](#how-to-run-in-standalone-mode) for
more information.

### License key or license file?

The examples provide a license to the services in one of two ways, depending on which folder you use:

* **License key** (`license-key`, `https`, `standalone` or `load-balancing`): each service receives the key directly
  through the
  `CHEMAXON_LICENSE_SERVER_KEY` environment variable, which is set from the `LICENSE_KEY` value in the `.env` file. No
  separate license server is started.
* **License file** (`license-file`): the docker-compose file describes the JChem Microservices system with a central
  license server. The license server is a module we build during the __UP__ command, and it is based on the
  [Nginx](https://hub.docker.com/_/nginx) image; the services read the license from it via the `CHEMAXON_LICENSE_URL`
  environment variable.

### How to update the license file?

This applies to the `license-file` example only (the other examples have no license
server &ndash; update the `LICENSE_KEY` value in their `.env` file instead).

If you already have a license server then the related docker image (`cxn/jms/nginx-license-server:latest`) should be deleted first.
The new license file should be placed in the folder and docker compose can be started &ndash; it re-builds the license server automatically.

### What else could be on volumes?

* in all services: the `/app/jws/logs/` folder contains the log files
* in `jms-config`: the `/app/jws/jws-config/common-config` folder contains the common configurations for the services
* in `jms-db`: the `/app/jws/jws-db/data` is the default folder to store the H2 database files if Database Search
  Service is configured to run with H2 (not recommended in production)

These folders could be on volumes depending on your needs.

### Database

**PostgreSQL is the recommended default.** The examples come pre-configured to run `jms-db` and `jms-taskmanager`
against PostgreSQL because it is more robust than the embedded H2 database and better suited for production and
concurrent workloads. H2 is available as a lightweight alternative (see [Using H2 instead](#using-h2-instead-alternative)),
but PostgreSQL is preferred unless you have a specific reason to use H2.

The connection details are set in the `.env` file next to each `docker-compose.yml`:

```
POSTGRES_DB=jwsdb
POSTGRES_TASK_DB=taskdb
POSTGRES_USER=jws
POSTGRES_PASSWORD=jws
```

#### Using H2 instead (alternative)

By default (without the `CXN_DB_*` / `CXN_TASK_*` overrides), both services use a file-based **H2** database.
If you prefer H2 over PostgreSQL, remove the `postgres` service and the `CXN_DB_*` (for `jms-db`) and
`CXN_TASK_*` (for `jms-taskmanager`) environment variables &ndash; along with the `postgres` entry from the
services' `depends_on` &ndash; from the chosen `docker-compose.yml`. With H2 the data is stored on the file
system inside the containers (see [What could be on volumes?](#what-could-be-on-volumes)), so mount
`/app/jws/jws-db/data` (and the Task Manager's `./data`) to a volume if you want to persist it.

### How to start with HTTPS

The files in the [https](./https) folder are a variant of the `license-key` example where `jms-gateway` is configured with HTTPS access.
Necessary configurations in the [https/.env](https/.env) file should be updated before execution:
* LICENSE_KEY - Your license key
* KEY_STORE_FILE - Key store file name. It should be placed next to the `https/docker-compose.yml` file
* KEY_STORE_PASSWORD - Password of keystore
* KEY_STORE_TYPE - Key store type (e.g. PKCS12)

### How to run in standalone mode

The files in the [standalone](./standalone) folder start every service in standalone web application mode.
Unlike the microservices system examples, there is no Config, Discovery or Gateway service &ndash; each module
runs on its own by setting `EUREKA_CLIENT_ENABLED=false` and `SPRING_CLOUD_CONFIG_ENABLED=false`.

Each service is reachable directly on its own port and exposes its Swagger UI at `http://localhost:<port>/API/`:

| Service              | Port | Swagger UI                 |
|----------------------|------|----------------------------|
| jms-io               | 8061 | http://localhost:8061/API/ |
| jms-db               | 8062 | http://localhost:8062/API/ |
| jms-structure        | 8063 | http://localhost:8063/API/ |
| jms-calculations     | 8064 | http://localhost:8064/API/ |
| jms-markush          | 8065 | http://localhost:8065/API/ |
| jms-structurechecker | 8066 | http://localhost:8066/API/ |
| jms-reactor          | 8067 | http://localhost:8067/API/ |
| jms-taskmanager      | 8068 | http://localhost:8068/API/ |

The `jms-db` and `jms-reactor` services are wired to `jms-taskmanager` (and vice versa) so that asynchronous
operations work across the standalone services:

* `jms-db` and `jms-reactor` reach the Task Manager through `COM_CHEMAXON_TASKMANAGER_SERVICE=http://jms-taskmanager:8068`.
* `jms-taskmanager` reaches back through `COM_CHEMAXON_TASKMANAGER_SERVICE_JWSDB=http://jms-db:8062` and
`COM_CHEMAXON_TASKMANAGER_SERVICE_JWS-REACTOR=http://jms-reactor:8067`.

You can remove any service you do not need from the `docker-compose.yml` file. Because only `jms-db` and `jms-taskmanager` depend on
PostgreSQL and only `jms-db` and `jms-reactor` depend on the Task Manager, several parts of the compose file are optional depending on what you keep:

* **`postgres` service and database environment variables** are only needed if you keep `jms-db` or `jms-taskmanager`.
`jms-db` uses the `CXN_DB_*` variables and `jms-taskmanager` uses the `CXN_TASK_*` variables (both also list `postgres`
under `depends_on`); every other service is stateless. If you remove both `jms-db` and `jms-taskmanager`, also remove the
`postgres` service, its `depends_on` references, the `POSTGRES_*` variables from the `.env` file, and the `configs` block
that creates the Task Manager database.
* **Task Manager cross-service environment variables** are only needed if you keep `jms-taskmanager`. If you remove it,
also remove the `COM_CHEMAXON_TASKMANAGER_SERVICE` variable from `jms-db` and `jms-reactor`. Conversely, if you remove
`jms-db` or `jms-reactor`, drop the matching `COM_CHEMAXON_TASKMANAGER_SERVICE_JWSDB` /
`COM_CHEMAXON_TASKMANAGER_SERVICE_JWS-REACTOR` entry from `jms-taskmanager`.

In short, when you drop a service you do not need, remember to also remove the related Postgres service and Task Manager
wiring described above &ndash; not just the service block itself &ndash; otherwise the remaining services will try to
reach endpoints that no longer exist.

### How to run with load balancing

The files in the [load-balancing](./load-balancing) folder start two instances of Database Search Service which connect
to a common PostgreSQL database. In this setup, Gateway service handles the load balancing, but you can use any other
external solution for that if you configure the services in [standalone](./standalone) mode.

If needed, even more instances of Database Search Service can be started, without changing any additional configuration.

The stateless services of JChem Microservices can also be scaled up similarly. Their configuration is even simpler as
their instances do not need a common resource.

### How to set up OpenTelemetry zero code instrumentation

Attention to the following points:

* Ensure that the OpenTelemetry agent is compatible with your application's Java version.
* Review and adjust the environment variables according to your specific requirements.
* Monitor the logs for any errors or warnings related to OpenTelemetry setup.
* Slight performance degradation may occur due to the additional overhead of instrumentation.

#### 1. Download OpenTelemetry

Download opentelemetry-javaagent.jar from [Releases](https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases) of the opentelemetry-java-instrumentation repository and place the JAR in your preferred directory. The JAR file contains the agent and instrumentation libraries.

#### 2. Configure in Docker

In `docker-compose.yml`, add the following to each service for which you want to enable OpenTelemetry.

```
    environment:
      - "JAVA_TOOL_OPTIONS=-javaagent:/home/cxnapp/opentelemetry/opentelemetry-javaagent.jar"
      - "OTEL_SERVICE_NAME=SERVICE_NAME"
      - "OTEL_TRACES_EXPORTER=otlp"
      - "OTEL_METRICS_EXPORTER=otlp"
      - "OTEL_EXPORTER_OTLP_PROTOCOL=http/protobuf"
      - "OTEL_EXPORTER_OTLP_ENDPOINT=EXPORTER_URL"
      - "OAuth2ClientEndpointProtectionEnabled=false"
    volumes:
      - PATH_ON_YOUR_MACHINE/opentelemetry-javaagent.jar:/home/cxnapp/opentelemetry/opentelemetry-javaagent.jar
```

Replace the PATH_ON_YOUR_MACHINE, SERVICE_NAME and EXPORTER_URL with the proper values.

#### 3. Add OpenTelemetry backend to Docker compose (optional)

An easy way to test your OpenTelemetry setup is to add the [grafana/otel-lgtm](https://hub.docker.com/r/grafana/otel-lgtm) image to your Docker compose.

- Add a new service to your Docker compose:

```
  otel-lgtm:
    image: grafana/otel-lgtm
    ports:
      - "3000:3000"
      - "4317:4317"
      - "4318:4318"
```

- Add to each of your monitored services:

```
    depends_on:
      otel-lgtm:
        condition: service_healthy
```

- Replace the EXPORTER_URL in the `environment:` list of monitored services with "http://otel-lgtm:4318"

- After starting the containers, go to `localhost:3000`.

#### 4. Performance degradation mitigation (optional)

##### General
To mitigate performance degradation, consider the following strategies:
- Optimize the OpenTelemetry configuration by adjusting sampling rates and spans.
- Use a more efficient exporter, such as the OTLP exporter with compression enabled.
- Regularly update the OpenTelemetry agent to the latest version for performance improvements.

##### Docker configuration

In `docker-compose.yml`, add OpenTelemetry variables next to your existing `OTEL_*` entries on each instrumented service. Examples below use the same `environment:` list format.

**Reduce trace volume (sampling)**

```
      - "OTEL_TRACES_SAMPLER=parentbased_traceidratio"
      - "OTEL_TRACES_SAMPLER_ARG=0.1"
```

Adjust `OTEL_TRACES_SAMPLER_ARG` (for example `0.05` for 5%, `0.01` for 1%). Other samplers include `always_on`, `always_off`, `traceidratio`, `parentbased_always_on`, and `parentbased_always_off`.

**Stop exporting traces or metrics**

```
      - "OTEL_TRACES_EXPORTER=none"
      - "OTEL_METRICS_EXPORTER=none"
      - "OTEL_LOGS_EXPORTER=none"
```
