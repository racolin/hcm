package com.banvie.hcm.model.policy;

import java.util.ArrayList;
import java.util.List;

public class DataPolicy {
    public List<Policy> items = new ArrayList<>();
    public int page = 0;
    public int size = 0;
    public int totalItems = 0;
    public int totalPages = 0;
    public int totalElements = 0;
    public boolean hasNext = false;
    public boolean hasPrevious = false;
}
