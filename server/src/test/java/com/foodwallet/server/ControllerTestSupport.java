package com.foodwallet.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.controller.store.StoreApiController;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.interceptor.query.ApiQueryCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@WebMvcTest(controllers = {StoreApiController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ApiQueryCounter apiQueryCounter;

    @MockBean
    protected FileStore fileStore;

    @MockBean
    protected StoreService storeService;
}
