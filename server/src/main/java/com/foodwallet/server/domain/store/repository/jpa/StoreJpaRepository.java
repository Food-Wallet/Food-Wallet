package com.foodwallet.server.domain.store.repository.jpa;

import com.foodwallet.server.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreJpaRepository extends JpaRepository<Store, Long> {
}
