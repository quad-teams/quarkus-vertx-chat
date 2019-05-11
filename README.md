# quarkus-vertx-chat
A rudimentary messaging application showcasing Quarkus, Vert.x, Reactive Streams and server-sent events (SSE).

For some more backround be sure to read the associated [article](https://quad.team/blog/messaging-with-quarkus-and-vertx).

## Highlights
### Creating this project
If you want to start this project from scratch you can like so:

```bash
mvn io.quarkus:quarkus-maven-plugin:0.14.0:create \
    -DprojectGroupId=team.quad \
    -DprojectArtifactId=quarkus-vertx-chat \
    -Dextensions="hibernate-validator, resteasy-jsonb, smallrye-openapi, vertx"
```

Alternatively, extensions can be added one by one after the base project is created:

```bash
mvn io.quarkus:quarkus-maven-plugin:0.14.0:create \
    -DprojectGroupId=team.quad \
    -DprojectArtifactId=quarkus-vertx-chat
```

then

```bash
cd quarkus-vertx-chat
./mvnw quarkus:add-extension -Dextensions=resteasy-jsonb
./mvnw quarkus:add-extension -Dextensions=hibernate-validator
./mvnw quarkus:add-extension -Dextensions="smallrye-openapi"
./mvnw quarkus:add-extension -Dextensions=vertx
```

### Start the application
This project ships with the correct version of mvn if you prefer to use that. You may start the project in development mode like so:

```bash
./mvnw quarkus:dev
```

In development mode, Quarkus will recompile and changed files and restart the application on each request.

### Functional Testing
Here is an example of a functional test from the set of [tests in this repository](src/test/java/team/quad/ChatResourceTest.java):

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

### A simple UI
This version of the application ships with a [simple version](/src/main/resources/META-INF/resources/simple.html) of the UI for demo purposes and a more elaborate one that is more convenient to use and with more asthetic appeal. The simple version is as follows:

```html
<!doctype html>
<html>
  <head>
    <meta charset="utf-8"/>
    <script type="application/javascript" src="streaming.js"></script>
  </head>
  <body>
    <div id="container"></div>
  </body>
</html>
```

`streaming.js`:

```javascript
var eventSource = new EventSource("/stream");
eventSource.onmessage = function (event) {
    var container = document.getElementById("container");
    var paragraph = document.createElement("p");
    paragraph.innerHTML = event.data;
    container.appendChild(paragraph);
};
```
