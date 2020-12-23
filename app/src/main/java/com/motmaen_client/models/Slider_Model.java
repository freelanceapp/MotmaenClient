package com.motmaen_client.models;

import java.io.Serializable;
import java.util.List;

public class Slider_Model implements Serializable {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data implements Serializable {
       private String image;

        public String getImage() {
            return image;
        }
    }
}
