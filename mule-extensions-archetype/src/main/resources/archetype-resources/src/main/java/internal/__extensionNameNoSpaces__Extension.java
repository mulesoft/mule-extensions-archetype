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
