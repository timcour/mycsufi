package edu.csufresno.mycsufi;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;


public class Where extends MapActivity {
    /** Called when the activity is first created. */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	MapController mapController;
	MyPositionOverlay positionOverlay;
	MyDestinationOverlay destinationOverlay;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
        
        //Get a reference to the MapView
        MapView myMapView = (MapView)findViewById(R.id.myMapView);
        //get the map view's controller
        mapController = myMapView.getController();
        
        // Configure the map display options
        myMapView.setSatellite(true);
        myMapView.setStreetView(true);
        myMapView.displayZoomControls(false);
        
        //Zoom in
        mapController.setZoom(17);
        
        //add the MyPositionOverlay
        positionOverlay = new MyPositionOverlay();
        List<Overlay> overlays = myMapView.getOverlays();
        overlays.add(positionOverlay);
        
        //add the MyDestinationOverlay
        destinationOverlay = new MyDestinationOverlay();
        List<Overlay> overlays1 = myMapView.getOverlays();
        overlays.add(destinationOverlay);
        
        LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(context);
        
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        
        Location location = locationManager.getLastKnownLocation(provider);
        updateWithNewLocation(location);
        
        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
    }
    
    private void updateWithNewLocation(Location location) {
    	String latLongString;
    	TextView myLocationText;
    	myLocationText = (TextView)findViewById(R.id.myLocationText);
    	if (location!=null) {
    		
    		//update the location marker
    		positionOverlay.setLocation(location);
    		destinationOverlay.setLocation(location);
    		
    		//update the map location.
    		Double geoLat = location.getLatitude()*1E6;
    		Double geoLng = location.getLongitude()*1E6;
    		GeoPoint point = new GeoPoint(geoLat.intValue(),geoLng.intValue());
    		
    		mapController.animateTo(point);
    		
    		double lat = location.getLatitude();
    		double lng = location.getLongitude();
    		latLongString = "Lat:" + lat + "\nLong:" + lng;
    		
    	}
    	else {
    		// center map to default location center of school 
    		Double geoLat = 36.812276 *1E6;
    		Double geoLng = -119.746858*1E6;
    		GeoPoint point = new GeoPoint(geoLat.intValue(),geoLng.intValue());
    		
    		mapController.animateTo(point);
    		//end part
    		latLongString = "No location found, you are lost";
    	}
    	myLocationText.setText("Your Current Position is: \n" + latLongString);
    }


	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}
		
		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
		}
		
		public void onProviderEnabled(String provider) {}
		public void onStatusChanged(String provider, int status, Bundle extras) {}
	};
}