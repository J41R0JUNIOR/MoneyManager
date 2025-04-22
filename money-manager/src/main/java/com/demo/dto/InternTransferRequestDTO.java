package com.demo.dto;

public record InternTransferRequestDTO(Long id, Double amount, Long walletSenderId, Long walletReceiverId, Long cardSenderId, Long cardReceiverId) {
}
