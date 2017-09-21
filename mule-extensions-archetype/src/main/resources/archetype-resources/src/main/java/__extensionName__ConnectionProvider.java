package ${package};

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ${extensionName}ConnectionProvider implements PoolingConnectionProvider<${extensionName}Connection> {

  private final Logger LOGGER = LoggerFactory.getLogger(CacaConnectionProvider.class);

  @Parameter
  private String requiredParameter;

  @Parameter
  @Optional(defaultValue = "100")
  private int optionalParameter;

  @Override
  public ${extensionName}Connection connect() throws ConnectionException {
    return new ${extensionName}Connection(requiredParameter + ":" + optionalParameter);
  }

  @Override
  public void disconnect(${extensionName}Connection connection) {
    try {
      connection.stop();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting [" + connection.getId() + "]: " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(${extensionName}Connection connection) {
    return ConnectionValidationResult.success();
  }
}
