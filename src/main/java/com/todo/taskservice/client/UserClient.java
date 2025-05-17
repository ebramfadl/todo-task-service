package com.todo.taskservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice")
public interface UserClient {
    @GetMapping("/user")
    Long getLoggedInUser();
    @GetMapping("/user/check-exists/{id}")
    Boolean checkUserExistsById(@PathVariable Long id);

}
