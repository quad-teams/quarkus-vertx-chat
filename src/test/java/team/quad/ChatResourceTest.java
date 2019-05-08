package team.quad;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@QuarkusTest
public class ChatResourceTest {

    @Test
    public void testUserEquality() {
        User user1 = new User("john@quad.team", "John");
        User user2 = new User("john@quad.team", "John");

        assertEquals(user1, user2);
        assertNotSame(user1, user2);
    }

    @Test
    public void testJoinGroupEndpointExists() {
        User user = new User("christopher@quad.team", "Christopher");

        given()
            .contentType("application/json")
            .body(user)
            .when().post("/join").then()
                .statusCode(200);
    }

    @Test
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

}