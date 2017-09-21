package ${package};

public final class ${extensionName}Connection {

  private final String id;

  public ${extensionName}Connection(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void stop() {
    // do something to stop this connection!
  }
}
