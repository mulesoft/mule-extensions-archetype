/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extension.archetype.it;

import static java.lang.Boolean.FALSE;
import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.mule.extensions.archetype.ArchetypeConstants.ARCHETYPE_INTERACTIVE_MODE_PROP;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSIONS_ARCHETYPE_AID;
import static org.mule.extensions.archetype.ArchetypeConstants.ARCHETYPE_AID_PROP;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSIONS_ARCHETYPE_GID;
import static org.mule.extensions.archetype.ArchetypeConstants.ARCHETYPE_GID_PROP;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSIONS_ARCHETYPE_VERSION;
import static org.mule.extensions.archetype.ArchetypeConstants.ARCHETYPE_VERSION_PROP;
import static org.mule.extensions.archetype.ArchetypeConstants.ARTIFACT_ID;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSION_NAME;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSION_NAME_NO_SPACES;
import static org.mule.extensions.archetype.ArchetypeConstants.GROUP_ID;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSION_VERSION;
import static org.mule.extensions.archetype.ArchetypeConstants.PACKAGE;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.tika.io.IOUtils;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class ExtensionArchetypeGenerationTestCase {

  private static final File ROOT = new File("target/test-classes/");
  private static final String TEMPORAL_EXTENSION_MODEL_PATH_TEMPLATE = ROOT.getAbsolutePath() +"/%s/target/temporal-extension-model.json";

  private static final String JAVA_HOME = "JAVA_HOME";
  private static final String TEST_EXTENSION_NAME = "Basic";
  private static final String TEST_EXTENSION_NAME_NO_SPACES = "Basic";
  private static final String TEST_EXTENSION_GID = "org.mule.test.extension";
  private static final String TEST_EXTENSION_AID = "test-extension";
  private static final String TEST_EXTENSION_VERSION = "1.0.0";
  private static final String TEST_EXTENSION_PACKAGE = "org.mule.test";
  private static final String MAVEN_OPTS = getProperty("argLine", "");
  private static final String MAVEN_SETTINGS_PROPERTY = "mule.extension.archetype.testSettings";

  private Verifier verifier;

  @Test
  public void generateWithCustomProps() throws Exception {
    generate(TEST_EXTENSION_GID, TEST_EXTENSION_AID, TEST_EXTENSION_VERSION, getAllProperties("BasicName", "BasicName"));
  }

  @Test
  public void generateWithNameWithSpaces() throws Exception {
    generate(TEST_EXTENSION_GID, TEST_EXTENSION_AID, TEST_EXTENSION_VERSION, getAllProperties("Basic Pepe Extension", "BasicPepeExtension"));
  }

  @Test
  public void generateWithNameWithHyphens() throws Exception {
    generate(TEST_EXTENSION_GID, TEST_EXTENSION_AID, TEST_EXTENSION_VERSION, getAllProperties("my-pepe-extension", "MyPepeExtension"));
  }

  @Test
  public void generateDefault() throws Exception {
    // default values specified in the archetype
    generate("org.mule.extension", "mule-basic-extension", "1.0.0-SNAPSHOT", getPluginProperties());
  }

  private void generate(String groupId, String artifactId, String version, Properties pluginProperties) throws Exception {
    // Cleans up the test context
    clean(groupId, artifactId, version);

    verifier.setSystemProperties(pluginProperties);
    verifier.setAutoclean(false);
    verifier.executeGoal("archetype:generate", getEnvVars());
    verifier.setMavenDebug(true);
    verifier.verifyErrorFreeLog();

    // Since creating the archetype was successful, we now want to actually build the generated project
    verifier = new Verifier(ROOT.getAbsolutePath() + "/" + artifactId);

    if (System.getProperty(MAVEN_SETTINGS_PROPERTY) != null) {
      List cliProps = new ArrayList<String>();
      cliProps.add("-X -s " + System.getProperty(MAVEN_SETTINGS_PROPERTY, (String) null));
      verifier.setCliOptions(cliProps);
    }
    verifier.addCliOption("-DskipDocumentation");
    verifier.setMavenDebug(true);
    verifier.executeGoals(asList("package"), getEnvVars());
    verifier.verifyErrorFreeLog();
    Optional<String> extensionName = Optional.ofNullable(pluginProperties.getProperty(EXTENSION_NAME));
    verifyExtensionModel(extensionName.orElse(TEST_EXTENSION_NAME), artifactId);
  }

  private void verifyExtensionModel(String extensionName, String artifactId) throws Exception {
    String normalizedExtensionName = extensionName.toLowerCase().replace(" ", "-");
    String actualExtensionModel = getActualExtensionModel(artifactId);
    String expectedExtensionModel = getExpectedExtensionModel(normalizedExtensionName) ;
    JSONAssert.assertEquals(expectedExtensionModel, actualExtensionModel,true);
  }

  private String getExpectedExtensionModel(String extensionName) throws Exception {
    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(extensionName+".json");
    return IOUtils.toString(is);
   }

  private String getActualExtensionModel(String artifactId) throws Exception{
    return readFileToString(new File(String.format(TEMPORAL_EXTENSION_MODEL_PATH_TEMPLATE, artifactId)));
  }

  private void clean(String groupId, String artifactId, String version) throws VerificationException, IOException {
    /*
     * We must first make sure that any artifact created
     * by this test has been removed from the local
     * repository. Failing to do this could cause
     * unstable test results. Fortunately, the verifier
     * makes it easy to do this.
     */
    verifier = new Verifier(ROOT.getAbsolutePath());

    // Deleting a former created artifact from the archetype to be tested
    verifier.deleteArtifact(groupId, artifactId, version, null);

    // Delete the created maven project
    verifier.deleteDirectory(TEST_EXTENSION_AID);
  }

  private static Properties getAllProperties(String extensionName, String extensionNameNoSpaces) {
    Properties props = getPluginProperties();

    // Extensions archetype properties
    props.put(EXTENSION_NAME, extensionName);
    props.put(EXTENSION_NAME_NO_SPACES, extensionNameNoSpaces);
    props.put(GROUP_ID, TEST_EXTENSION_GID);
    props.put(ARTIFACT_ID, TEST_EXTENSION_AID);
    props.put(EXTENSION_VERSION, TEST_EXTENSION_VERSION);
    props.put(PACKAGE, TEST_EXTENSION_PACKAGE);

    return props;
  }

  private static Properties getPluginProperties() {
    Properties props = System.getProperties();

    // Archetype plugin properties
    props.put(ARCHETYPE_GID_PROP, EXTENSIONS_ARCHETYPE_GID);
    props.put(ARCHETYPE_AID_PROP, EXTENSIONS_ARCHETYPE_AID);
    props.put(ARCHETYPE_VERSION_PROP, EXTENSIONS_ARCHETYPE_VERSION);
    props.put(ARCHETYPE_INTERACTIVE_MODE_PROP, FALSE.toString());

    return props;
  }

  private static Properties getEnvVars() {
    Properties vars = new Properties();

    vars.put("MAVEN_OPTS", MAVEN_OPTS);
    vars.put(JAVA_HOME, getenv(JAVA_HOME));

    return vars;
  }
}
