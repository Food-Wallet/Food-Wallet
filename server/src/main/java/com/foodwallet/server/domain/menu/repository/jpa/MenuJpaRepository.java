package com.foodwallet.server.domain.menu.repository.jpa;

import com.foodwallet.server.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuJpaRepository extends JpaRepository<Menu, Long> {
}
