package com.pccw.cloud.loadtest.service;

import com.pccw.cloud.loadtest.web.model.loadtest.LoadTestingParameterDTO;
import org.springframework.stereotype.Component;

@Component
public class ProducerService {

    private LoadTest loadTest;

    public ProducerService(LoadTest loadTest) {
        this.loadTest = loadTest;
    }

    public void startLoadTest(LoadTestingParameterDTO loadTestingParameterDTO) {
        switch (loadTestingParameterDTO.getAction()){
            case "start":
                loadTest.setLoadTestingParameterDTO(loadTestingParameterDTO);
                loadTest.start();
                break;
            case "stop":
                loadTest.stop();
                break;
            default:
                throw new IllegalArgumentException("Invalid parameter value: action");
        }
    }
}
