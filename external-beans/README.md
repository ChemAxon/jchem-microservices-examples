# JChem Microservices external beans example

Example for using external beans with JChem Microservices

This small example contains a custom filter (in `my.custom.filter.MyCustomFilter` class) and a "more complex" configuration class (in `my.custom.beans.Config` class).

## How to try out

1. Build the example project with the command `./gradlew build`
2. Copy the jar file from `build/libs/` folder to the `extra-libs` folder of your JMS installation.
3. Set `cxn.jms.external.config.dir` property to the path of the `xml` folder
4. Start JChem Microservices

This way the 2 `*Config.xml` file will be read, and the decalerd beans will be instatiated.

Find more information about bean schema, here: https://docs.spring.io/spring-framework/reference/core/appendix/xsd-schemas.html
