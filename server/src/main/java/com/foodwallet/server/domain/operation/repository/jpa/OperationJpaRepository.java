package com.foodwallet.server.domain.operation.repository.jpa;

import com.foodwallet.server.domain.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationJpaRepository extends JpaRepository<Operation, Long> {
}
