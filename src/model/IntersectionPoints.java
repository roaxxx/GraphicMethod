package model;

public class IntersectionPoints {
	private double itPointY;
	private double itPointX;


	public IntersectionPoints() {
		
	}	
	

	public IntersectionPoints(double itPointY, double itPointX) {
		this.itPointY = itPointY;
		this.itPointX = itPointX;
	}
	public double getItPointY() {
		return itPointY;
	}
	public void setItPointY(double itPointY) {
		this.itPointY = itPointY;
	}
	public double getItPointX() {
		return itPointX;
	}
	public void setItPointX(double itPointX) {
		this.itPointX = itPointX;
	}
}
