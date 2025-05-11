package com.todo.taskservice.client;

import com.todo.userservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081/user", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("")
    Long getLoggedInUser();

//    @GetMapping("/{id}")
//    User getUserById(@PathVariable Long id);


}
