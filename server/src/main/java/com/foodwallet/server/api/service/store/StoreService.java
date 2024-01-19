package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.api.service.store.response.StoreModifyResponse;
import com.foodwallet.server.api.service.store.response.StoreOpenResponse;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import com.foodwallet.server.common.exception.AuthenticationException;
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
        Member member = memberRepository.findByEmail(email);

        Store store = storeRepository.findById(storeId);

        if (!store.isMine(member)) {
            throw new AuthenticationException("접근 권한이 없습니다.");
        }

        StoreType type = StoreType.of(request.getType());

        store.modifyInfo(type, request.getName(), request.getDescription());

        return StoreModifyResponse.of(store);
    }

    public StoreOpenResponse openStore(Long storeId, StoreOpenServiceRequest request) {
        return null;
    }
}
