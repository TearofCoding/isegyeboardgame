package com.accio.isegye.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateStoreRequest {

    @NotBlank(message = "매장 이름 필수")
    private String storeName;

    private Integer hourFee;
}