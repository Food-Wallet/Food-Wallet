package com.foodwallet.server.api.service.menu;

import com.foodwallet.server.api.service.menu.request.MenuCreateServiceRequest;
import com.foodwallet.server.api.service.menu.request.MenuModifyServiceRequest;
import com.foodwallet.server.api.service.menu.response.MenuCreateResponse;
import com.foodwallet.server.api.service.menu.response.MenuModifyResponse;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.menu.Menu;
import com.foodwallet.server.domain.menu.repository.MenuRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public MenuCreateResponse createMenu(String email, Long storeId, MenuCreateServiceRequest request) {
        Member member = memberRepository.findByEmail(email);

        Store store = storeRepository.findById(storeId);

        if (!store.isMine(member)) {
            throw new AuthenticationException("접근 권한이 없습니다.");
        }

        if (store.isDeleted()) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        Menu menu = Menu.createMenu(request.getName(), request.getDescription(), request.getPrice(), request.getImage(), store);
        Menu savedMenu = menuRepository.save(menu);
        return MenuCreateResponse.of(savedMenu);
    }

    public MenuModifyResponse modifyMenuInfo(String email, Long menuId, MenuModifyServiceRequest request) {
        Member member = memberRepository.findByEmail(email);

        Menu menu = menuRepository.findJoinStoreById(menuId);

        if (!menu.getStore().isMine(member)) {
            throw new AuthenticationException("접근 권한이 없습니다.");
        }

        menu.modifyInfo(request.getName(), request.getDescription(), request.getPrice());

        return MenuModifyResponse.of(menu);
    }
}
