package com.pccw.cloud.loadtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoadTestDetail {
    private String url;
    private String threadName;
    private Integer callsPerThread;
}
