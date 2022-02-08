package com.example.project1.dto;

public class SearchRequest {

    private String email;

    public SearchRequest(String email) {
        this.email = email;
    }

    public SearchRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "email='" + email + '\'' +
                '}';
    }
}
