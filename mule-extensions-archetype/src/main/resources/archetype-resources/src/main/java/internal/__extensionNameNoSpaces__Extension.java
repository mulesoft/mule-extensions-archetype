/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package ${package}.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
#set($xsdName = ${extensionName.toLowerCase().replaceAll("(?i)extension", "").replaceAll("(?i)connector", "").trim().replace(" ", "-").replaceAll("-$", "")})
@Xml(prefix = "${xsdName}")
@Extension(name = "${extensionName}")
@Configurations(${extensionNameNoSpaces}Configuration.class)
public class ${extensionNameNoSpaces}Extension {

}
