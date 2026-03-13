package dev.jaimerey.accounts.service.impl;

import dev.jaimerey.accounts.dto.CardsDto;
import dev.jaimerey.accounts.dto.CustomerDetailsDto;
import dev.jaimerey.accounts.dto.LoansDto;
import dev.jaimerey.accounts.entity.Accounts;
import dev.jaimerey.accounts.entity.Customer;
import dev.jaimerey.accounts.exception.ResourceNotFoundException;
import dev.jaimerey.accounts.repository.AccountsRepository;
import dev.jaimerey.accounts.repository.CustomerRepository;
import dev.jaimerey.accounts.service.client.CardsFeignClient;
import dev.jaimerey.accounts.service.client.LoansFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class CustomersServiceImplTest {
//
//    @Mock
//    private AccountsRepository accountsRepository;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private CardsFeignClient cardsFeignClient;
//
//    @Mock
//    private LoansFeignClient loansFeignClient;
//
//    @InjectMocks
//    private CustomersServiceImpl customersService;
//
//    private Customer customer;
//    private Accounts accounts;
//    private CardsDto cardsDto;
//    private LoansDto loansDto;
//    private String mobileNumber;
//
//    @BeforeEach
//    void setUp() {
//        mobileNumber = "9876543210";
//
//        // Setup Customer
//        customer = new Customer();
//        customer.setCustomerId(1L);
//        customer.setName("John Doe");
//        customer.setEmail("john.doe@example.com");
//        customer.setMobileNumber(mobileNumber);
//
//        // Setup Accounts
//        accounts = new Accounts();
//        accounts.setAccountNumber(1000000001L);
//        accounts.setCustomerId(1L);
//        accounts.setAccountType("Savings");
//        accounts.setBranchAddress("123 Main Street, New York");
//
//        // Setup CardsDto
//        cardsDto = new CardsDto();
//        cardsDto.setCardNumber("4000123456789012");
//        cardsDto.setCardType("Credit Card");
//        cardsDto.setMobileNumber(mobileNumber);
//        cardsDto.setTotalLimit(100000);
//        cardsDto.setAmountUsed(10000);
//        cardsDto.setAvailableAmount(90000);
//
//        // Setup LoansDto
//        loansDto = new LoansDto();
//        loansDto.setLoanNumber("1234567890");
//        loansDto.setLoanType("Home Loan");
//        loansDto.setMobileNumber(mobileNumber);
//        loansDto.setTotalLoan(500000);
//        loansDto.setAmountPaid(50000);
//        loansDto.setOutstandingAmount(450000);
//    }
//
//    @Test
//    @DisplayName("Should fetch customer details successfully with all information")
//    void fetchCustomerDetails_Success() {
//        // Given
//        when(customerRepository.findByMobileNumber(mobileNumber))
//                .thenReturn(Optional.of(customer));
//        when(accountsRepository.findByCustomerId(customer.getCustomerId()))
//                .thenReturn(Optional.of(accounts));
//        when(loansFeignClient.fetchLoanDetails(mobileNumber))
//                .thenReturn(new ResponseEntity<>(loansDto, HttpStatus.OK));
//        when(cardsFeignClient.fetchCardDetails(mobileNumber))
//                .thenReturn(new ResponseEntity<>(cardsDto, HttpStatus.OK));
//
//        // When
//        CustomerDetailsDto result = customersService.fetchCustomerDetails(mobileNumber);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getName()).isEqualTo("John Doe");
//        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
//        assertThat(result.getMobileNumber()).isEqualTo(mobileNumber);
//
//        // Verify Accounts
//        assertThat(result.getAccountsDto()).isNotNull();
//        assertThat(result.getAccountsDto().getAccountNumber()).isEqualTo(1000000001L);
//        assertThat(result.getAccountsDto().getAccountType()).isEqualTo("Savings");
//
//        // Verify Cards
//        assertThat(result.getCardsDto()).isNotNull();
//        assertThat(result.getCardsDto().getCardNumber()).isEqualTo("4000123456789012");
//        assertThat(result.getCardsDto().getCardType()).isEqualTo("Credit Card");
//
//        // Verify Loans
//        assertThat(result.getLoansDto()).isNotNull();
//        assertThat(result.getLoansDto().getLoanNumber()).isEqualTo("1234567890");
//        assertThat(result.getLoansDto().getLoanType()).isEqualTo("Home Loan");
//
//        // Verify all methods were called
//        verify(customerRepository, times(1)).findByMobileNumber(mobileNumber);
//        verify(accountsRepository, times(1)).findByCustomerId(customer.getCustomerId());
//        verify(loansFeignClient, times(1)).fetchLoanDetails(mobileNumber);
//        verify(cardsFeignClient, times(1)).fetchCardDetails(mobileNumber);
//    }
//
//    @Test
//    @DisplayName("Should throw ResourceNotFoundException when customer not found")
//    void fetchCustomerDetails_CustomerNotFound() {
//        // Given
//        when(customerRepository.findByMobileNumber(mobileNumber))
//                .thenReturn(Optional.empty());
//
//        // When & Then
//        assertThatThrownBy(() -> customersService.fetchCustomerDetails(mobileNumber))
//                .isInstanceOf(ResourceNotFoundException.class)
//                .hasMessageContaining("Customer")
//                .hasMessageContaining("mobileNumber")
//                .hasMessageContaining(mobileNumber);
//
//        // Verify only customer repository was called
//        verify(customerRepository, times(1)).findByMobileNumber(mobileNumber);
//        verify(accountsRepository, never()).findByCustomerId(anyLong());
//        verify(loansFeignClient, never()).fetchLoanDetails(anyString());
//        verify(cardsFeignClient, never()).fetchCardDetails(anyString());
//    }
//
//    @Test
//    @DisplayName("Should throw ResourceNotFoundException when account not found")
//    void fetchCustomerDetails_AccountNotFound() {
//        // Given
//        when(customerRepository.findByMobileNumber(mobileNumber))
//                .thenReturn(Optional.of(customer));
//        when(accountsRepository.findByCustomerId(customer.getCustomerId()))
//                .thenReturn(Optional.empty());
//
//        // When & Then
//        assertThatThrownBy(() -> customersService.fetchCustomerDetails(mobileNumber))
//                .isInstanceOf(ResourceNotFoundException.class)
//                .hasMessageContaining("Account")
//                .hasMessageContaining("customerId");
//
//        // Verify repositories were called but not feign clients
//        verify(customerRepository, times(1)).findByMobileNumber(mobileNumber);
//        verify(accountsRepository, times(1)).findByCustomerId(customer.getCustomerId());
//        verify(loansFeignClient, never()).fetchLoanDetails(anyString());
//        verify(cardsFeignClient, never()).fetchCardDetails(anyString());
//    }
//
//    @Test
//    @DisplayName("Should fetch customer details even if cards service returns null")
//    void fetchCustomerDetails_CardsServiceReturnsNull() {
//        // Given
//        when(customerRepository.findByMobileNumber(mobileNumber))
//                .thenReturn(Optional.of(customer));
//        when(accountsRepository.findByCustomerId(customer.getCustomerId()))
//                .thenReturn(Optional.of(accounts));
//        when(loansFeignClient.fetchLoanDetails(mobileNumber))
//                .thenReturn(new ResponseEntity<>(loansDto, HttpStatus.OK));
//        CardsDto nullCards = null;
//        when(cardsFeignClient.fetchCardDetails(mobileNumber))
//                .thenReturn(new ResponseEntity<>(nullCards, HttpStatus.OK));
//
//        // When
//        CustomerDetailsDto result = customersService.fetchCustomerDetails(mobileNumber);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getName()).isEqualTo("John Doe");
//        assertThat(result.getAccountsDto()).isNotNull();
//        assertThat(result.getLoansDto()).isNotNull();
//        assertThat(result.getCardsDto()).isNull(); // Cards should be null
//
//        verify(cardsFeignClient, times(1)).fetchCardDetails(mobileNumber);
//    }
//
//    @Test
//    @DisplayName("Should fetch customer details even if loans service returns null")
//    void fetchCustomerDetails_LoansServiceReturnsNull() {
//        // Given
//        when(customerRepository.findByMobileNumber(mobileNumber))
//                .thenReturn(Optional.of(customer));
//        when(accountsRepository.findByCustomerId(customer.getCustomerId()))
//                .thenReturn(Optional.of(accounts));
//        LoansDto nullLoans = null;
//        when(loansFeignClient.fetchLoanDetails(mobileNumber))
//                .thenReturn(new ResponseEntity<>(nullLoans, HttpStatus.OK));
//        when(cardsFeignClient.fetchCardDetails(mobileNumber))
//                .thenReturn(new ResponseEntity<>(cardsDto, HttpStatus.OK));
//
//        // When
//        CustomerDetailsDto result = customersService.fetchCustomerDetails(mobileNumber);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getName()).isEqualTo("John Doe");
//        assertThat(result.getAccountsDto()).isNotNull();
//        assertThat(result.getLoansDto()).isNull(); // Loans should be null
//        assertThat(result.getCardsDto()).isNotNull();
//
//        verify(loansFeignClient, times(1)).fetchLoanDetails(mobileNumber);
//    }
}

