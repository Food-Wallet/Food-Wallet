package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import com.foodwallet.server.api.service.store.response.*;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.OperationalInfo;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import com.foodwallet.server.common.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.foodwallet.server.common.message.ExceptionMessageConst.*;
import static com.foodwallet.server.common.message.ExceptionMessageConst.NOT_AUTHORIZED;
import static com.foodwallet.server.common.message.ExceptionMessageConst.NO_ACCOUNT_INFORMATION;


@RequiredArgsConstructor
@Service
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    public StoreCreateResponse createStore(String email, StoreCreateServiceRequest request) {
        Member member = memberRepository.findByEmail(email);

        if (!member.isBusinessMember()) {
            throw new IllegalArgumentException(IS_NOT_BUSINESS_MEMBER);
        }

        if (!member.isExistAccount()) {
            throw new IllegalArgumentException(NO_ACCOUNT_INFORMATION);
        }

        StoreType type = StoreType.of(request.getType());

        Store store = Store.createStore(type, request.getName(), request.getDescription(), member);
        Store savedStore = storeRepository.save(store);

        return StoreCreateResponse.of(savedStore);
    }

    public StoreModifyResponse modifyStoreInfo(String email, Long storeId, StoreModifyServiceRequest request) {
        Store store = getMyStore(email, storeId);

        StoreType type = StoreType.of(request.getType());

        store.modifyInfo(type, request.getName(), request.getDescription());

        return StoreModifyResponse.of(store);
    }

    public StoreModifyImageResponse modifyStoreImage(String email, Long storeId, MultipartFile image) throws IOException {
        Store store = getMyStore(email, storeId);

        UploadFile uploadFile = fileStore.storeFile(image);

        store.modifyImage(uploadFile);

        return StoreModifyImageResponse.of(store);
    }

    public StoreOpenResponse openStore(String email, Long storeId, StoreOpenServiceRequest request) {
        Store store = getMyStore(email, storeId);

        store.open(request.getAddress(), request.getOpenTime(), request.getLatitude(), request.getLongitude());

        return StoreOpenResponse.of(store);
    }

    public StoreCloseResponse closeStore(String email, Long storeId) {
        Store store = getMyStore(email, storeId);

        OperationalInfo operationalInfo = store.getOperationalInfo();

        store.close();

        return StoreCloseResponse.of(store.getName(), operationalInfo);
    }

    public StoreRemoveResponse removeStore(String email, Long storeId) {
        Store store = getMyStore(email, storeId);

        store.remove();

        return StoreRemoveResponse.of(store);
    }

    private Store getMyStore(String email, Long storeId) {
        Member member = memberRepository.findByEmail(email);

        Store store = storeRepository.findById(storeId);

        if (!store.isMine(member)) {
            throw new AuthenticationException(NOT_AUTHORIZED);
        }

        return store;
    }
}
