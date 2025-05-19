package com.todo.taskservice.rabbitmq;

import com.todo.taskservice.dto.EmailMessage;
import com.todo.taskservice.observer.Subject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskNotificationPublisher implements Subject {

    private List<EmailMessage> observers = new ArrayList<>();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void addObserver(EmailMessage observer) {
        observers.add(observer);
        log.info("TaskNotificationPublisher: Added observer -> " + observer);
    }

    public void removeObserver(EmailMessage observer) {
        observers.remove(observer);
        log.info("TaskNotificationPublisher: Removed observer -> " + observer);
    }

    public void notifyObservers() {
        for (EmailMessage observer : observers) {
            rabbitTemplate.convertAndSend(
                    "task_exchange",
                    "task_routing_key",
                    observer
            );
            log.info("TaskNotificationPublisher: Notified observer -> " + observer);
        }
        observers.clear();
    }
}

