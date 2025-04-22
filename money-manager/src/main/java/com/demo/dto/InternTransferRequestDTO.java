package com.demo.dto;

public record InternTransferRequestDTO(Long id, float amount, Long walletSenderId, Long walletReceiverId, Long cardSenderId, Long cardReceiverId) {
}
