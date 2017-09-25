/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extensions.archetype;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.codehaus.plexus.util.StringUtils.capitalise;
import static org.codehaus.plexus.util.StringUtils.trim;
import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.name;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

import java.util.Scanner;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "generate", requiresProject = false)
public class ExtensionArchetypeGeneratorMojo extends AbstractMojo {

  private static final String ARCHETYPE_GID = "org.mule.extensions";
  private static final String ARCHETYPE_AID = "mule-extensions-archetype";
  private static final String ARCHETYPE_VERSION = "1.0.0-SNAPSHOT";

  private static final String NAME = "extensionName";
  private static final String VERSION = "version";
  private static final String GROUP_ID = "groupId";
  private static final String ARTIFACT_ID = "artifactId";
  private static final String PACKAGE = "package";

  @Component
  private MavenProject project;

  @Component
  private MavenSession session;

  @Component
  private BuildPluginManager pluginManager;

  @Parameter(property = NAME)
  private String extensionName;

  @Parameter(property = GROUP_ID)
  private String extensionGroupId;

  @Parameter(property = ARTIFACT_ID)
  private String extensionArtifactId;

  @Parameter(property = VERSION)
  private String extensionVersion;

  @Parameter(property = PACKAGE)
  private String mainPackage;

  public void execute() throws MojoExecutionException, MojoFailureException {
    initialise();
    try {
      executeArchetype();
    } catch (Exception e) {
      e.printStackTrace();
      debugValue(NAME, extensionName);
      debugValue(GROUP_ID, extensionGroupId);
      debugValue(ARTIFACT_ID, extensionArtifactId);
      debugValue(VERSION, extensionVersion);
      debugValue(PACKAGE, mainPackage);
      throw new MojoFailureException("Failed to create project with the provided data: " + e.getMessage());
    }
  }

  private void executeArchetype() throws MojoExecutionException {

    // Sets the properties to the project so they can be fetched when generating the artifact
    session.getUserProperties().setProperty(NAME, extensionName);
    session.getUserProperties().setProperty(GROUP_ID, extensionGroupId);
    session.getUserProperties().setProperty(ARTIFACT_ID, extensionArtifactId);
    session.getUserProperties().setProperty(PACKAGE, mainPackage);
    session.getUserProperties().setProperty(VERSION, extensionVersion);

    executeMojo(
      plugin(groupId("org.apache.maven.plugins"), artifactId("maven-archetype-plugin"), version("3.0.1")),
      goal("generate"),
      configuration(
          element(name("archetypeGroupId"), ARCHETYPE_GID),
          element(name("archetypeArtifactId"), ARCHETYPE_AID),
          element(name("archetypeVersion"), ARCHETYPE_VERSION),
          element(name("interactiveMode"), "false")
      ),
      executionEnvironment(
        project,
        session,
        pluginManager
      )
    );
  }

  private void debugValue(String name, String value) {
    getLog().debug("- : " + name + " " + value);
  }

  private void initialise() {
    readExtensionName();
    readGroupId();
    readArtifactId();
    readVersion();
    readPackage();
  }

  private void readExtensionName() {
    if (isBlank(extensionName)) {
      System.out.println("* Enter the name of the extension (empty for default): ");
      this.extensionName = capitalise(trim(readLine()));
      displayDefaultValueMessage(NAME, extensionName);
    }
  }

  private void readGroupId() {
    if (isBlank(extensionGroupId)) {
      System.out.println("* Enter the extension's groupId (empty for default): ");
      this.extensionGroupId = trim(readLine());
      displayDefaultValueMessage(GROUP_ID, extensionGroupId);
    }
  }

  private void readArtifactId() {
    if (isBlank(extensionArtifactId)) {
      System.out.println("* Enter the extension's artifactId (empty for default): ");
      this.extensionArtifactId = trim(readLine());
      displayDefaultValueMessage(ARTIFACT_ID, extensionArtifactId);
    }
  }

  private void readVersion() {
    if (isBlank(extensionVersion)) {
      System.out.println("* Enter the extension's version (empty for default): ");
      this.extensionVersion = trim(readLine());
      displayDefaultValueMessage(VERSION, extensionVersion);
    }
  }

  private void readPackage() {
    if (isBlank(mainPackage)) {
      System.out.println("* Enter the extension's main package (empty for default): ");
      this.mainPackage = trim(readLine());
      displayDefaultValueMessage(PACKAGE, mainPackage);
    }
  }

  private String readLine() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private void displayDefaultValueMessage(String name, String prop) {
    if (isBlank(prop)) {
      System.out.println("Using default value for property [" + name + "]");
    }
  }
}
