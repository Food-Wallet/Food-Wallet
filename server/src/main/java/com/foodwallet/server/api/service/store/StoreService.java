package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.foodwallet.server.api.service.store.StoreValidator.*;
import static com.foodwallet.server.common.message.ExceptionMessage.IS_NOT_BUSINESS_MEMBER;
import static com.foodwallet.server.common.message.ExceptionMessage.NO_ACCOUNT_INFORMATION;

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
            throw new AuthenticationException(IS_NOT_BUSINESS_MEMBER);
        }

        if (!member.isExistAccount()) {
            throw new IllegalArgumentException(NO_ACCOUNT_INFORMATION);
        }

        StoreType type = StoreType.of(request.getType());
        String validatedName = nameValidation(request.getName());
        String validatedDescription = descriptionValidation(request.getDescription());

        UploadFile uploadFile = toUploadFile(request.getImage());

        Store store = Store.createStore(type, validatedName, validatedDescription, uploadFile, member);
        Store savedStore = storeRepository.save(store);

        return StoreCreateResponse.of(savedStore);
    }

    private UploadFile toUploadFile(MultipartFile file) {
        try {
            return fileStore.storeFile(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 업로드를 실패했습니다.", e);
        }
    }
}
