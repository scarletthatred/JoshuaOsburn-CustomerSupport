package com.example.customersupport.site;


import java.util.Arrays;

public class Attachment {
private String name;
private byte[] contents;

    public Attachment() {
        super();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "name='" + name + '\'' +
                ", contents=" + Arrays.toString(contents) +
                '}';
    }
}