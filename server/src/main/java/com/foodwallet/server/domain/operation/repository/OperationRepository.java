package com.foodwallet.server.domain.operation.repository;

import com.foodwallet.server.domain.operation.Operation;

public interface OperationRepository {

    Operation save(Operation operation);

    Operation findById(Long operationId);

    Operation findByStoreIdAndStatusEqStart(Long storeId);
}
