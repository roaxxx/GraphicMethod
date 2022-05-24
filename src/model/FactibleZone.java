package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.lang.*;

public class FactibleZone {
	private double factZX1;
	private double factZX2;
	private String eqPoints;

	public FactibleZone(double factZoneX1, double factZoneX2, String equalityPoints) {
		this.factZX1 = factZoneX1;
		this.factZX2 = factZoneX2;
		this.eqPoints = equalityPoints;
	}

	public FactibleZone() {

	}

	public double getFactZX1() {
		return factZX1;
	}

	public void setFactZX1(double factZoneX1) {
		this.factZX1 = factZoneX1;
	}

	public double getFactZX2() {
		return factZX2;
	}

	public void setFactZX2(double factZoneX2) {
		this.factZX2 = factZoneX2;
	}
	
	
	public String getEqPoints() {
		return eqPoints;
	}

	public void setEqPoints(String equalityPoints) {
		this.eqPoints = equalityPoints;
	}

	//Método para determinar la zona factible
	public ArrayList<FactibleZone> makeFatibleZone(ArrayList<Restriction> restrics,
			Restriction actualRes, ArrayList<FactibleZone> factPoints){
		double y = 0;
		double x = 0;
		for (IntersectionPoints inps: actualRes.getIntersectionPoints()) {
			boolean isOnFacZone = true;
			for(Restriction rest : restrics) {
				if(!rest.equals(actualRes)) {
					y = (rest.getResourse() - (inps.getItPointX())*rest.getX1())/rest.getX2();
					x = (rest.getResourse() - (inps.getItPointY())*rest.getX2())/rest.getX1();
					if(y<0.0) {
						y = 0;
					}else if(x<0.0) {
						x = 0;
					}
					if(rest.getEquality().equals("<=") && (Math.round(x) < Math.round(inps.getItPointX())
							|| Math.round(y)  < Math.round(inps.getItPointY())) ) {		
						if(y ==4.0) {
						}
						isOnFacZone = false;
					}else if(rest.getEquality().equals(">=") && (Math.round(x) > (inps.getItPointX())
							|| Math.round(y)  > Math.round(inps.getItPointY()))) {
						isOnFacZone = false;
					}else if(rest.getEquality().equals("=") && (Math.round(x) != (inps.getItPointX())
							|| Math.round(y)  != Math.round(inps.getItPointY()))) {
						isOnFacZone = false;
					}
				}
			}
			
			if(isOnFacZone && !isOn(factPoints, inps.getItPointX(), inps.getItPointY())) {
				factPoints.add(new FactibleZone(inps.getItPointX(), inps.getItPointY(),
						actualRes.getEquality()));
			}
		}
		return factPoints;
	}
	
	//Escalado de puntos factibles 
	public ArrayList<FactibleZone> scalePoints(ArrayList<FactibleZone> factPoints,
												double scaleX,double scaleY) {
		
		ArrayList<Integer> xPoints = new ArrayList<Integer>();
		for (FactibleZone faPoints: factPoints) {

			if((int) ((scaleX*faPoints.getFactZX1())+60)>660) {
				xPoints.add(660);
				faPoints.setFactZX1(660);
				faPoints.setFactZX2(60);
			}else {	
				xPoints.add((int) ((scaleX*faPoints.getFactZX1())+60));
				faPoints.setFactZX1((int) ((scaleX*faPoints.getFactZX1())+60));
				faPoints.setFactZX2((int) (560-((scaleY*faPoints.getFactZX2()))));
			}
		}
		return orderXPoints(xPoints, factPoints);
	}
	
	//Ordenar puntos de zona factible
	public ArrayList<FactibleZone> orderXPoints(ArrayList<Integer> xPoints,
												ArrayList<FactibleZone> factPoints){
		
			ArrayList<FactibleZone> orderedFactPoints = new ArrayList<FactibleZone>();
			Collections.sort(xPoints);
			for ( int i = 0; i< xPoints.size();i++) {	
				orderedFactPoints.add(getFactPoint(xPoints.get(i), orderedFactPoints, factPoints));
			}
		return orderedFactPoints;
	}
	
	//Retorna un punto de zona factible especifico
	public FactibleZone getFactPoint(int xPoint,ArrayList<FactibleZone> orderedFactPoints,
			ArrayList<FactibleZone> factPoints) {
		
		for (FactibleZone factibleZone : factPoints) {
			if(xPoint == (int) factibleZone.getFactZX1() && !isOnFactibleZone(orderedFactPoints, factibleZone)) {
				return factibleZone;
			}
		}
		
		return null;
	}
	
	//Determina si ya existe un determinado punto en la lista de puntos oredenados
	public boolean isOnFactibleZone(ArrayList<FactibleZone> orderedFactPoints,
			FactibleZone factibleZone) {
		for (FactibleZone factZone : orderedFactPoints) {
			if(factZone.getFactZX1()==factibleZone.getFactZX1()
					&& factZone.getFactZX2()==factibleZone.getFactZX2()) {
				return true;
			}
		}
		return false;
	}

	//Método para determinar si ya existe el punto factible
	public boolean isOn(ArrayList<FactibleZone> factPoints, double x, double y) {
		for (FactibleZone factibleZone : factPoints) {
			if(factibleZone.getFactZX1()==x && factibleZone.getFactZX2()==y) {
				return true;
			}
		}
		return false;
	}
}