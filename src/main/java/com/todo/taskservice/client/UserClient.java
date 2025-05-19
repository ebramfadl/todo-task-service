package com.todo.taskservice.client;

import com.todo.taskservice.dto.UserNotificationSetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "userservice",configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user")
    Long getLoggedInUser();
    @GetMapping("/user/check-exists/{id}")
    Boolean checkUserExistsById(@PathVariable Long id);

    @GetMapping("/user/user-setting/{id}")
    UserNotificationSetting getUserSetting(@PathVariable Long id);
    @PostMapping("/user/settings")
    List<UserNotificationSetting> getSettings(@RequestBody List<Long> userIds);

}
