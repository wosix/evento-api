package com.wojtek.evento.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Builder
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;

    @NotEmpty
    @NotNull
    private String email;

    @Size(min = 7, max = 30)
    private String password;
}
