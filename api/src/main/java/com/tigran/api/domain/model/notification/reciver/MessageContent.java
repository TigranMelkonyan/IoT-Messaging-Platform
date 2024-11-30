package com.tigran.api.domain.model.notification.reciver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 8:12 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageContent {

    private String title;
    private String text;
    private String topic;
}
