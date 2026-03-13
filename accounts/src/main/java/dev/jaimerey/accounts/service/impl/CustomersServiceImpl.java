package dev.jaimerey.accounts.service.impl;

import dev.jaimerey.accounts.dto.AccountsDto;
import dev.jaimerey.accounts.dto.CardsDto;
import dev.jaimerey.accounts.dto.CustomerDetailsDto;
import dev.jaimerey.accounts.dto.LoansDto;
import dev.jaimerey.accounts.entity.Accounts;
import dev.jaimerey.accounts.entity.Customer;
import dev.jaimerey.accounts.exception.ResourceNotFoundException;
import dev.jaimerey.accounts.mapper.AccountsMapper;
import dev.jaimerey.accounts.mapper.CustomerMapper;
import dev.jaimerey.accounts.repository.AccountsRepository;
import dev.jaimerey.accounts.repository.CustomerRepository;
import dev.jaimerey.accounts.service.ICustomersService;
import dev.jaimerey.accounts.service.client.CardsFeignClient;
import dev.jaimerey.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if (loansDtoResponseEntity != null) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);

        if (null != cardsDtoResponseEntity) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }
        return customerDetailsDto;

    }
}