package com.tigran.oauth.repository.account;

import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.repository.account.custom.AccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 3:06 PM
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, AccountRepositoryCustom {

    boolean existsByUserName(final String username);
    
    Optional<Account> findByUserName(final String username);
}
