package ${package};

import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

public class ${extensionName}Operations {

  public String retrieveInfo(@Config ${extensionName}Configuration configuration, @Connection ${extensionName}Connection connection){
    return "Using Configuration [" + configuration.toString() + "] with Connection id [" + connection.getId() + "]";
  }

  public String sayHi(String person) {
    return buildHelloMessage(person);
  }

  /**
   * Private Methods are not exposed as operations
   */
  private String buildHelloMessage(String person) {
    return "Hello " + person + "!!!";
  }
}
