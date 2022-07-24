package com.banvie.hcm.param;

public class EmployeeParam {
    public EmployeeParam(int page, String search, boolean isMore) {
        this.page = page;
        this.search = search;
        this.isMore = isMore;
    }

    public int page;
    public String search;
    public boolean isMore;
}
