package edu.csufresno.mycsufi;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Projection;

public class MyPositionOverlay extends com.google.android.maps.Overlay {
	
	private final int mRadius = 5;

	
	public void draw(Canvas canvas, com.google.android.maps.MapView mapView, boolean shadow) {
		Projection projection = mapView.getProjection();
		
		if (shadow == false) {
			//get the current location
			Double latitude = location.getLatitude()*1E6;
			Double longitude = location.getLongitude()*1E6;
			
			//testing purpose with no gps you need to input locations for it to display
			//Double latitude = 36.81258 *1E6;
			//Double longitude = -119.751562 * 1E6;
			
			GeoPoint geoPoint;
			geoPoint = new GeoPoint(latitude.intValue(),longitude.intValue());
			
			//convert the location to screen pixels
			Point point = new Point();
			projection.toPixels(geoPoint, point);
			
			RectF oval = new RectF(point.x - mRadius, point.y - mRadius, point.x + mRadius, point.y + mRadius);
			
			//Setup the paint
			Paint paint = new Paint();
			paint.setARGB(0,0,0,255);
			paint.setAntiAlias(true);
			paint.setFakeBoldText(true);
			
			Paint backPaint = new Paint();
			paint.setARGB(200,20,175,20);
			backPaint.setAntiAlias(true);
			
			
			
			RectF backRect= new RectF(point.x + 2 + mRadius, point.y - 3*mRadius + 3, point.x + 80, point.y + mRadius + 3);
			
			//draw the marker
			canvas.drawOval(oval,paint);
			canvas.drawRoundRect(backRect, 5, 3, backPaint);
			canvas.drawText("You are here", point.x + 2*mRadius,point.y + 3,paint);
		}
		super.draw(canvas,mapView,shadow);
	}
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

