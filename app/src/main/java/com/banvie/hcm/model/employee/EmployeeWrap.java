package com.banvie.hcm.model.employee;

import java.util.List;

public class EmployeeWrap<T> {
    public List<T> items;
    public int page;
    public int size;
//    public int totalItems;
    public int totalPages;
    public int totalElements;
    public boolean hasNext;
//    public boolean hasPrevious;
}
