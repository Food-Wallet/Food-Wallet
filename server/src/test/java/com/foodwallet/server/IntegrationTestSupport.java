package com.foodwallet.server;

import com.foodwallet.server.api.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    @MockBean
    protected FileStore fileStore;
}
