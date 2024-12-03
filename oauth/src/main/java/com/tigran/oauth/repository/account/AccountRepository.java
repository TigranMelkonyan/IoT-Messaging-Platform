package com.tigran.oauth.repository.account;

import com.tigran.oauth.model.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 3:06 PM
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
