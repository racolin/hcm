package com.banvie.hcm.model;

import com.banvie.hcm.type.ToolId;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Tool implements Serializable {
    public ToolId id;
    public String name;
    public byte[] image;
    public boolean isShow = true;

    public Tool() {

    }

    public Tool(ToolId id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tool tool = (Tool) o;
        return name.equals(tool.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
