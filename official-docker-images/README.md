# How to use the official JChem Microservices Docker images

> **Disclaimer**
>
> This is just a quick example. If you use this example to build your system,
then be prepared to do your own security settings. Feel free to change any parts
to make your life easier, this version only solves the purpose to get you started.

## Prerequisites and setup

### Docker

In order to download the official Docker images of JChem Microservices, you need to install [Docker](https://www.docker.com/) on your system and configure it to access the [Chemaxon Public Repository](https://docs.chemaxon.com/display/docs/general_public-repository.md). After acquiring your credentials, you can simply call

```
docker login chemaxon.jfrog.io
```

to set up Docker to access the repository.

### License

For these examples, you will also need licenses for the JChem Microservices modules. We show examples for both license key and
license file usage. Follow step-by-step setup below based on your license type.

### How to run

#### With license file

1. Navigate to [license-file](./license-file)
2. Put your `license.cxl` file to this folder, next to the docker compose file.
3. Check the JChem Microservices version in the `.env` file, and change it to the one you need
4. Execute `docker-compose up`

#### With license key

1. Navigate to [license-key](./license-key)
2. Fill `LICENSE_KEY` variable in `.env` file with your `license key`
3. Check the JChem Microservices version in the `.env` file, and change it to the one you need
4. Execute `docker-compose up`

## Config and additional info

### What is happening in this example?

The docker-compose file has a description of the JChem Microservices system
wiht a central license server. The license server is a module we build during
__UP__ command, and it is based on [Nginx](https://hub.docker.com/_/nginx) image.

### How to access the system?

In the end the port 8080 will be open and forwarded towards the outer world. 

### How to update the license file?

If you already have a license server then the related docker image (`cxn/jms/nginx-license-server:latest`) should be deleted first.
The new license file should be placed in the folder and docker compose can be started &ndash; it re-builds the license server automatically.

### What could be on volumes?

* `/app/jws/jws-db/data` folder contains all the data you upload to the Database Search Service
* `/app/jws/jws-config/common-config` folder contains all the configurations for the services
* `/app/jws/logs/` folder contains all the log files

These folders in a production environment could be on volumes depending on your 
needs, but this is only a kick-starter example, it does not aim to teach docker 
basics. If you want to restructure the containers, feel free. If you would like 
to save more persistent data, than it is up to you.

### How to start with HTTPS

The files in the [https](./https) folder are a variant of the `license-file` example where `jms-gateway` is configured with HTTPS access.
Necessary configurations in the [https/.env](https/.env) file should be updated before execution:
* KEY_STORE_FILE - Key store file name. It should be placed next to the `https/docker-compose.yml` file
* KEY_STORE_PASSWORD - Password of keystore
* KEY_STORE_TYPE - Key store type (e.g. PKCS12)

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
