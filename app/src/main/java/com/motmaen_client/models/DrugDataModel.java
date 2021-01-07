package com.motmaen_client.models;

import java.io.Serializable;
import java.util.List;

public class DrugDataModel implements Serializable {
    private List<DrugModel> data;

    public List<DrugModel> getData() {
        return data;
    }
}
