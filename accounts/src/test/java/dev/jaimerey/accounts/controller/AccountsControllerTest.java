package dev.jaimerey.accounts.controller;

import dev.jaimerey.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AccountsController.class)
@AllArgsConstructor
class AccountsControllerTest {

    private final MockMvc mockMvc;
    @Autowired
    private IAccountsService iAccountsService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createAccount() {

    }

    @Test
    void fetchAccountDetails() throws Exception {


        mockMvc.perform(get("/api/fetch/1234567890")
                .contentType("application/json"));
    }

    @Test
    void updateAccountDetails() {

    }

    @Test
    void deleteAccountDetails() {

    }
}