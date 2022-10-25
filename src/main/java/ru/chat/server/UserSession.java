package ru.chat.server;

import java.io.PrintWriter;

import static java.util.Objects.requireNonNull;

public class UserSession implements MessageSubscription.EventReceiver {
    private String login;
    private PrintWriter writer;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        requireNonNull(login, "login shouldn't be null");
        if (this.login != null) {
            throw new IllegalStateException("login change is not allowed");
        }

        this.login = login;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        requireNonNull(writer);
        this.writer = writer;
    }

    @Override
    public void onNewMessage(String message) {
        writer.println(message);
    }
}