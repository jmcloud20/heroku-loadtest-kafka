package com.pccw.cloud.loadtest.web.controller;

import com.pccw.cloud.loadtest.service.ProducerService;
import com.pccw.cloud.loadtest.web.model.MessageDto;
import com.pccw.cloud.loadtest.web.model.loadtest.LoadTestingParameterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class LoadTestController {

    private ProducerService producerService;

    public LoadTestController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/loadTest")
    @ResponseStatus(HttpStatus.OK)
    public void loadTesting(
            @RequestParam String action,
            @RequestParam String topic,
            @RequestParam int numberOfThread,
            @RequestParam int totalCallPerThread,
            @RequestParam int interval,
            @RequestBody MessageDto messageDto
    ) {
        log.info("Action: " + action);
        log.info("Topic: " + topic);
        log.info("Number of Thread: " + numberOfThread);
        log.info("Total call per Thread: " + totalCallPerThread);
        log.info("Interval: " + interval);
        log.info("Request Body: " + messageDto);

        producerService.startLoadTest(LoadTestingParameterDTO.builder()
                .action(action)
                .topic(topic)
                .numberOfThread(numberOfThread)
                .totalCallPerThread(totalCallPerThread)
                .interval(interval)
                .messageDto(messageDto).build());

    }

}
