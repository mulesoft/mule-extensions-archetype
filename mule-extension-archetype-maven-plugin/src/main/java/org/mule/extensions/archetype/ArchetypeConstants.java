/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extensions.archetype;

/**
 * A set of constant values used in the mojo, such us property names and some fixed values.
 *
 * @since 1.0
 */
public class ArchetypeConstants {

  public static final String ARCHETYPE_GID_PROP = "archetypeGroupId";
  public static final String ARCHETYPE_AID_PROP = "archetypeArtifactId";
  public static final String ARCHETYPE_VERSION_PROP = "archetypeVersion";
  public static final String ARCHETYPE_INTERACTIVE_MODE_PROP = "interactiveMode";

  public static final String EXTENSIONS_ARCHETYPE_GID = "org.mule.extensions";
  public static final String EXTENSIONS_ARCHETYPE_AID = "mule-extensions-archetype";
  public static final String EXTENSIONS_ARCHETYPE_VERSION = "1.1.2";

  public static final String EXTENSION_NAME = "extensionName";
  public static final String EXTENSION_VERSION = "version";
  public static final String GROUP_ID = "groupId";
  public static final String ARTIFACT_ID = "artifactId";
  public static final String PACKAGE = "package";
}
