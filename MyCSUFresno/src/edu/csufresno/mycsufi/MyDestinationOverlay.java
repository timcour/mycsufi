package edu.csufresno.mycsufi;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyDestinationOverlay extends Overlay {
	
	private final int mRadius = 5;
	
	//I need the values passed here for locations below are example locations
	Double latitudecoord = 36.813869;
	Double longitudecoord = -119.750398;
	
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		Projection projection = mapView.getProjection();
		
		if (shadow == false) {
			//get the destination location
		
			Double latitude = latitudecoord *1E6;
			Double longitude = longitudecoord * 1E6;
			
			GeoPoint geoPoint;
			geoPoint = new GeoPoint(latitude.intValue(),longitude.intValue());
			
			//convert the location to screen pixels
			Point point = new Point();
			projection.toPixels(geoPoint, point);
			
			RectF oval = new RectF(point.x - mRadius, point.y - mRadius, point.x + mRadius, point.y + mRadius);
			
			//Setup the paint
			Paint paint = new Paint();
			paint.setARGB(255,0,0,255);
			paint.setAntiAlias(true);
			paint.setFakeBoldText(true);
			
			Paint backPaint = new Paint();
			paint.setARGB(200,200,20,20);
			backPaint.setAntiAlias(true);
			
			RectF backRect= new RectF(point.x + 2 + mRadius, point.y - 3*mRadius + 3, point.x + 75, point.y + mRadius + 3);
			
			//draw the marker
			canvas.drawOval(oval,paint);
			canvas.drawRoundRect(backRect, 5, 3, backPaint);
			canvas.drawText("Destination", point.x + 2*mRadius,point.y + 3,paint);
		}
		super.draw(canvas,mapView,shadow);
	}
	@Override
	public boolean onTap(GeoPoint point, MapView mapView) {
		return false;
	}

	Location location;
	
	public Location getLocation() {
		return location;
	}	
	public void setLocation(Location location) {
		this.location = location;
		
	}
}

