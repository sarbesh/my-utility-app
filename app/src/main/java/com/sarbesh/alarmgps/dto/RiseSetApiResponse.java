package com.sarbesh.alarmgps.dto;

public class RiseSetApiResponse {
    private RiseSetResponse results;
    private String status;

    public RiseSetApiResponse() {
    }

    public RiseSetApiResponse(RiseSetResponse results, String status) {
        this.results = results;
        this.status = status;
    }

    public RiseSetResponse getResults() {
        return results;
    }

    public void setResults(RiseSetResponse results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "riseSetApiResponse{" +
                "results=" + results +
                ", status='" + status + '\'' +
                '}';
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
