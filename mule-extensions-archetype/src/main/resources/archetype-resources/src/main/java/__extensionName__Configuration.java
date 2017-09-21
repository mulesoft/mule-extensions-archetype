package ${package};

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

@Operations(${extensionName}Operations.class)
@ConnectionProviders(${extensionName}ConnectionProvider.class)
public class ${extensionName}Configuration {

  @Parameter
  private String configParameter;

  public String getConfigParameter(){
    return configParameter;
  }
}
