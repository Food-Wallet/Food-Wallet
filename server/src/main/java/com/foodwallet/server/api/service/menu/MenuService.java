package com.foodwallet.server.api.service.menu;

import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.service.menu.request.MenuCreateServiceRequest;
import com.foodwallet.server.api.service.menu.request.MenuModifyServiceRequest;
import com.foodwallet.server.api.service.menu.response.*;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.menu.Menu;
import com.foodwallet.server.domain.menu.SellingStatus;
import com.foodwallet.server.domain.menu.repository.MenuRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.foodwallet.server.common.message.ExceptionMessage.NOT_AUTHORIZED;

@RequiredArgsConstructor
@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final FileStore fileStore;

    /**
     * 메뉴 정보를 입력 받아 신규 메뉴를 등록한다.
     *
     * @param email   신규 메뉴를 등록할 회원의 이메일
     * @param storeId 신규 메뉴를 등록할 매장의 식별키
     * @param request 신규 메뉴의 정보
     * @return 신규 등록된 메뉴의 정보
     * @throws IOException              이미지 파일 업로드에 실패한 경우
     * @throws AuthenticationException  매장을 등록한 회원과 요청한 회원이 다른 경우
     * @throws IllegalArgumentException 영구 삭제된 매장에 메뉴 등록을 요청하는 경우
     */
    public MenuCreateResponse createMenu(String email, Long storeId, MenuCreateServiceRequest request) throws IOException {
        Store store = getMyStore(email, storeId);

        if (store.isDeleted()) {
            throw new IllegalArgumentException(NOT_AUTHORIZED);
        }

        UploadFile image = fileStore.storeFile(request.getImage());

        Menu menu = Menu.createMenu(request.getName(), request.getDescription(), request.getPrice(), image, store);
        Menu savedMenu = menuRepository.save(menu);

        return MenuCreateResponse.of(savedMenu);
    }

    /**
     * 메뉴 정보를 입력 받아 메뉴 정보를 수정한다.
     *
     * @param email   정보 수정을 요청한 회원의 이메일
     * @param menuId  정보 수정할 메뉴의 식별키
     * @param request 수정할 메뉴 정보
     * @return 수정된 메뉴의 정보
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     */
    public MenuModifyResponse modifyMenuInfo(String email, Long menuId, MenuModifyServiceRequest request) {
        Menu menu = getMyStoreMenu(email, menuId);

        menu.modifyInfo(request.getName(), request.getDescription(), request.getPrice());

        return MenuModifyResponse.of(menu);
    }

    /**
     * 이미지 파일을 입력 받아 메뉴 이미지를 수정한다.
     *
     * @param email  이미지 수정을 요청한 회원의 이메일
     * @param menuId 이미지 수정할 메뉴의 식별키
     * @param file   수정할 이미지 파일
     * @return 이미지가 수정된 메뉴의 정보
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     * @throws IOException             이미지 파일 업로드에 실패한 경우
     */
    public MenuModifyImageResponse modifyMenuImage(String email, Long menuId, MultipartFile file) throws IOException {
        Menu menu = getMyStoreMenu(email, menuId);

        UploadFile image = fileStore.storeFile(file);

        menu.modifyImage(image);

        return MenuModifyImageResponse.of(menu);
    }

    /**
     * 판매 상태를 입력 받아 메뉴 판매 상태를 수정한다.
     *
     * @param email  판매 상태 수정을 요청한 회원의 이메일
     * @param menuId 판매 상태 수정할 메뉴의 식별키
     * @param status 수정할 판매 상태
     * @return 판매 상태가 수정된 메뉴의 정보
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     */
    public MenuModifyStatusResponse modifyMenuStatus(String email, Long menuId, String status) {
        Menu menu = getMyStoreMenu(email, menuId);

        SellingStatus sellingStatus = SellingStatus.of(status);

        menu.modifySellingStatus(sellingStatus);

        return MenuModifyStatusResponse.of(menu);
    }

    /**
     * 메뉴를 영구 삭제한다.
     *
     * @param email  영구 삭제를 요청한 회원의 이메일
     * @param menuId 영구 삭제할 메뉴의 식별키
     * @return 영구 삭제된 메뉴의 정보
     * @throws AuthenticationException 매장을 등록한 회원과 요청한 회원이 다른 경우
     */
    public MenuRemoveResponse removeMenu(String email, Long menuId) {
        Menu menu = getMyStoreMenu(email, menuId);

        menu.remove();

        return MenuRemoveResponse.of(menu);
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

    /**
     * 회원과 메뉴를 조회한다. 조회된 회원과 메뉴를 판매하는 매장을 등록한 회원이 같으면 메뉴를 반환한다.
     *
     * @param email  조회할 회원의 이메일
     * @param menuId 조회할 메뉴의 식별키
     * @return 조회된 메뉴 엔티티
     * @throws AuthenticationException 조회된 회원과 매장을 등록한 회원이 다른 경우
     */
    private Menu getMyStoreMenu(String email, Long menuId) {
        Member member = memberRepository.findByEmail(email);

        Menu menu = menuRepository.findJoinStoreById(menuId);

        if (!menu.getStore().isMine(member)) {
            throw new AuthenticationException(NOT_AUTHORIZED);
        }

        return menu;
    }
}
