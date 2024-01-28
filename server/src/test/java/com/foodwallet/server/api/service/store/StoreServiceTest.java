package com.foodwallet.server.api.service.store;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyInfoServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import com.foodwallet.server.api.service.store.response.*;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.operation.Coordinate;
import com.foodwallet.server.domain.operation.Operation;
import com.foodwallet.server.domain.operation.OperationStatus;
import com.foodwallet.server.domain.operation.repository.OperationRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.foodwallet.server.domain.member.MemberRole.BUSINESS;
import static com.foodwallet.server.domain.member.MemberRole.USER;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class StoreServiceTest extends IntegrationTestSupport {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OperationRepository operationRepository;

    @DisplayName("신규 매장 등록시 사업자 회원이 아니라면 예외가 발생한다.")
    @Test
    void createStoreWithNotBusinessMember() {
        //given
        Account account = createAccount();
        Member member = createMember("dong82@naver.com", USER, account);
        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1위 닭강정 맛집!")
            .image(null)
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore("dong82@naver.com", request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("사업자 회원만 매장을 등록할 수 있습니다.");
    }

    @DisplayName("신규 매장 등록시 회원의 계좌 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void createStoreWithoutAccountInfo() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1위 닭강정 맛집!")
            .image(null)
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore("dong82@naver.com", request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("계좌 정보가 존재하지 않습니다.");
    }

    @DisplayName("사업자 회원의 이메일과 매장 정보를 입력 받아 신규 매장을 등록한다.")
    @Test
    void createStore() throws IOException {
        //given
        Account account = createAccount();
        Member member = createMember("dong82@naver.com", BUSINESS, account);
        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1위 닭강정 맛집!")
            .image(null)
            .build();

        //when
        StoreCreateResponse response = storeService.createStore("dong82@naver.com", request);

        //then
        assertThat(response)
            .extracting("storeType", "storeName", "storeDescription", "storeImage")
            .contains("치킨", "나리닭강정", "국내 1위 닭강정 맛집!", null);
    }

    @DisplayName("매장 정보 수정시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void modifyStoreInfoWithNotAuth() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        StoreModifyInfoServiceRequest request = StoreModifyInfoServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내에서 제일 잘나가는 닭강정!")
            .build();

        Member otherMember = createMember("do72@naver.com", BUSINESS, null);

        //when //then
        assertThatThrownBy(() -> storeService.modifyStoreInfo("do72@naver.com", store.getId(), request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("매장 정보 수정시 매장이 운영중이라면 예외가 발생한다.")
    @Test
    void modifyStoreInfoWithOpen() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.OPEN);
        StoreModifyInfoServiceRequest request = StoreModifyInfoServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내에서 제일 잘나가는 닭강정!")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.modifyStoreInfo("dong82@naver.com", store.getId(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("영업중에는 매장 정보를 수정할 수 없습니다.");
    }

    @DisplayName("회원의 이메일, 매장 식별키, 매장 정보를 입력 받아 매장 정보를 수정한다. ")
    @Test
    void modifyStoreInfo() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        StoreModifyInfoServiceRequest request = StoreModifyInfoServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내에서 제일 잘나가는 닭강정!")
            .build();

        //when
        StoreModifyInfoResponse response = storeService.modifyStoreInfo("dong82@naver.com", store.getId(), request);

        //then
        assertThat(response)
            .extracting("storeId", "storeType", "storeName", "storeDescription")
            .contains(store.getId(), "치킨", "나리닭강정", "국내에서 제일 잘나가는 닭강정!");

        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore)
            .extracting("type", "name", "description")
            .contains(StoreType.CHICKEN, "나리닭강정", "국내에서 제일 잘나가는 닭강정!");
    }

    @DisplayName("매장 이미지 수정시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void modifyStoreImageWithNotAuth() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        MockMultipartFile image = new MockMultipartFile(
            "storeImage",
            "store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        Member otherMember = createMember("do72@naver.com", BUSINESS, null);

        //when //then
        assertThatThrownBy(() -> storeService.modifyStoreImage("do72@naver.com", store.getId(), image))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("매장 이미지 수정시 매장이 운영중이라면 예외가 발생한다.")
    @Test
    void modifyStoreImageWithOpen() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.OPEN);
        MockMultipartFile image = new MockMultipartFile(
            "storeImage",
            "store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        //when //then
        assertThatThrownBy(() -> storeService.modifyStoreImage("dong82@naver.com", store.getId(), image))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("영업중에는 매장 정보를 수정할 수 없습니다.");
    }

    @DisplayName("회원의 이메일, 매장 식별키, 매장 이미지를 입력 받아 매장 이미지를 수정한다. ")
    @Test
    void modifyStoreImage() throws IOException {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        MockMultipartFile image = new MockMultipartFile(
            "storeImage",
            "store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        UploadFile uploadFile = UploadFile.builder()
            .uploadFileName("uploadFileName")
            .storeFileName("storeFileName")
            .build();

        given(fileStore.storeFile(image))
            .willReturn(uploadFile);

        //when
        StoreModifyImageResponse response = storeService.modifyStoreImage("dong82@naver.com", store.getId(), image);

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore)
            .extracting("image.uploadFileName", "image.storeFileName")
            .contains("uploadFileName", "storeFileName");
    }

    @DisplayName("매장 운영 시작시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void openStoreWithoutAuth() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        StoreOpenServiceRequest request = StoreOpenServiceRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(20, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        Member otherMember = createMember("do72@naver.com", BUSINESS, null);

        //when //then
        assertThatThrownBy(() -> storeService.openStore("do72@naver.com", store.getId(), request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("매장 운영 시작시 매장이 이미 운영중이라면 예외가 발생한다.")
    @Test
    void openStoreWithOpen() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.OPEN);
        StoreOpenServiceRequest request = StoreOpenServiceRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(20, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.openStore("dong82@naver.com", store.getId(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 매장을 운영하고 있습니다.");
    }

    @DisplayName("매장 운영 시작시 입력 받은 운영 시작 시간과 종료 시간이 같으면 예외가 발생한다.")
    @Test
    void openStoreWithStartTimeEqualsFinishTime() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        StoreOpenServiceRequest request = StoreOpenServiceRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(11, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.openStore("dong82@naver.com", store.getId(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("운영 시작 시간과 종료 시간을 다르게 설정해주세요.");
    }

    @DisplayName("매장 운영 시작시 입력 받은 운영 시작 시간보다 종료 시간이 과거라면 익일 종료로 변경된다.")
    @Test
    void openStoreWithFinishTimeLessThanStartTime() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        StoreOpenServiceRequest request = StoreOpenServiceRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(10, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        //when
        StoreOpenResponse response = storeService.openStore("dong82@naver.com", store.getId(), request);

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore.getStatus()).isEqualByComparingTo(StoreStatus.OPEN);

        Operation operation = operationRepository.findById(response.getOperationInfo().getOperationId());
        assertThat(operation)
            .extracting("address", "time", "coordinate.latitude", "coordinate.longitude", "totalSales")
            .contains("경기도 성남시 분당구 판교역로 166", "오전 11:00 ~ 익일 10:00", 37.3954951, 127.1103645, 0);
    }

    @DisplayName("회원의 이메일, 매장 식별키, 운영 정보를 입력 받아 매장 운영을 시작한다.")
    @Test
    void openStore() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        StoreOpenServiceRequest request = StoreOpenServiceRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(20, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        //when
        StoreOpenResponse response = storeService.openStore("dong82@naver.com", store.getId(), request);

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore.getStatus()).isEqualByComparingTo(StoreStatus.OPEN);

        Operation operation = operationRepository.findById(response.getOperationInfo().getOperationId());
        assertThat(operation)
            .extracting("address", "time", "coordinate.latitude", "coordinate.longitude", "totalSales")
            .contains("경기도 성남시 분당구 판교역로 166", "오전 11:00 ~ 오후 8:00", 37.3954951, 127.1103645, 0);
    }

    @DisplayName("매장 운영 종료시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void closeStoreWithoutAuth() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 28, 20, 0);
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.OPEN);
        Operation operation = createOperation(OperationStatus.START, LocalDateTime.of(2024, 1, 28, 10, 50), store);

        Member otherMember = createMember("do72@naver.com", BUSINESS, null);

        //when //then
        assertThatThrownBy(() -> storeService.closeStore("do72@naver.com", store.getId(), currentDateTime))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("매장 운영 종료시 매장이 운영 상태가 아니라면 예외가 발생한다.")
    @Test
    void closeStoreWithNotOpen() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 28, 20, 0);
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        Operation operation = createOperation(OperationStatus.FINISH, currentDateTime, store);

        //when //then
        assertThatThrownBy(() -> storeService.closeStore("dong82@naver.com", store.getId(), currentDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("운영 중인 매장이 아닙니다.");
    }

    @DisplayName("회원의 이메일, 매장 식별키, 현재 시간을 입력 받아 매장 운영을 종료한다.")
    @Test
    void closeStore() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 28, 20, 0);
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.OPEN);
        Operation operation = createOperation(OperationStatus.START, LocalDateTime.of(2024, 1, 28, 10, 50), store);

        //when
        StoreCloseResponse response = storeService.closeStore("dong82@naver.com", store.getId(), currentDateTime);

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore.getStatus()).isEqualByComparingTo(StoreStatus.CLOSE);

        Operation findOperation = operationRepository.findById(operation.getId());
        assertThat(findOperation)
            .extracting("status", "finishedDateTime", "totalSales")
            .contains(OperationStatus.FINISH, currentDateTime, 0);
    }

    public Account createAccount() {
        return Account.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();
    }

    public Member createMember(String email, MemberRole role, Account account) {
        Member member = Member.builder()
            .email(email)
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(role)
            .account(account)
            .build();
        return memberRepository.save(member);
    }

    private Store createStore(Member member, StoreStatus status) {
        Store store = Store.builder()
            .status(status)
            .type(StoreType.CHICKEN)
            .name("나리닭강정")
            .description("국내 1등 닭강정 맛집!")
            .member(member)
            .build();
        return storeRepository.save(store);
    }

    private Operation createOperation(OperationStatus status, LocalDateTime finishedDateTime, Store store) {
        Operation operation = Operation.builder()
            .status(status)
            .address("경기도 성남시 분당구 판교역로 166")
            .time("오전 11:00 ~ 오후 20:00")
            .coordinate(Coordinate.builder()
                .latitude(37.3954951)
                .longitude(127.1103645)
                .build())
            .startedDateTime(LocalDateTime.of(2024, 1, 28, 10, 50))
            .finishedDateTime(finishedDateTime)
            .totalSales(0)
            .store(store)
            .build();
        return operationRepository.save(operation);
    }
}