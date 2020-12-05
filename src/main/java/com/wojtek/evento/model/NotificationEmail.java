package com.wojtek.evento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    private String subject;
    private String receiver;
    private String body;
}

