package ${package};

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;

@Extension(name = "${extensionName.toLowerCase()}")
@Configurations(${extensionName}Configuration.class)
public class ${extensionName}Extension {

}
