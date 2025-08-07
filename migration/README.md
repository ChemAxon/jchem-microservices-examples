# Migration from JChem Webservices to JChem Microservices

This project contains a tool to help you transfer your data _from JChem Webservices Classic to JChem Microservices_.

## Prerequisites

If you would like to run this project please set ```chemaxonRepositoryUser``` and ```chemaxonRepositoryPassword``` in ```gradle.properties```.

## How To Transfer Data

First you have to export your existing data from JChem Webservices to one (or more) molecule file(s).

After that, put the file(s) into the ```input``` folder inside the project folder.

Now run the following command

- On linux: ```./gradlew transferData```
- On windows: ```gradlew transferData```

This will create the _json file(s)_ from your data which you can find in the ```output``` folder.

Now, you can use the file(s) to ```batch insert``` your data with JChem Microservices using the Database Search Service API.
