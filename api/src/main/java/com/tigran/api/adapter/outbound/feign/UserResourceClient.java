package com.tigran.api.adapter.outbound.feign;

import com.tigran.api.domain.model.shared.user.UserAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 1:01 PM
 */
@FeignClient(name = "user-service", url = "${http://localhost:8081}")
public interface UserResourceClient {

    @GetMapping("/users/with-enabled-notifications")
    List<UserAccount> getAllUsersWithEnabledNotification();
}
