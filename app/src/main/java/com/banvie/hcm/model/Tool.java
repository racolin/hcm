package com.banvie.hcm.model;

import java.util.Arrays;
import java.util.Objects;

public class Tool {
    private String name;
    private byte[] image;
    private boolean isShow = true;

    public Tool(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public boolean setShow() {
        return true;
    }

    public void unsetShow() {
        isShow = false;
    }

    public boolean getShow() {
        return isShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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
