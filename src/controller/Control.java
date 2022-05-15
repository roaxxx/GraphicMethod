package controller;

import java.util.ArrayList;
import java.util.Iterator;

import model.FactibleZone;
import model.IntersectionPoints;
import model.Restriction;
import view.CustomEvent;
import view.CustomEventResponse;
import view.IOManager;

public class Control implements CustomEvent {
	
	private CustomEventResponse event;
	
	public Control() {
		
	}
	
	public void init() {
		IOManager iom  = new IOManager();
		iom.setVisible(true);
	}

	public CustomEventResponse getEvent() {
		return event;
	}

	public void setEvent(CustomEventResponse event) {
		this.event = event;
	}

	@Override
	public void showGrahp() {
		event.makeGraph();
		
	}

	@Override
	public void sentRestrictions(ArrayList<Restriction> restrics) {
		ArrayList<FactibleZone> itcp = new ArrayList<FactibleZone>();
		Restriction restriction = new Restriction();
		FactibleZone fz = new FactibleZone();
		
		double [] scale = new double [2]; 
		scale = restriction.getHigher(restrics);
		System.out.println(" x:"+scale[0]+" y:"+scale[1]);
		//Resuelve las restricciones
		restrics = restriction.resolveRestrictions(restrics, scale[0], scale[1]);
		
		//Devuelve los puntos que conforman la zona factible
		for (Restriction r : restrics) {
			itcp = fz.makeFatibleZone(restrics, r,itcp);
			
		}
		printArray(itcp);
		
		itcp = fz.scalePoints(itcp,scale[0], scale[1]);
		System.out.println(" Escalado");
		printArray(itcp);
		
		int [] factPointsY = new int [itcp.size()];
		int [] factPointsX = new int [itcp.size()];
		
		for (int i = 0; i< itcp.size() ;i++) {
			factPointsX[i] = (int) itcp.get(i).getFactZoneX1();
			factPointsY[i] = (int) itcp.get(i).getFactZoneX2();
		}
		event.sentResolvedRestrictions(restrics, factPointsX, factPointsY, itcp.size());
	}

	
	private void printArray(ArrayList<FactibleZone> itcp) {
		for (FactibleZone it : itcp) {
			System.out.println("X:"+it.getFactZoneX1()+" Y:"+it.getFactZoneX2());
		}
	}

}
