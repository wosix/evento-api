package com.wojtek.evento.dto;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private String latitude;
    private String longitude;
}
