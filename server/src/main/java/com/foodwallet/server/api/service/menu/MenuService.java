package com.foodwallet.server.api.service.menu;

import com.foodwallet.server.api.service.menu.request.MenuCreateServiceRequest;
import com.foodwallet.server.api.service.menu.response.MenuCreateResponse;
import com.foodwallet.server.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuCreateResponse createMenu(String email, Long storeId, MenuCreateServiceRequest request) {
        return null;
    }
}
