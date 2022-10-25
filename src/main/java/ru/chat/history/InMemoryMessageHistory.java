package ru.chat.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryMessageHistory implements MessageHistory {
    private final List<Object> messages = Collections.synchronizedList(new ArrayList<>());
    @Override
    public void onNewMessage(String message) {
        addMessage(message);
    }

    @Override
    public void addMessage(String message) {
        messages.add(message);

    }

    @Override
    public List<Object> getRecentMessage(int count) {
        synchronized (messages){
            int firstIndex = messages.size() - count;
            if (firstIndex < 0) {
                firstIndex = 0;
            }
            return List.copyOf(messages.subList(firstIndex, messages.size()));
        }
    }
}
