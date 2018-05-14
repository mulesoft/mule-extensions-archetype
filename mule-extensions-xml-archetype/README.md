# Smart Connector Archetype

The Smart connector archetype generates when ran generates a simple structure project with 3 files: a `pom.xml`, the `module-<name>.xml` and an assertion file for MTF (MUnit Testing Framework) named `assertion-munit-test.xml`.
If we name our connector `basic` with the following script, 

```
mvn archetype:generate                                  \
  -DarchetypeGroupId=org.mule.extensions                \
  -DarchetypeArtifactId=mule-extensions-xml-archetype   \
  -DarchetypeVersion=1.2.0-SNAPSHOT                     \
  -DgroupId=org.mule.extension.it.basic                 \
  -DartifactId=module-basic-it                  \
  -Dpackage=org.mule.extension.it.basic                 \
  -Dversion=0.1.0-SNAPSHOT                              \  
  -DmuleConnectorName=Basic
``` 
We will end up with the following structure:

```
module-basic-it
   ├── pom.xml
   └── src
       ├── main
       │   └── resources
       │       └── org
       │           └── mule
       │               └── extension
       │                   └── it
       │                       └── basic
       │                           └── module-Basic.xml
       └── test
           └── munit
               └── assertion-munit-test.xml
```

## How do the test run?
As this project is a `maven-archetype`, the way to run the tests follows the [archetype:integration-test](https://maven.apache.org/archetype/maven-archetype-plugin/integration-test-mojo.html) way, which is placing folders under `src/test/resources/projects/`, and for each folder a singular integration test will run.

Current assertions in this module for Smart Connectors archetypes assert two things: 
* a) the generated `<module>` is syntactically and semantically correct, as it builds (see `goal.txt`) and run the tests with MUnit
* b) the generated `<module>` has the same structure and *content* as the folder inside the `src/test/resources/projects/<name>/reference`. This allows to determine the generated file respects both structure and content for the entire `<module>` project.



 
