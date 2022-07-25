package com.banvie.hcm.model.organization_chart;

import com.banvie.hcm.model.summary.JobTitle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationChart implements Serializable {
    public String id = "";
    public String fullName = "";
    public String username = "";
    public JobTitle jobTitle = new JobTitle();
    public Profile profile = new Profile();
    public byte[] image_bytes = null;
    public String code = "";
    public List<OrganizationChart> descendants = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationChart that = (OrganizationChart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id);
        return result;
    }
}
