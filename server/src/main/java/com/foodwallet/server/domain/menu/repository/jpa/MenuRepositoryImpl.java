package com.foodwallet.server.domain.menu.repository.jpa;

import com.foodwallet.server.domain.menu.Menu;
import com.foodwallet.server.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MenuRepositoryImpl implements MenuRepository {

    private final MenuJpaRepository menuJpaRepository;

    @Override
    public Menu save(Menu menu) {
        return menuJpaRepository.save(menu);
    }

    @Override
    public Menu findById(Long menuId) {
        Optional<Menu> findMenu = menuJpaRepository.findById(menuId);
        if (findMenu.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 메뉴입니다.");
        }
        return findMenu.get();
    }

    @Override
    public Menu findJoinStoreById(Long menuId) {
        Optional<Menu> findMenu = menuJpaRepository.findJoinStoreById(menuId);
        if (findMenu.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 메뉴입니다.");
        }
        return findMenu.get();
    }
}
