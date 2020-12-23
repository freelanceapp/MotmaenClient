package com.motmaen_client.models;

import java.io.Serializable;

public class DiseaseModel implements Serializable {
    private int id;
    private String title;
    private String is_dangerous;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIs_dangerous() {
        return is_dangerous;
    }
}
