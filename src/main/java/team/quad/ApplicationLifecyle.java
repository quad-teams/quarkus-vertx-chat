package team.quad;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationLifecyle {

    @Inject
    Vertx vertx;

    void onStart(@Observes StartupEvent event) {
        vertx.deployVerticle(new GroupChatService());
    }
}
