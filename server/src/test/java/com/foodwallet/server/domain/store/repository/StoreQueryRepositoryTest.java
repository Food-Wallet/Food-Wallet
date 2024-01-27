package com.foodwallet.server.domain.store.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.dto.StoreDetailDto;
import com.foodwallet.server.domain.store.repository.dto.StoreSearchCond;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import static com.foodwallet.server.domain.store.StoreStatus.*;
import static com.foodwallet.server.domain.store.StoreStatus.CLOSE;
import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static com.foodwallet.server.domain.store.StoreType.*;
import static org.assertj.core.api.Assertions.*;

class StoreQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StoreQueryRepository storeQueryRepository;

    @Autowired
    private StoreRepository storeRepository;

}