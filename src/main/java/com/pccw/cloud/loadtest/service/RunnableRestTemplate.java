package com.pccw.cloud.loadtest.service;

import com.pccw.cloud.loadtest.domain.LoadTestDetail;
import com.pccw.cloud.loadtest.web.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RunnableRestTemplate implements Runnable {

    private MessageDto messageDto;
    private RestTemplate restTemplate;
    private LoadTestDetail loadTestDetail;

    public RunnableRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public void run() {
        log.info("Change message description according to thread name.");
        messageDto.setDesc("This message is executed by thread: " + loadTestDetail.getThreadName());

        Integer callsPerThread = loadTestDetail.getCallsPerThread();
        log.info("Number of calls per thread: " + callsPerThread);
        for (int i = 0; i < callsPerThread; i++) {
            this.forwardMessage();
        }
    }

    private void forwardMessage() {
        String url = loadTestDetail.getUrl();
        log.info("Forwarding to URL: " + url);
        this.restTemplate.postForLocation(url, messageDto);
        log.info("Done sending to URL: " + url);
    }

    public void setMessageDto(MessageDto messageDto) {
        this.messageDto = messageDto;
    }

    public void setLoadTestDetail(LoadTestDetail loadTestDetail) {
        this.loadTestDetail = loadTestDetail;
    }
}
