package com.foodwallet.server.api.service.menu;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.menu.request.MenuCreateServiceRequest;
import com.foodwallet.server.api.service.menu.request.MenuModifyServiceRequest;
import com.foodwallet.server.api.service.menu.response.*;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.menu.Menu;
import com.foodwallet.server.domain.menu.SellingStatus;
import com.foodwallet.server.domain.menu.repository.MenuRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MenuServiceTest extends IntegrationTestSupport {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("메뉴 등록시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void createMenuWithoutAuth() {
        //given
        Member member1 = createMember("dong82@naver.com");
        Store store = createStore(member1);

        Member member2 = createMember("do72@naver.com");

        MenuCreateServiceRequest request = MenuCreateServiceRequest.builder()
            .name("간장닭강정")
            .price(8000)
            .build();

        //when //then
        assertThatThrownBy(() -> menuService.createMenu("do72@naver.com", store.getId(), request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("삭제된 매장에 메뉴 등록을 시도하는 경우 예외가 발생한다.")
    @Test
    void createMenuWithRemovedStore() {
        //given
        Member member = createMember("dong82@naver.com");
        Store store = createStore(member);
        store.remove();

        MenuCreateServiceRequest request = MenuCreateServiceRequest.builder()
            .name("간장닭강정")
            .price(8000)
            .build();

        //when //then
        assertThatThrownBy(() -> menuService.createMenu("dong82@naver.com", store.getId(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("사업자 회원 이메일, 매장 식별키, 메뉴 정보를 입력 받아 신규 메뉴를 등록한다.")
    @Test
    void createMenu() throws IOException {
        //given
        Member member = createMember("dong82@naver.com");
        Store store = createStore(member);

        MenuCreateServiceRequest request = MenuCreateServiceRequest.builder()
            .name("간장닭강정")
            .price(8000)
            .build();

        //when
        MenuCreateResponse response = menuService.createMenu("dong82@naver.com", store.getId(), request);

        //then
        assertThat(response)
            .extracting("name", "price")
            .contains("간장닭강정", 8000);
    }

    @DisplayName("메뉴 정보 수정시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void modifyMenuInfoWithoutAuth() {
        //given
        Member member1 = createMember("dong82@naver.com");
        Store store = createStore(member1);
        Menu menu = createMenu(store);

        Member member2 = createMember("do72@naver.com");

        MenuModifyServiceRequest request = MenuModifyServiceRequest.builder()
            .name("간장닭강정")
            .description("우리 매장 시그니처 메뉴입니다!")
            .price(9000)
            .build();

        //when //then
        assertThatThrownBy(() -> menuService.modifyMenuInfo("do72@naver.com", menu.getId(), request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("사업자 회원 이메일, 매장 식별키, 메뉴 정보를 입력 받아 메뉴 정보를 수정한다.")
    @Test
    void modifyMenuInfo() {
        //given
        Member member = createMember("dong82@naver.com");
        Store store = createStore(member);
        Menu menu = createMenu(store);

        MenuModifyServiceRequest request = MenuModifyServiceRequest.builder()
            .name("간장닭강정")
            .description("우리 매장 시그니처 메뉴입니다!")
            .price(9000)
            .build();

        //when
        MenuModifyResponse response = menuService.modifyMenuInfo("dong82@naver.com", menu.getId(), request);

        //then
        Menu findMenu = menuRepository.findById(menu.getId());
        assertThat(findMenu)
            .extracting("name", "description", "price")
            .contains("간장닭강정", "우리 매장 시그니처 메뉴입니다!", 9000);
    }

    @DisplayName("메뉴 이미지 수정시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void modifyMenuImageWithoutAuth() {
        //given
        Member member1 = createMember("dong82@naver.com");
        Store store = createStore(member1);
        Menu menu = createMenu(store);

        Member member2 = createMember("do72@naver.com");

        MockMultipartFile image = new MockMultipartFile(
            "image",
            "store-menu-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        //when //then
        assertThatThrownBy(() -> menuService.modifyMenuImage("do72@naver.com", menu.getId(), image))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("사업자 회원 이메일, 매장 식별키, 파일 정보를 입력 받아 메뉴 이미지를 수정한다.")
    @Test
    void modifyMenuImage() throws IOException {
        //given
        Member member = createMember("dong82@naver.com");
        Store store = createStore(member);
        Menu menu = createMenu(store);

        MockMultipartFile image = new MockMultipartFile(
            "image",
            "store-menu-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        UploadFile uploadFile = UploadFile.builder()
            .uploadFileName("upload-file-name.jpg")
            .storeFileName("s3-store-file-url.jpg")
            .build();

        BDDMockito.given(fileStore.storeFile(any(MultipartFile.class)))
            .willReturn(uploadFile);

        //when
        MenuModifyImageResponse response = menuService.modifyMenuImage("dong82@naver.com", menu.getId(), image);

        //then
        Menu findMenu = menuRepository.findById(menu.getId());
        assertThat(findMenu.getImage())
            .extracting("uploadFileName", "storeFileName")
            .contains("upload-file-name.jpg", "s3-store-file-url.jpg");
    }

    @DisplayName("메뉴 판매 상태 수정시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void modifyMenuStatusWithoutAuth() {
        //given
        Member member1 = createMember("dong82@naver.com");
        Store store = createStore(member1);
        Menu menu = createMenu(store);

        Member member2 = createMember("do72@naver.com");

        //when //then
        assertThatThrownBy(() -> menuService.modifyMenuStatus("do72@naver.com", menu.getId(), "STOP_SELLING"))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("사업자 회원 이메일, 매장 식별키, 상태 정보를 입력 받아 메뉴 판매 상태를 수정한다.")
    @Test
    void modifyMenuStatus() {
        //given
        Member member = createMember("dong82@naver.com");
        Store store = createStore(member);
        Menu menu = createMenu(store);

        //when
        MenuModifyStatusResponse response = menuService.modifyMenuStatus("dong82@naver.com", menu.getId(), "STOP_SELLING");

        //then
        Menu findMenu = menuRepository.findById(menu.getId());
        assertThat(findMenu.getStatus()).isEqualByComparingTo(SellingStatus.STOP_SELLING);
    }

    @DisplayName("메뉴 삭제시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void removeMenuWithoutAuth() {
        //given
        Member member1 = createMember("dong82@naver.com");
        Store store = createStore(member1);
        Menu menu = createMenu(store);

        Member member2 = createMember("do72@naver.com");

        //when //then
        assertThatThrownBy(() -> menuService.removeMenu("do72@naver.com", menu.getId()))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("사업자 회원 이메일과 매장 식별키를 입력 받아 메뉴를 삭제한다.")
    @Test
    void removeMenu() {
        //given
        Member member = createMember("dong82@naver.com");
        Store store = createStore(member);
        Menu menu = createMenu(store);

        //when
        MenuRemoveResponse response = menuService.removeMenu("dong82@naver.com", menu.getId());

        //then
        Menu findMenu = menuRepository.findById(menu.getId());
        assertThat(findMenu.isDeleted()).isTrue();
    }

    private Member createMember(String email) {
        Member member = Member.builder()
            .email(email)
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.BUSINESS)
            .build();
        return memberRepository.save(member);
    }

    private Store createStore(Member member) {
        Store store = Store.builder()
            .status(StoreStatus.CLOSE)
            .type(CHICKEN)
            .name("나리닭강정")
            .member(member)
            .build();
        return storeRepository.save(store);
    }

    private Menu createMenu(Store store) {
        Menu menu = Menu.builder()
            .name("간장닭강정")
            .price(8000)
            .status(SellingStatus.SELLING)
            .store(store)
            .build();
        return menuRepository.save(menu);
    }
}