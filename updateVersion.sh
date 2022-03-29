#!/bin/sh

set -o nounset

updatePropertiesVersion() {
  VERSION_TO_PROPERTY="$1"
  POM_PROPERTY_PATH="$2"

  # PROPERTIES argument should be passed as a literal "arrayName[@]" without $ because here using the ! it is double expanded
  # to obtiain the values and declare again the array.
  PROPERTIES=("${!3}")

  echo "Updating deps in pom: $POM_PROPERTY_PATH"

  for PROPERTY_NAME in "${PROPERTIES[@]}"
  do

      perl -0777 -i -pe "s/(<properties>.*<$PROPERTY_NAME)(.*)(\/$PROPERTY_NAME>.*<\/properties>)/\${1}>$VERSION_TO_PROPERTY<\${3}/s" "$POM_PROPERTY_PATH"
      echo "- Updating property $PROPERTY_NAME version to $VERSION_TO_PROPERTY"

  done
}

updateParentVersion() {
  VERSION_TO_PARENT="$1"
  POM_PROPERTY_PATH="$2"

  echo "Updating parent version to '$VERSION_TO_PARENT' in pom '${POM_PROPERTY_PATH}'..."
  perl -0777 -i -pe "s/(<parent>.*<version)(.*)(\/version>.*<\/parent>)/\${1}>$VERSION_TO_PARENT<\${3}/s" "$POM_PROPERTY_PATH"
}

updateExtensionsArchetypeVersionConstant(){
    VERSION_TO_CONSTANT="$1"
    FILE_WITH_CONSTANTS_PATH="$2"

    echo "Updating constant to '${VERSION_TO_CONSTANT}' in file '${FILE_WITH_CONSTANTS_PATH}'..."
    perl -0777 -i -pe "s/(public static final String EXTENSIONS_ARCHETYPE_VERSION = \")(.*?)(\"\;)(.*)/\${1}${VERSION_TO_CONSTANT}\${3}\${4}/s" "$FILE_WITH_CONSTANTS_PATH"
}



VERSION_TO_EXTENSIONS_ARCHETYPE=$1

updateExtensionsArchetypeVersionConstant "$VERSION_TO_EXTENSIONS_ARCHETYPE" mule-extension-archetype-maven-plugin/src/main/java/org/mule/extensions/archetype/ArchetypeConstants.java
mvn versions:set versions:commit -DnewVersion="${VERSION_TO_EXTENSIONS_ARCHETYPE}"
