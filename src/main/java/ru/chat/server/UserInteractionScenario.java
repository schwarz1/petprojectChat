package ru.chat.server;

import ru.chat.history.MessageHistory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInteractionScenario {
    private final UserSession session;
    private final MessageSubscription subscription;
    private final MessageHistory messages;

    public UserInteractionScenario(UserSession session, MessageSubscription subscription, MessageHistory messages) {
        this.session = session;
        this.subscription = subscription;
        this.messages = messages;
    }

    public void start(BufferedReader reader) throws IOException {
        String login = queryForLogin(reader, session.getWriter());

        if (login != null) {
            session.setLogin(login);
            sendHistory(session.getWriter());
            subscription.subscribe(session);
            processMessages(session.getLogin(), reader);
        }
    }

    public void processMessages(String login, BufferedReader reader) throws IOException {
        do {
            String message = reader.readLine();
            if (message == null) break;
            if (!message.isBlank()) {
                String messageToSend = "[" + login + "] " + message;
                subscription.notifyAllSubscribers(messageToSend);
            }
        } while (true);
    }

    public static String queryForLogin(BufferedReader reader, PrintWriter writer) throws IOException {
        do {
            writer.println("Enter login:");
            String answer = reader.readLine();
            if (answer == null) {
                break;
            }

            if (!answer.isBlank() && answer.length() <= 50) {
                return answer;
            }

            writer.println("Login shouldn't be neither empty nor longer than 50");
        } while (true);

        return null;
    }

    public void sendHistory(PrintWriter writer) {
        for (Object message : messages.getRecentMessage(10)) {
            writer.println(message);
        }
    }
}
