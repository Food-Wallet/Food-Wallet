package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.api.service.store.response.StoreModifyResponse;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    public StoreCreateResponse createStore(String email, StoreCreateServiceRequest request) {
        Member member = memberRepository.findByEmail(email);

        if (!member.isBusinessMember()) {
            throw new IllegalArgumentException("사업자 회원만 매장을 등록할 수 있습니다.");
        }

        if (!member.isExistAccount()) {
            throw new IllegalArgumentException("매장을 등록하기 위해서 계좌를 등록해야 합니다.");
        }

        StoreType type = StoreType.of(request.getType());

        Store store = Store.createStore(type, request.getName(), request.getDescription(), member);
        Store savedStore = storeRepository.save(store);

        return StoreCreateResponse.of(savedStore);
    }

    public StoreModifyResponse modifyStoreInfo(String email, Long storeId, StoreModifyServiceRequest request) {
        return null;
    }
}
