package com.todo.taskservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", url = "http://localhost:8081/user")
public interface UserClient {
    @GetMapping("")
    Long getLoggedInUser();

}
