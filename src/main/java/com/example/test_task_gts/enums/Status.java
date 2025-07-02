package com.example.test_task_gts.enums;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@NotNull
@Getter
public enum Status {
    TABLE_ALREADY_EXISTS(420, "таблица с таким именем уже существует"),
    TABLE_NOT_FOUND(421, "таблица c таким именемне найдена");

    private final Integer code;
    private final String status;
}
