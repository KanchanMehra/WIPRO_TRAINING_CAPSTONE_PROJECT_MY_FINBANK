package com.bank.customer_service.dto;

import com.bank.customer_service.entity.ChatMessage;
import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.entity.Loan;
import com.bank.customer_service.entity.Transaction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class to convert between Entities and DTOs
 * Ensures password and sensitive fields are not exposed
 */
public class EntityDtoMapper {

	// ==================== CUSTOMER MAPPERS ====================

	/**
	 * Convert Customer entity to CustomerDTO
	 * Password field is excluded for security
	 */
	public static CustomerDTO toCustomerDTO(Customer customer) {
		if (customer == null) {
			return null;
		}

		return new CustomerDTO(
				customer.getId(),
				customer.getAccountNo(),
				customer.getFirstName(),
				customer.getLastName(),
				customer.getDob(),
				customer.getDateOfOpen(),
				customer.getAddress1(),
				customer.getAddress2(),
				customer.getCity(),
				customer.getState(),
				customer.getZipCode(),
				customer.getCountry(),
				customer.getAmount(),
				customer.getCheqFacil(),
				customer.getAccountType(),
				customer.getUserName(),
				customer.getMobileNo(),
				customer.getEmail(),
				customer.getStatus()
		);
	}

	/**
	 * Convert list of Customer entities to list of CustomerDTOs
	 */
	public static List<CustomerDTO> toCustomerDTOList(List<Customer> customers) {
		if (customers == null) {
			return null;
		}

		return customers.stream()
				.map(EntityDtoMapper::toCustomerDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Convert CustomerDTO to Customer entity
	 * Use this for updates where password is not changed
	 */
	public static Customer toCustomerEntity(CustomerDTO dto) {
		if (dto == null) {
			return null;
		}

		Customer customer = new Customer();
		customer.setId(dto.getId());
		customer.setAccountNo(dto.getAccountNo());
		customer.setFirstName(dto.getFirstName());
		customer.setLastName(dto.getLastName());
		customer.setDob(dto.getDob());
		customer.setDateOfOpen(dto.getDateOfOpen());
		customer.setAddress1(dto.getAddress1());
		customer.setAddress2(dto.getAddress2());
		customer.setCity(dto.getCity());
		customer.setState(dto.getState());
		customer.setZipCode(dto.getZipCode());
		customer.setCountry(dto.getCountry());
		customer.setAmount(dto.getAmount());
		customer.setCheqFacil(dto.getCheqFacil());
		customer.setAccountType(dto.getAccountType());
		customer.setUserName(dto.getUserName());
		customer.setMobileNo(dto.getMobileNo());
		customer.setEmail(dto.getEmail());
		customer.setStatus(dto.getStatus());
		// Note: Password is NOT set - must be handled separately for security

		return customer;
	}

	// ==================== TRANSACTION MAPPERS ====================

	/**
	 * Convert Transaction entity to TransactionDTO
	 */
	public static TransactionDTO toTransactionDTO(Transaction transaction) {
		if (transaction == null) {
			return null;
		}

		return new TransactionDTO(
				transaction.getId(),
				transaction.getTransactionId(),
				transaction.getCustomerId(),
				transaction.getType(),
				transaction.getAmount(),
				transaction.getBalanceBefore(),
				transaction.getBalanceAfter(),
				transaction.getTransactionDate(),
				transaction.getStatus(),
				transaction.getRemarks()
		);
	}

	/**
	 * Convert list of Transaction entities to list of TransactionDTOs
	 */
	public static List<TransactionDTO> toTransactionDTOList(List<Transaction> transactions) {
		if (transactions == null) {
			return null;
		}

		return transactions.stream()
				.map(EntityDtoMapper::toTransactionDTO)
				.collect(Collectors.toList());
	}

	// ==================== CHAT MESSAGE MAPPERS ====================

	/**
	 * Convert ChatMessage entity to ChatMessageDTO
	 */
	public static ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage) {
		if (chatMessage == null) {
			return null;
		}

		return new ChatMessageDTO(
				chatMessage.getId(),
				chatMessage.getSenderId(),
				chatMessage.getSenderType(),
				chatMessage.getSenderName(),
				chatMessage.getRoomId(),
				chatMessage.getMessage(),
				chatMessage.getMessageType(),
				chatMessage.getTimestamp(),
				chatMessage.getReadStatus()
		);
	}

	/**
	 * Convert list of ChatMessage entities to list of ChatMessageDTOs
	 */
	public static List<ChatMessageDTO> toChatMessageDTOList(List<ChatMessage> chatMessages) {
		if (chatMessages == null) {
			return null;
		}

		return chatMessages.stream()
				.map(EntityDtoMapper::toChatMessageDTO)
				.collect(Collectors.toList());
	}

	// ==================== LOAN MAPPERS ====================

	/**
	 * Convert Loan entity to LoanDTO
	 */
	public static LoanDTO toLoanDTO(Loan loan) {
		if (loan == null) {
			return null;
		}

		return new LoanDTO(
				loan.getId(),
				loan.getCustomerId(),
				loan.getLoanApplicationNo(),
				loan.getLoanAmount(),
				loan.getDurationInMonths(),
				loan.getInterestRate(),
				loan.getEmiAmount(),
				loan.getTotalAmountPayable(),
				loan.getTotalInterest(),
				loan.getPurpose(),
				loan.getRequiredByDate(),
				loan.getRemarks(),
				loan.getStatus(),
				loan.getApplicationDate(),
				loan.getApprovalDate(),
				loan.getTotalEmis(),
				loan.getPaidEmis(),
				loan.getRemainingEmis()
		);
	}

	/**
	 * Convert list of Loan entities to list of LoanDTOs
	 */
	public static List<LoanDTO> toLoanDTOList(List<Loan> loans) {
		if (loans == null) {
			return null;
		}

		return loans.stream()
				.map(EntityDtoMapper::toLoanDTO)
				.collect(Collectors.toList());
	}
}

