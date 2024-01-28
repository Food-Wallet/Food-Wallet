package com.foodwallet.server.domain.operation.repository.jpa;

import com.foodwallet.server.domain.operation.Operation;
import com.foodwallet.server.domain.operation.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OperationRepositoryImpl implements OperationRepository {

    private final OperationJpaRepository operationJpaRepository;

    @Override
    public Operation save(Operation operation) {
        return operationJpaRepository.save(operation);
    }

    @Override
    public Operation findById(Long operationId) {
        Optional<Operation> findOperation = operationJpaRepository.findById(operationId);
        if (findOperation.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 운영 정보입니다.");
        }
        return findOperation.get();
    }

    @Override
    public Operation findByStoreIdAndStatusEqStart(Long storeId) {
        Optional<Operation> findOperation = operationJpaRepository.findByStoreIdAndStatusEqStart(storeId);
        if (findOperation.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 운영 정보입니다.");
        }
        return findOperation.get();
    }
}
