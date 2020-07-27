package com.pccw.cloud.loadtest.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class MessageDto {
    private String topic;
    private String desc;
    private Object message;
}
