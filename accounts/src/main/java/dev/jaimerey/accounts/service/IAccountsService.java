package dev.jaimerey.accounts.service;

import dev.jaimerey.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the data transfer object containing customer information
     */
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
