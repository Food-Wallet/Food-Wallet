package com.foodwallet.server.domain.operation.repository.jpa;

import com.foodwallet.server.domain.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationJpaRepository extends JpaRepository<Operation, Long> {

    @Query("select o from Operation o where o.store.id=:storeId and o.status='START'")
    Optional<Operation> findByStoreIdAndStatusEqStart(@Param("storeId") Long storeId);
}
