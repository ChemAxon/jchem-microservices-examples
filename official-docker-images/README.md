# Disclaimer

This is just a quick example. If you use this example to build your system,
than be prepared to do your own securty settings. Feel free to change any parts
to make your life easier, this version only solves the purpose to get you started.

# About

This is an example of how to use the official docker images.
For this example to use you will need licenses for the JChem Microservices
Modules (downloaded to this folder as `license.cxl`) and __docker__ and 
__docker-compose__ installed on your system.

# How to run

execute `docker-compose up`

# What is happening in this example?

The docker-compose file has a description of the JChem Microservices system
whit a central license server. The license server is a module we build during 
__UP__ command, and it is based on [Nginx](https://hub.docker.com/_/nginx) image.

# How to access the system?

In the end the port 8080 will be open and forwared towards the outer world. 

# What could be on volumes?

* `/app/jws/jws-db/data` folder contains all the data you uplaod to JMS DB.
* `/app/jws/jws-config/common-config` folder conatins all the configurations for the services
* `/app/jws/logs/` fodler contains all the log files

These folders in a production environment could be on volumes depending on your 
needs, but this is only a kick-starter example, it does not aim to teach docker 
basics. If you want to restructure the conatiners, feel free. If you would like 
to save more persistent data, than it is up to you.
