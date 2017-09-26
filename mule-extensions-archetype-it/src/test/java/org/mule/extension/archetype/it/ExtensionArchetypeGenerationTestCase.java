/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extension.archetype.it;

import static java.lang.Boolean.FALSE;
import static org.mule.extensions.archetype.ArchetypeConstants.ARCHETYPE_INTERACTIVE_MODE_PROP;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSIONS_ARCHETYPE_AID;
import static org.mule.extensions.archetype.ArchetypeConstants.ARCHETYPE_AID_PROP;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSIONS_ARCHETYPE_GID;
import static org.mule.extensions.archetype.ArchetypeConstants.ARCHETYPE_GID_PROP;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSIONS_ARCHETYPE_VERSION;
import static org.mule.extensions.archetype.ArchetypeConstants.ARCHETYPE_VERSION_PROP;
import static org.mule.extensions.archetype.ArchetypeConstants.ARTIFACT_ID;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSION_NAME;
import static org.mule.extensions.archetype.ArchetypeConstants.GROUP_ID;
import static org.mule.extensions.archetype.ArchetypeConstants.EXTENSION_VERSION;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.junit.Before;
import org.junit.Test;

public class ExtensionArchetypeGenerationTestCase {

  private static final File ROOT = new File("target/test-classes/");

  private static final String TEST_EXTENSION_NAME = "Test";
  private static final String TEST_EXTENSION_GID = "org.mule.test.extension";
  private static final String TEST_EXTENSION_AID = "test-extension";
  private static final String TEST_EXTENSION_VERSION = "1.0.0";

  private Verifier verifier;

  @Before
  public void setUp() throws VerificationException, IOException {
    /*
     * We must first make sure that any artifact created
     * by this test has been removed from the local
     * repository. Failing to do this could cause
     * unstable test results. Fortunately, the verifier
     * makes it easy to do this.
     */
    verifier = new Verifier(ROOT.getAbsolutePath());

    // Deleting a former created artifact from the archetype to be tested
    verifier.deleteArtifact(TEST_EXTENSION_GID, TEST_EXTENSION_AID, TEST_EXTENSION_VERSION, null);
    verifier.deleteArtifact("org.mule.extension", "mule-basic-extension", "1.0.0-SNAPSHOT", null);

    // Delete the created maven project
    verifier.deleteDirectory(TEST_EXTENSION_AID);
    verifier.deleteDirectory("mule-basic-extension");
  }

  @Test
  public void generateWithCustomProps() throws VerificationException {
    Properties props = getMavenArchetypePluginProperties();

    // Extensions archetype properties
    props.put(EXTENSION_NAME, TEST_EXTENSION_NAME);
    props.put(GROUP_ID, TEST_EXTENSION_GID);
    props.put(ARTIFACT_ID, TEST_EXTENSION_AID);
    props.put(EXTENSION_VERSION, TEST_EXTENSION_VERSION);

    generateArchetype(props, TEST_EXTENSION_AID, TEST_EXTENSION_NAME);
  }

  @Test
  public void generateWithNoProps() throws VerificationException {
    generateArchetype(getMavenArchetypePluginProperties(), "mule-basic-extension", "Basic");
  }

  private void generateArchetype(Properties properties, String extAID, String extName) throws VerificationException {
    verifier.setSystemProperties(properties);
    verifier.setAutoclean(false);

    // The Command Line Options (CLI) are passed to the verifier as a list.
    verifier.executeGoal("archetype:generate");
    verifier.setMavenDebug(true);

    verifier.verifyErrorFreeLog();

    // Since creating the archetype was successful, we now want to actually build the generated project
    verifier = new Verifier(ROOT.getAbsolutePath() + "/" + extAID);
    verifier.setSystemProperties(properties);

    verifier.assertFilePresent("src/main/java/org/mule/extension/" + extName + "ConnectionProvider.java");
    verifier.assertFilePresent("src/main/java/org/mule/extension/" + extName + "Extension.java");
    verifier.assertFilePresent("src/main/java/org/mule/extension/" + extName + "Configuration.java");
    verifier.assertFilePresent("src/main/java/org/mule/extension/" + extName + "Operations.java");

//    verifier.setAutoclean(true);
//    verifier.setMavenDebug(true);
    verifier.executeGoal("verify");
//    verifier.verifyErrorFreeLog();
  }

  private static Properties getMavenArchetypePluginProperties() {
    Properties props = System.getProperties();
    // Archetype plugin properties
    props.put(ARCHETYPE_GID_PROP, EXTENSIONS_ARCHETYPE_GID);
    props.put(ARCHETYPE_AID_PROP, EXTENSIONS_ARCHETYPE_AID);
    props.put(ARCHETYPE_VERSION_PROP, EXTENSIONS_ARCHETYPE_VERSION);
    props.put(ARCHETYPE_INTERACTIVE_MODE_PROP, FALSE.toString());
    return props;
  }
}
