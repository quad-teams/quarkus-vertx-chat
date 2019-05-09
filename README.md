# quarkus-vertx-chat
A rudimentary messaging application showcasing Quarkus, Vert.x, Reactive Streams and server-sent events (SSE)

## Highlights
### Creating this project
If you want to start this project from scratch you can like so:

```bash
mvn io.quarkus:quarkus-maven-plugin:0.14.0:create \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=vertx-quickstart \
    -Dextensions="hibernate-validator, resteasy-jsonb, smallrye-openapi, vertx"
```

Alternatively, extensions can be added one by one after the base project is created:

```bash
mvn io.quarkus:quarkus-maven-plugin:0.14.0:create \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=vertx-quickstart
```

then

```bash
./mvnw quarkus:add-extension -Dextensions=resteasy-jsonb
./mvnw quarkus:add-extension -Dextensions=hibernate-validator
./mvnw quarkus:add-extension -Dextensions="smallrye-openapi"
./mvnw quarkus:add-extension -Dextensions=vertx
```

### Functional Testing
Here is a functional test:

```java
public void testJoinGroupResponse() {
    User user = new User("christopher@quad.team", "Christopher");

    User result =
          given()
            .contentType("application/json")
            .body(user)
            .when().post("/join")
            .as(User.class);

    assertEquals("christopher@quad.team", result.getUsername());
    assertEquals("Christopher", result.getAlias());
}
```

A JSON binding library will be necessary, such as Jackson, to make use of certain [REST-assured](http://rest-assured.io/) features that involve converting JSON to Java objects and vice-versa:
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <scope>test</scope>
</dependency>
```
