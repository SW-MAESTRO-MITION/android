package com.mition.realty.model;

import java.io.Serializable;

/**
 * Created by dn2757 on 2017. 12. 2..
 */

public class PDFFile implements Serializable {
    private String name;
    private String Absolutepath;
    public boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolutepath() {
        return Absolutepath;
    }

    public void setAbsolutepath(String absolutepath) {
        Absolutepath = absolutepath;
    }
}
