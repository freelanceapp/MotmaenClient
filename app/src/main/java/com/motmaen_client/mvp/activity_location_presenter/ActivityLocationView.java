package com.motmaen_client.mvp.activity_location_presenter;

import com.google.android.gms.maps.model.LatLng;

public interface ActivityLocationView {
    void onLocationChanged(LatLng latLng);
}
