package com.motmaen_client.models;

import java.io.Serializable;
import java.util.List;

public class DoctorModel implements Serializable {
    private List<SingleDoctorModel> data;

    public List<SingleDoctorModel> getData() {
        return data;
    }
}
