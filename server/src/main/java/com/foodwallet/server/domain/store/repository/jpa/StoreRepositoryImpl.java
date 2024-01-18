package com.foodwallet.server.domain.store.repository.jpa;

import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StoreRepositoryImpl implements StoreRepository {

    private final StoreJpaRepository storeJpaRepository;

    @Override
    public Store save(Store store) {
        return storeJpaRepository.save(store);
    }

    @Override
    public Store findById(Long storeId) {
        Optional<Store> findStore = storeJpaRepository.findById(storeId);
        if (findStore.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 매장입니다.");
        }
        return findStore.get();
    }
}
