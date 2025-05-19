package com.todo.taskservice.observer;

import com.todo.taskservice.dto.EmailMessage;

public interface Subject {

    void addObserver(EmailMessage observer);

    void removeObserver(EmailMessage observer);

    void notifyObservers();
}
