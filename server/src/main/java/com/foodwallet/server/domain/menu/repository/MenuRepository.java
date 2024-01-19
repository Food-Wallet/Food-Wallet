package com.foodwallet.server.domain.menu.repository;

import com.foodwallet.server.domain.menu.Menu;

public interface MenuRepository {

    Menu save(Menu menu);

    Menu findById(Long menuId);
}
