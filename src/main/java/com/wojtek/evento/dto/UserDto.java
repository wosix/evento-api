package com.wojtek.evento.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@Builder
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;

    @NotEmpty
    @NotNull
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 3, max = 16)
    private String username;

    @Size(min = 7, max = 30)
    private String password;

    private List<EventDto> eventDtoList;

    public UserDto(@NotBlank @Size(min = 3, max = 16) String username, @Size(min = 7, max = 30) String password) {
        this.username = username;
        this.password = password;
    }
}
