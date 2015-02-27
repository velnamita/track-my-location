package com.techmagic.locationapp.data;

import android.content.Context;
import android.net.Uri;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.techmagic.locationapp.data.model.LocationData;

import java.util.List;

public class DataHelper {

    private static DataHelper instance;
    private Context context;

    private DataHelper(Context context) {
        this.context = context;
    }

    public static DataHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataHelper(context);
        }
        return instance;
    }

    public void saveLocation(LocationData data) {
        data.save();
        notifyChanged(Data.LocationData.URI);
    }

    public List<LocationData> getAllLocations() {
        List<LocationData> data = new Select().from(LocationData.class).execute();
        return data;
    }

    public LocationData getLastLocation() {
        List<LocationData> data = new Select().from(LocationData.class).orderBy(Data.LocationData.COLUMN_TIMESTAMP + " DESC").limit(String.valueOf(1)).execute();
        LocationData locationData = data != null && data.size() > 0 ? data.get(0) : null;
        return locationData;
    }

    public void deleteAllLocations() {
        new Delete().from(LocationData.class).execute();
    }

    private void notifyChanged(Uri uri) {
        context.getContentResolver().notifyChange(uri, null);
    }

}
