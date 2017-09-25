# Mule Extensions Archetype

An archetype is defined as an original pattern or model from which all other things of the same kind are made.

This particular Archetype defines the structure of a Mule Basic Extension trying to provide a system that provides a consistent
means of generating Extension projects.

## Generate Project

There is a maven plugin with steps and descriptions for the required fields in order to create a new Extension Project

By running the following command you will get prompt for values.

```
mvn extensions-generator:generate
```

## Old School Archetype

You can also create a new Extension project by invoking the archetype directly with the following properties.

```
mvn archetype:generate -DarchetypeGroupId=org.mule.extensions
                       -DarchetypeArtifactId=mule-extensions-archetype
                       -DarchetypeVersion=1.0.0-SNAPSHOT
                       -DgroupId=org.mule.extension.basic
                       -DartifactId=basic-extension
                       -DextensionName=Basic
```

This is more tedious and we worked to make your life easier :), use the maven plugin.

