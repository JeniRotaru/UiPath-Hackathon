package hackathlon.uipath.mapdisplay;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private int vehicleNumber = 32;
    private static int counter = 1;
    private MarkerOptions m, routeStart, routeStop;
    private Marker myMarker;
    private Polyline currentPolyline;
    private double latitudeMovementOffset = 0.000100;
    private double longitudeMovementOffset = 0.000300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_demo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Button b1 = (Button) findViewById(R.id.send_button);
        final EditText et = (EditText) findViewById(R.id.input_text);

        m = new MarkerOptions();
        routeStart = new MarkerOptions().position(new LatLng(44.399165, 26.046882)).title("Location 1");
        routeStop = new MarkerOptions().position(new LatLng(44.426615, 26.100245)).title("Location 2");


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (et.getText().toString().matches("")) {
                    //Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MapsActivity.this, "Please, enter the route!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                new FetchURL(MapsActivity.this).execute(getUrl(routeStart.getPosition(), routeStop.getPosition(), "driving"), "driving");
                plotStations(mMap);
                List<Object> dummyArray = new DummyArray().getList();
                DummyElement currentLocation = (DummyElement) dummyArray.get(dummyArray.size() - 1);
                DummyElement previousLocation = (DummyElement) dummyArray.get(dummyArray.size() - 2);

                final double startingLatitude = 44.399165;
                final double startingLongitude = 26.046882;
                final double stopLatitude = 44.426615;
                final double stopLongitude = 26.100245;

                LatLng vehicleStartLocation = new LatLng(startingLatitude, startingLongitude);
                myMarker = mMap.addMarker(new MarkerOptions().position(vehicleStartLocation)
                        .title("32")
                        .icon(BitmapDescriptorFactory.fromBitmap(setMarkerDrawable(vehicleNumber))));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vehicleStartLocation, 18));

                final double newVehicleLat = startingLatitude + (counter * latitudeMovementOffset);
                final double newVehicleLon = startingLongitude + (counter * longitudeMovementOffset);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            LatLng newLocation = new LatLng(newVehicleLat, newVehicleLon);
//                            counter++;
//                            myMarker.setPosition(newLocation);
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 18));
//                        }
//                    }, 1000);
//                final Handler handler = new Handler();
//                Runnable runnableCode = new Runnable() {
//                    @Override
//                    public void run() {
//                        LatLng newLocation = new LatLng(startingLatitude + (counter * latitudeMovementOffset), startingLongitude + (counter * longitudeMovementOffset));
//                        counter++;
//                        myMarker.setPosition(newLocation);
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 18));
//                        Log.d("Handlers", "Called on main thread");
//                        handler.postDelayed(this, 2000);
//                    }
//                };
                //int count2 = 1;
                //while (count2 < 10) {
                    LatLng newLocation = new LatLng(startingLatitude + (counter * latitudeMovementOffset), startingLongitude + (counter * longitudeMovementOffset));
                    counter++;
                    myMarker.setPosition(newLocation);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 18));
                   // count2++;
                   // try{
                   //     Thread.sleep(1000);
                   // }catch (Exception e){}
                //}
//                for (int i = 0; i < 10; i++)
//                    handler.postDelayed(runnableCode, 2000);
            }
            //myMarker.remove();

        });
    }

    public void plotStations(GoogleMap gMap) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.new_bus_station_icon_9);
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.399165, 26.046882)).title("Depou Alexandria"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.400448, 26.0504428)).title("Antiaeriana -> Ajunge in 3 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.403172, 26.057152)).title("Margeanului -> Ajunge in 5 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.405732, 26.063236)).title("Piata Rahova -> Ajunge in 8 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.410186, 26.069429)).title("Petre Ispirescu -> Ajunge in 10 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.413277, 26.073855)).title("Calea Ferentari -> Ajunge in 12 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.415078, 26.077353)).title("Sos. Progresului -> Ajunge in 15 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.417124, 26.081044)).title("Piata Chirigiu -> Ajunge in 18 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.421937, 26.091043)).title("Piata Regina Maria -> Ajunge in 21 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.423753, 26.094948)).title("11 Iunie -> Ajunge in 24 min."));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.426615, 26.100245)).title("Piata Unirii -> Ajunge in 26 min."));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        //mMap.addMarker(routeStart);
        //mMap.addMarker(routeStop);

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(44.399165, 26.046882), 18));
//        List<Object>  dummyArray = new DummyArray().getList();
//        DummyElement currentLocation = (DummyElement)dummyArray.get(dummyArray.size() - 1);
//        DummyElement previousLocation = (DummyElement)dummyArray.get(dummyArray.size() - 2);
//
//        LatLng location1 = new LatLng(previousLocation.lat, previousLocation.lng);
//        myMarker = mMap.addMarker(new MarkerOptions().position(location1)
//                .title("Marker in Bucharest")
//                .icon(BitmapDescriptorFactory.fromBitmap(setMarkerDrawable(vehicleNumber))));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 18));
//        LatLng bucharest = new LatLng(44.439663, 26.096306);
//        mMap.addMarker(new MarkerOptions().position(bucharest)
//                .title("Marker in Bucharest")
//                .icon(BitmapDescriptorFactory.fromBitmap(setMarkerDrawable(vehicleNumber))));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucharest, 18));
    }

    private void moveToCurrentLocation(LatLng currentLocation, GoogleMap gm) {
        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        gm.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        gm.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }

    private LatLng DummyMove(LatLng latLongObj) {
        return new LatLng(latLongObj.longitude + 0.10,
                latLongObj.latitude + 0.10
        );

    }

    public Bitmap setMarkerDrawable(int number) {
        int background = R.drawable.custom_marker;
        Bitmap icon = drawTextToBitmap(background, String.valueOf(number));
        return icon;
    }


    public Bitmap drawTextToBitmap(int gResId, String gText) {
        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /* SET FONT COLOR (e.g. WHITE -> rgb(255,255,255)) */
        paint.setColor(Color.rgb(255, 255, 255));
        /* SET FONT SIZE (e.g. 15) */
        paint.setTextSize((int) (15 * scale));
        /* SET SHADOW WIDTH, POSITION AND COLOR (e.g. BLACK) */
        paint.setShadowLayer(1f, 0f, 1f, Color.BLACK);

        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;
        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service

        String key = "";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + key;
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

}
