package ru.chat.history;

import ru.chat.server.MessageSubscription;

import java.util.List;

public interface MessageHistory extends MessageSubscription.EventReceiver {

    void addMessage(String message);

    List<Object> getRecentMessage(int count);

}
