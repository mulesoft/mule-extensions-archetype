/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extension.archetype.it;

import static java.lang.Boolean.FALSE;
import static java.lang.System.getenv;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
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
import static org.mule.extensions.archetype.ArchetypeConstants.PACKAGE;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.junit.Before;
import org.junit.Test;

public class ExtensionArchetypeGenerationTestCase {

  private static final File ROOT = new File("target/test-classes/");

  private static final String JAVA_HOME = "JAVA_HOME";
  private static final String TEST_EXTENSION_NAME = "Basic";
  private static final String TEST_EXTENSION_GID = "org.mule.test.extension";
  private static final String TEST_EXTENSION_AID = "test-extension";
  private static final String TEST_EXTENSION_VERSION = "1.0.0";
  private static final String TEST_EXTENSION_PACKAGE = "org.mule.test";

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

    // Delete the created maven project
    verifier.deleteDirectory(TEST_EXTENSION_AID);
  }

  @Test
  public void generateWithCustomProps() throws VerificationException, IOException {
    verifier.setSystemProperties(getProperties());
    verifier.setAutoclean(false);
    verifier.executeGoal("org.mule.extensions:mule-extensions-archetype-maven-plugin:generate");
    verifier.setMavenDebug(true);
    verifier.verifyErrorFreeLog();

    // Since creating the archetype was successful, we now want to actually build the generated project
    verifier = new Verifier(ROOT.getAbsolutePath() + "/" + TEST_EXTENSION_AID);
    verifier.setMavenDebug(true);
    verifier.executeGoals(asList("compile", "test"), singletonMap(JAVA_HOME, getenv(JAVA_HOME)));
    verifier.verifyErrorFreeLog();
  }

  private static Properties getProperties() {
    Properties props = System.getProperties();

    // Archetype plugin properties
    props.put(ARCHETYPE_GID_PROP, EXTENSIONS_ARCHETYPE_GID);
    props.put(ARCHETYPE_AID_PROP, EXTENSIONS_ARCHETYPE_AID);
    props.put(ARCHETYPE_VERSION_PROP, EXTENSIONS_ARCHETYPE_VERSION);
    props.put(ARCHETYPE_INTERACTIVE_MODE_PROP, FALSE.toString());

    // Extensions archetype properties
    props.put(EXTENSION_NAME, TEST_EXTENSION_NAME);
    props.put(GROUP_ID, TEST_EXTENSION_GID);
    props.put(ARTIFACT_ID, TEST_EXTENSION_AID);
    props.put(EXTENSION_VERSION, TEST_EXTENSION_VERSION);
    props.put(PACKAGE, TEST_EXTENSION_PACKAGE);

    return props;
  }
}
