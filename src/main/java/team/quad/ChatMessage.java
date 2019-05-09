package team.quad;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ChatMessage {

    @Email
    String username;

    @NotEmpty
    String message;

    public ChatMessage() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
