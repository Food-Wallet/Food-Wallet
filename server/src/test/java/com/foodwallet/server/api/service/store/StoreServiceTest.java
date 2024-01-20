package com.foodwallet.server.api.service.store;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import com.foodwallet.server.api.service.store.response.*;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.OperationalInfo;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import com.foodwallet.server.common.exception.AuthenticationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.foodwallet.server.domain.member.MemberRole.*;
import static com.foodwallet.server.domain.store.StoreStatus.CLOSE;
import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class StoreServiceTest extends IntegrationTestSupport {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("신규 매장을 생성시 사업자 회원이 아니라면 예외가 발생한다.")
    @Test
    void createStoreNotBusinessMember() {
        //given
        Account account = createAccount();
        Member member = createMember(USER, account, "dong82@naver.com");

        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore(member.getEmail(), request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("사업자 회원만 매장을 등록할 수 있습니다.");
    }

    @DisplayName("신규 매장을 생성시 사업자 회원의 계좌 정보가 없으면 예외가 발생한다.")
    @Test
    void createStoreWithoutAccount() {
        //given
        Member member = createMember(BUSINESS, null, "dong82@naver.com");

        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore(member.getEmail(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("매장을 등록하기 위해서 계좌를 등록해야 합니다.");
    }

    @DisplayName("사업자 회원 이메일과 매장 정보를 입력 받아 신규 매장을 생성한다.")
    @Test
    void createStore() {
        //given
        Account account = createAccount();
        Member member = createMember(BUSINESS, account, "dong82@naver.com");

        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .build();

        //when
        StoreCreateResponse response = storeService.createStore(member.getEmail(), request);

        //then
        assertThat(response)
            .extracting("type", "name")
            .contains("치킨", "나리닭강정");
    }

    @DisplayName("매장 정보 수정시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void modifyStoreInfoWithoutAuth() {
        //given
        Account account = createAccount();
        Member member1 = createMember(BUSINESS, account, "dong82@naver.com");
        Store store = createStore(member1, StoreStatus.CLOSE, null);

        Member member2 = createMember(BUSINESS, account, "do72@naver.com");

        StoreModifyServiceRequest request = StoreModifyServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1등 닭강정")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.modifyStoreInfo(member2.getEmail(), store.getId(), request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("사업자 회원 이메일, 매장 식별키, 매장 정보를 입력 받아 매장 정보를 수정한다.")
    @Test
    void modifyStoreInfo() {
        //given
        Account account = createAccount();
        Member member = createMember(BUSINESS, account, "dong82@naver.com");
        Store store = createStore(member, StoreStatus.CLOSE, null);

        StoreModifyServiceRequest request = StoreModifyServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1등 닭강정")
            .build();

        //when
        StoreModifyResponse response = storeService.modifyStoreInfo(member.getEmail(), store.getId(), request);

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore)
            .extracting("type", "name", "description")
            .contains(CHICKEN, "나리닭강정", "국내 1등 닭강정");
    }

    @DisplayName("매장 식별키와 운영 장소를 입력 받아 매장을 오픈할 수 있다.")
    @Test
    void openStore() {
        //given
        Account account = createAccount();
        Member member = createMember(BUSINESS, account, "dong82@naver.com");
        Store store = createStore(member, StoreStatus.CLOSE, null);

        StoreOpenServiceRequest request = StoreOpenServiceRequest.builder()
            .address("서울 중구 세종대로 110")
            .openTime("오전 10:00 ~ 오후 8:00")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .build();

        //when
        StoreOpenResponse response = storeService.openStore("dong82@naver.com", store.getId(), request);

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore)
            .extracting("status", "operationalInfo.address", "operationalInfo.openTime", "operationalInfo.latitude", "operationalInfo.longitude")
            .contains(OPEN, "서울 중구 세종대로 110", "오전 10:00 ~ 오후 8:00", 37.566352778, 126.977952778);
    }

    @DisplayName("매장 운영을 종료시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void closeStoreWithoutAuth() {
        //given
        Account account = createAccount();
        Member member1 = createMember(BUSINESS, account, "dong82@naver.com");
        OperationalInfo operationalInfo = createOperationalInfo();
        Store store = createStore(member1, OPEN, operationalInfo);

        Member member2 = createMember(BUSINESS, account, "do72@naver.com");

        //when //then
        assertThatThrownBy(() -> storeService.closeStore(member2.getEmail(), store.getId()))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("사업자 회원 이메일을 입력 받아 매장 운영을 종료한다.")
    @Test
    void closeStore() {
        //given
        Account account = createAccount();
        Member member = createMember(BUSINESS, account, "dong82@naver.com");
        OperationalInfo operationalInfo = createOperationalInfo();
        Store store = createStore(member, OPEN, operationalInfo);

        //when
        StoreCloseResponse response = storeService.closeStore("dong82@naver.com", store.getId());

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore)
            .extracting("status", "operationalInfo")
            .contains(CLOSE);
    }

    @DisplayName("매장 식별키와 업로드 파일 객체를 입력 받아 매장 이미지를 수정한다.")
    @Test
    void modifyStoreImage() throws IOException {
        //given
        Account account = createAccount();
        Member member = createMember(BUSINESS, account, "dong82@naver.com");
        Store store = createStore(member, CLOSE, null);
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "my-store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        UploadFile uploadFile = UploadFile.builder()
            .uploadFileName("upload-file-name.png")
            .storeFileName("s3-store-file-url.png")
            .build();

        BDDMockito.given(fileStore.storeFile(any(MultipartFile.class)))
            .willReturn(uploadFile);

        //when
        StoreModifyImageResponse response = storeService.modifyStoreImage("dong82@naver.com", store.getId(), image);

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore.getImage())
            .extracting("uploadFileName", "storeFileName")
            .contains("upload-file-name.png", "s3-store-file-url.png");
    }

    @DisplayName("매장 삭제시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void removeStoreWithoutAuth() {
        //given
        Account account = createAccount();
        Member member1 = createMember(BUSINESS, account, "dong82@naver.com");
        Store store = createStore(member1, CLOSE, null);

        Member member2 = createMember(BUSINESS, account, "do72@naver.com");

        //when //then
        assertThatThrownBy(() -> storeService.removeStore("do72@naver.com", store.getId()))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("회원 이메일과 매장 식별키를 입력 받아 매장을 삭제한다.")
    @Test
    void removeStore() {
        //given
        Account account = createAccount();
        Member member = createMember(BUSINESS, account, "dong82@naver.com");
        Store store = createStore(member, CLOSE, null);

        //when
        StoreRemoveResponse response = storeService.removeStore("dong82@naver.com", store.getId());

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore.isDeleted()).isTrue();
    }

    private Account createAccount() {
        return Account.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();
    }

    private Member createMember(MemberRole role, Account account, String email) {
        Member member = Member.builder()
            .email(email)
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .age(10)
            .gender("F")
            .role(role)
            .account(account)
            .token(null)
            .build();
        return memberRepository.save(member);
    }

    private OperationalInfo createOperationalInfo() {
        return OperationalInfo.builder()
            .address("서울 중구 세종대로 110")
            .openTime("오전 10:00 ~ 오후 8:00")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .build();
    }

    private Store createStore(Member member, StoreStatus status, OperationalInfo operationalInfo) {
        Store store = Store.builder()
            .status(status)
            .type(CHICKEN)
            .name("나리닭강정")
            .member(member)
            .operationalInfo(operationalInfo)
            .build();
        return storeRepository.save(store);
    }
}