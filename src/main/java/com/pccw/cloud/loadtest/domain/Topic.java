package com.pccw.cloud.loadtest.domain;

public enum Topic {

    CUST_OPT("CUST_optOut_optIn", "/api/v1/producer/CUST_optOut_optIn"),
    PROD_OFFER("prod_offer", "/api/v1/producer/prod_offer"),
    CUST_EMAIL("cust_update_email", "/api/v1/producer/cust_update_email");

    private String name;
    private String url;

    private Topic(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }


}
