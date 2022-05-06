package model;

import java.util.ArrayList;

public class Restriction {
	private String x1;
	private String x2;
	private String equality;
	private String resourse;
	private String xpoint;
	private String ypoint;
	private int pixelsY;
	private int pixelsX;

	//Constructor
	public Restriction() {

	}

	//Getters y setters
	public String getX1() {
		return x1;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public String getX2() {
		return x2;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public String getEquality() {
		return equality;
	}

	public void setEquality(String equality) {
		this.equality = equality;
	}

	public String getResourse() {
		return resourse;
	}

	public void setResourse(String resourse) {
		this.resourse = resourse;
	}

	public String getXpoint() {
		return xpoint;
	}

	public void setXpoint(String xpoint) {
		this.xpoint = xpoint;
	}

	public String getYpoint() {
		return ypoint;
	}

	public void setYpoint(String ypoint) {
		this.ypoint = ypoint;
	}


	public int getPixelsY() {
		return pixelsY;
	}

	public void setPixelsY(int pixelsY) {
		this.pixelsY = pixelsY;
	}

	public int getPixelsX() {
		return pixelsX;
	}

	public void setPixelsX(int pixelsX) {
		this.pixelsX = pixelsX;
	}

	public ArrayList<Restriction> setCantesianPoints(ArrayList<Restriction> restrics) {
		for (int i =0;i<restrics.size();i++) {
			Restriction r = new Restriction();
			r = restrics.get(i);
			double x = 0;
			double y = 0; 
			if(!r.getResourse().equals("0")) {

				if(r.getX1().equals("0")) {
					x = 0;
				}else {
					x = Double.parseDouble(r.getResourse())/Double.parseDouble(r.getX1()); 
				}
				if( r.getX2().equals("0")) {
					y = 0;
				}else {
					y = Double.parseDouble(r.getResourse())/Double.parseDouble(r.getX2());	
				}
			}else {
				y = Double.parseDouble(r.getX2())/Double.parseDouble(r.getX1());
				x = Double.parseDouble(r.getX1())/Double.parseDouble(r.getX2());
			}
			restrics.get(i).setXpoint(x+"");		
			restrics.get(i).setYpoint(y+"");	
		}
		return restrics;
	}


	public ArrayList<Restriction>  scaleDisplay(ArrayList<Restriction> restrics) {
		restrics = setCantesianPoints(restrics);
		double xScale = 600/getXHigher(restrics);
		double ySclae = 500/getYHigher(restrics);
		for(int i = 0; i<restrics.size();i++) {
			if(Double.parseDouble(restrics.get(i).getXpoint())==0){
				restrics.get(i).setPixelsX(0);
			}else {
				restrics.get(i).setPixelsX((int) (xScale*(Double.parseDouble(restrics.get(i)
						.getXpoint())))+60);
			}
			if(Double.parseDouble(restrics.get(i).getYpoint())==0) {
				restrics.get(i).setPixelsY(0);
			}else {  	   
				restrics.get(i).setPixelsY((int) (560 -(ySclae*(Double.parseDouble(restrics.get(i)
						.getYpoint())))));
			}
		}
		return restrics;
	}
	public double getYHigher(ArrayList<Restriction> restrics) {
		double ymax = 0;
		for (int i =0;i<restrics.size();i++) {

			if(Double.parseDouble(restrics.get(i).getYpoint())>ymax) {
				ymax = Double.parseDouble(restrics.get(i).getYpoint());
			}
		}
		return  ymax;
	}

	public double getXHigher(ArrayList<Restriction> restrics) {
		double xmax = 0;
		for (int i =0;i<restrics.size();i++) {

			if(Double.parseDouble(restrics.get(i).getXpoint())> xmax) {
				xmax = Double.parseDouble(restrics.get(i).getXpoint());
			}
		}
		return  xmax;
	}
}