package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Restriction {
	private Double x1;
	private Double x2;
	private String equality;
	private Double resourse;
	private ArrayList<IntersectionPoints> intersectionPoints;
	private CutPoints cutPoints;
	private int pixelsY;
	private int pixelsX;

	//Constructor
	public Restriction() {

	}

	//Constructor con campos
	public Restriction(Double x1, Double x2, String equality, Double resourse,
			ArrayList<IntersectionPoints> intersectionPoints,CutPoints cutPoints,
			int pixelsY, int pixelsX) {

		this.x1 = x1;
		this.x2 = x2;
		this.equality = equality;
		this.resourse = resourse;
		this.pixelsY = pixelsY;
		this.pixelsX = pixelsX;
		this.intersectionPoints = intersectionPoints;
		this.cutPoints = cutPoints;
	}


	//Getters y setters
	public Double getX1() {
		return x1;
	}

	public void setX1(Double x1) {
		this.x1 = x1;
	}

	public Double getX2() {
		return x2;
	}

	public void setX2(Double x2) {
		this.x2 = x2;
	}

	public String getEquality() {
		return equality;
	}

	public void setEquality(String equality) {
		this.equality = equality;
	}

	public Double getResourse() {
		return resourse;
	}

	public void setResourse(Double resourse) {
		this.resourse = resourse;
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


	public ArrayList<IntersectionPoints> getIntersectionPoints() {
		return intersectionPoints;
	}


	public void setIntersectionPoints(ArrayList<IntersectionPoints> intersectionPoints) {
		this.intersectionPoints = intersectionPoints;
	}


	public CutPoints getCutPoints() {
		return cutPoints;
	}


	public void setCutPoints(CutPoints cutPoints) {
		this.cutPoints = cutPoints;
	}

	//Método que determina los puntos de corte de una función
	public ArrayList<Restriction> getCutPoints(ArrayList<Restriction> restrics) {
		double x = 0;
		double y = 0; 
		
		for (Restriction rest : restrics) {
			Restriction r = new Restriction();
			r = rest;
			if(r.getResourse()!=0) {
				if(r.getX1()==0) {
					x = 0;
				}else {
					x = r.getResourse() / r.getX1(); 
				}
				if( r.getX2()==0) {
					y = 0;
				}else {
					y = r.getResourse() / r.getX2();	
				}
			}else {
				if(x == 0 || y == 0 || (x == 0 && y == 0 )) {
					x = 0;
					y = 0;
				}else {
					y = r.getX2() / r.getX1();
					x = r.getX1() / r.getX2();	
				}
			}
			CutPoints cpoints =new CutPoints();
			cpoints.setY(y);
			cpoints.setX(x);
			rest.setCutPoints(cpoints);
		
		}
		return restrics;
	}


	//Método que escala los puntos de corte
	public ArrayList<Restriction>  resolveRestrictions(ArrayList<Restriction> restrics,
			double scaleX, double scaleY) {

		restrics = getCutPoints(restrics);
		for (Restriction restric : restrics) {
			if(restric.getCutPoints().getX()==0){
				restric.setPixelsX(0);
			}else {
				restric.setPixelsX((int) (scaleX*(restric
						.getCutPoints().getX()))+60);
			}
			if(restric.getCutPoints().getY()==0) {
				restric.setPixelsY(0);
			}else {  	   
				restric.setPixelsY((int) (560 -(scaleY*restric
						.getCutPoints().getY())));
			}
			restric.setIntersectionPoints(makeIntersections(restrics, restric));
		}
		return restrics;
	}
	//Método que determina el mayor valor de  en el eje Y y x
	public double [] getHigher(ArrayList<Restriction> restrics) {
		restrics = getCutPoints(restrics);
		double [] highers = new double [2]; 
		for (int i =0;i<restrics.size();i++) {
			if(restrics.get(i).getCutPoints().getY()>highers[1]) {
				highers[1] =  restrics.get(i).getCutPoints().getY();
			}
			if(restrics.get(i).getCutPoints().getX()>highers[0]) {
				highers[0] =  restrics.get(i).getCutPoints().getX();
			}
		}	
		highers[0] = 600 / highers[0];
		highers[1] = 500 / highers[1];
		return  highers;
	}

	//Método para determinar los puntos de intersección
	public ArrayList<IntersectionPoints> makeIntersections(ArrayList<Restriction> restrics,
			Restriction actualRes){
		double x = 0;
		double y = 0;
		double ae = 0;
		double bd = 0;
		ArrayList<IntersectionPoints> itPoints = new ArrayList<IntersectionPoints>();
		
		itPoints.add(new IntersectionPoints(actualRes.getCutPoints().getY(),0));
		itPoints.add(new IntersectionPoints(0,actualRes.getCutPoints().getX()));

		for (Restriction restriction : restrics) {
			if(!restriction.equals(actualRes)) {
				IntersectionPoints itPoint = new IntersectionPoints();
				ae = actualRes.getX1() * restriction.getX2();
				bd = actualRes.getX2() * restriction.getX1();
				x = (((actualRes.getResourse()*restriction.getX2()) - (actualRes.getX2()
						*restriction.getResourse())) / ((ae-bd)));
				y =(((actualRes.getX1() * restriction.getResourse()) - (actualRes.getResourse()
						*restriction.getX1())) / ((ae-bd)));
				if(x>0 ) {
					itPoint.setItPointX(x);			
				}else if ((ae - bd) == 0 ){
					itPoint.setItPointX(0);
				}
				if(y>0) {
					itPoint.setItPointY(y);			
				}else if ((ae- bd) == 0 ){
					itPoint.setItPointY(0);
				}
				itPoints.add(itPoint);
			}			
		}	
		return itPoints;
	}

}