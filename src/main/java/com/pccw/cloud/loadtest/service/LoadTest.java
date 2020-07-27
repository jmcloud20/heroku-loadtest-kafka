package com.pccw.cloud.loadtest.service;

import com.pccw.cloud.loadtest.domain.LoadTestDetail;
import com.pccw.cloud.loadtest.domain.Topic;
import com.pccw.cloud.loadtest.web.model.loadtest.LoadTestingParameterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@ConfigurationProperties("loadtest")
public class LoadTest {

    private LoadTestingParameterDTO loadTestingParameterDTO;
    private final RunnableRestTemplate runnableRestTemplate;
    private List<Thread> runningThreads;

    private String api;

    public LoadTest(RunnableRestTemplate runnableRestTemplate) {
        this.runnableRestTemplate = runnableRestTemplate;
    }


    public void start() {

        if (runningThreads == null)
            this.createAndRunThreads();
        else {
            log.info("Threads are already running.");
            this.stop();
            this.createAndRunThreads();
        }
    }

    private void createAndRunThreads() {
        int numOfThread = loadTestingParameterDTO.getNumberOfThread();
        int interval = loadTestingParameterDTO.getInterval();
        LoadTestDetail loadTestDetail = this.createLoadTestDetail();

        runnableRestTemplate.setMessageDto(loadTestingParameterDTO.getMessageDto());

        runningThreads = new ArrayList<Thread>(numOfThread);
        log.info("Number of threads to be created: " + numOfThread);
        for (int i = 0; i < numOfThread; i++) {
            String threadName = "LoadTestThread-" + i;

            log.info("Creating thread: " + threadName);
            loadTestDetail.setThreadName(threadName);

            this.runnableRestTemplate.setLoadTestDetail(loadTestDetail);

            Thread thread = new Thread(this.runnableRestTemplate, threadName);
            runningThreads.add(thread);
            thread.start();

            log.info("seconds per call: " + interval);
            try {
                Thread.sleep(interval * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (runningThreads != null && !runningThreads.isEmpty()) {
            log.info("Currently running threads: " + runningThreads.size());
            log.info("Stopping currently running threads.");
            for (Thread runningThread : runningThreads) {
                runningThread.interrupt();
            }
            runningThreads = null;
        } else {
            log.info("No threads currently running.");
        }
    }

    private LoadTestDetail createLoadTestDetail() {
        String url = "";
        for (Topic value : Topic.values()) {
            if (value.getName().equalsIgnoreCase(loadTestingParameterDTO.getTopic())) {
                url = value.getUrl();
            }
        }
        return LoadTestDetail.builder()
                .url(this.api+url)
                .callsPerThread(loadTestingParameterDTO.getTotalCallPerThread())
                .build();
    }

    public void setLoadTestingParameterDTO(LoadTestingParameterDTO loadTestingParameterDTO) {
        this.loadTestingParameterDTO = loadTestingParameterDTO;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
