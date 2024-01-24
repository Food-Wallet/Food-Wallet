package com.foodwallet.server.domain.member.repository;

import com.foodwallet.server.domain.member.ConnectAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectAccountRedisRepository extends CrudRepository<ConnectAccount, String> {
}
