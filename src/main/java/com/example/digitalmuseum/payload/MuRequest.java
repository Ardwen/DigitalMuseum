package com.example.digitalmuseum.payload;

import java.util.List;

public class MuRequest {
    int page;
    int limit;
    List<Integer> cid;
    String country;
    String searchTerm;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Integer> getCid() {
        return cid;
    }

    public void setCid(List<Integer> cid) {
        this.cid = cid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
