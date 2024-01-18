package com.foodwallet.server.domain.store.repository;

import com.foodwallet.server.domain.store.Store;

public interface StoreRepository {

    Store save(Store store);
}
