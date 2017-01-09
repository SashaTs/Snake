package test;

import java.awt.Point;

public class BonusPoint {
	private Point location;
	
	public BonusPoint(Point location) {
		this.location = location;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}
}
