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
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import com.foodwallet.server.common.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.foodwallet.server.common.message.ExceptionMessage.*;
import static com.foodwallet.server.common.message.ExceptionMessage.NOT_AUTHORIZED;
import static com.foodwallet.server.common.message.ExceptionMessage.NO_ACCOUNT_INFORMATION;
import static com.foodwallet.server.domain.store.StoreStatus.OPEN;


@RequiredArgsConstructor
@Service
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 매장 정보를 입력 받아 신규 매장을 등록한다.
     *
     * @param email   신규 매장을 등록할 회원의 이메일
     * @param request 신규 매장의 정보
     * @return 신규 등록된 매장의 정보
     * @throws AuthenticationException  등록을 시도한 회원이 사업자 권한이 없는 경우
     * @throws IllegalArgumentException 회원의 계좌 정보가 존재하지 않는 경우
     */
    public StoreCreateResponse createStore(String email, StoreCreateServiceRequest request) {
        Member member = memberRepository.findByEmail(email);

        if (!member.isBusinessMember()) {
            throw new AuthenticationException(IS_NOT_BUSINESS_MEMBER);
        }

        if (!member.isExistAccount()) {
            throw new IllegalArgumentException(NO_ACCOUNT_INFORMATION);
        }

        String validatedName = StoreValidator.nameValidation(request.getName());
        String validatedDescription = StoreValidator.descriptionValidation(request.getDescription());

        StoreType type = StoreType.of(request.getType());

        Store store = Store.createStore(type, validatedName, validatedDescription, member);
        Store savedStore = storeRepository.save(store);

        return StoreCreateResponse.of(savedStore);
    }

    /**
     * 매장 정보를 입력 받아 매장 정보를 수정한다.
     *
     * @param email   정보 수정을 요청한 회원의 이메일
     * @param storeId 정보 수정할 매장의 식별키
     * @param request 수정할 매장 정보
     * @return 수정된 매장의 정보
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     */
    public StoreModifyResponse modifyStoreInfo(String email, Long storeId, StoreModifyServiceRequest request) {
        Store store = getMyStore(email, storeId);

        StoreType type = StoreType.of(request.getType());

        store.modifyInfo(type, request.getName(), request.getDescription());

        return StoreModifyResponse.of(store);
    }

    /**
     * 이미지 파일을 입력 받아 매장 이미지를 수정한다.
     *
     * @param email   이미지 수정을 요청한 회원의 이메일
     * @param storeId 이미지 수정할 매장의 식별키
     * @param image   수정할 이미지 파일
     * @return 수정된 매장의 정보
     * @throws IOException             이미지 업로드에 실패한 경우
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     */
    public StoreModifyImageResponse modifyStoreImage(String email, Long storeId, MultipartFile image) throws IOException {
        Store store = getMyStore(email, storeId);

        UploadFile uploadFile = fileStore.storeFile(image);

        store.modifyImage(uploadFile);

        return StoreModifyImageResponse.of(store);
    }

    /**
     * 현재 위치 정보를 입력 받아 매장 운영을 시작한다.
     *
     * @param email   운영 시작을 요청한 회원의 이메일
     * @param storeId 운영 시작할 매장의 식별키
     * @param request 운영 시작할 매장의 현재 위치 정보
     * @return 운영 시작한 매장의 정보
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     */
    public StoreOpenResponse openStore(String email, Long storeId, StoreOpenServiceRequest request) {
        Store store = getMyStore(email, storeId);

        store.open(request.getAddress(), request.getOpenTime(), request.getLatitude(), request.getLongitude());

        return StoreOpenResponse.of(store);
    }

    /**
     * 매장 운영을 종료한다.
     *
     * @param email   운영 종료를 요청한 회원의 이메일
     * @param storeId 운영 종료할 매징의 식별키
     * @return 운영 종료한 매장의 정보
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     */
    public StoreCloseResponse closeStore(String email, Long storeId) {
        Store store = getMyStore(email, storeId);

        OperationalInfo operationalInfo = store.getOperationalInfo();

        store.close();

        return StoreCloseResponse.of(store.getName(), operationalInfo);
    }

    /**
     * 매장을 영구 삭제한다.
     *
     * @param email   영구 삭제를 요청한 회원의 이메일
     * @param storeId 영구 삭제할 매장의 식별키
     * @return 영구 삭제된 매장의 정보
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     */
    public StoreRemoveResponse removeStore(String email, Long storeId, String pwd) {
        Store store = getMyStore(email, storeId);

        if (store.getStatus().equals(OPEN)) {
            throw new IllegalArgumentException("운영중인 매장은 삭제할 수 없습니다.");
        }

        Member member = memberRepository.findByEmail(email);
        if (!passwordEncoder.matches(pwd, member.getPwd())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        store.remove();

        return StoreRemoveResponse.of(store);
    }

    /**
     * 회원과 매장을 조회한다. 조회된 회원과 매장을 등록한 회원이 같으면 매장을 반환한다.
     *
     * @param email   조회할 회원의 이메일
     * @param storeId 조회할 매장의 식별키
     * @return 조회된 매장 엔티티
     * @throws AuthenticationException 조회된 회원과 매장을 등록한 회원이 다른 경우
     */
    private Store getMyStore(String email, Long storeId) {
        Member member = memberRepository.findByEmail(email);

        Store store = storeRepository.findById(storeId);

        if (!store.isMine(member)) {
            throw new AuthenticationException(NOT_AUTHORIZED);
        }

        return store;
    }
}
