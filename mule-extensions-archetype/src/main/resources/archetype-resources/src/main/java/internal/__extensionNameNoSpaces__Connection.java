/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package ${package}.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class ${extensionNameNoSpaces}Connection {

  private final String id;

  public ${extensionNameNoSpaces}Connection(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
