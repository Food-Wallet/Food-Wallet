package com.foodwallet.server.domain.menu.repository.jpa;

import com.foodwallet.server.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuJpaRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m join fetch m.store s where m.id=:menuId")
    Optional<Menu> findJoinStoreById(Long menuId);

}
