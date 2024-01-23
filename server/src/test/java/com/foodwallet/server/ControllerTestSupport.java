package com.foodwallet.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.controller.bookmark.BookmarkApiController;
import com.foodwallet.server.api.controller.bookmark.BookmarkQueryApiController;
import com.foodwallet.server.api.controller.member.AccountApiController;
import com.foodwallet.server.api.controller.menu.MenuApiController;
import com.foodwallet.server.api.controller.store.StoreApiController;
import com.foodwallet.server.api.controller.store.StoreQueryApiController;
import com.foodwallet.server.api.service.bookmark.BookmarkQueryService;
import com.foodwallet.server.api.service.bookmark.BookmarkService;
import com.foodwallet.server.api.service.member.AccountService;
import com.foodwallet.server.api.service.member.MemberQueryService;
import com.foodwallet.server.api.service.member.MemberService;
import com.foodwallet.server.api.service.menu.MenuService;
import com.foodwallet.server.api.service.store.StoreQueryService;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.interceptor.query.ApiQueryCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@WebMvcTest(controllers = {
    StoreApiController.class, StoreQueryApiController.class,
    MenuApiController.class, BookmarkApiController.class,
    BookmarkQueryApiController.class, AccountApiController.class
})
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

    @MockBean
    protected StoreQueryService storeQueryService;

    @MockBean
    protected MenuService menuService;

    @MockBean
    protected BookmarkService bookmarkService;

    @MockBean
    protected BookmarkQueryService bookmarkQueryService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected MemberQueryService memberQueryService;

    @MockBean
    protected AccountService accountService;
}
