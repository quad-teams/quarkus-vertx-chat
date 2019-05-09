package team.quad;

import io.vertx.axle.core.eventbus.EventBus;
import io.vertx.axle.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@Path("/")
public class ChatResource {

  @Inject
  EventBus eventBus;

  @POST
  @Path("join")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public CompletionStage<User> join(@Valid User user) {
    return eventBus.<JsonObject>send("join", JsonObject.mapFrom(user))
      .thenApply(Message::body)
      .thenApply(jsonObject -> jsonObject.mapTo(User.class));
  }

  @PUT
  @Path("leave")
  @Consumes(MediaType.APPLICATION_JSON)
  public CompletionStage<String> leave(@Email String username) {
    return eventBus.<String>send("leave", username)
      .thenApply(Message::body)
      .exceptionally(Throwable::getMessage);
  }

  @GET
  @Path("members")
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<JsonArray> members() {
    return eventBus.<JsonArray>send("get.members", "")
      .thenApply(Message::body);
  }

  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("chat")
  public CompletionStage<String> chat(@Valid ChatMessage message) {
    return eventBus.<String>send("chat", JsonObject.mapFrom(message))
      .thenApply(Message::body)
      .exceptionally(Throwable::getMessage);
  }

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @Path("stream")
  public Publisher<String> stream() {
    return eventBus.<String>consumer("stream").toPublisherBuilder()
      .map(Message::body)
      .buildRs();
  }
}
