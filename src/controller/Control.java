package controller;

import java.util.ArrayList;
import java.util.Iterator;

import model.FactibleZone;
import model.IntersectionPoints;
import model.ObjectiveFunction;
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

	private void printArray(ArrayList<FactibleZone> itcp) {
		for (FactibleZone it : itcp) {
			System.out.println("X:"+it.getFactZX1()+" Y:"+it.getFactZX2()+"eq"
					+it.getEqPoints());
		}
	}

	@Override
	public void sentRestrictions(ArrayList<Restriction> restrics, ObjectiveFunction objfun,
			int zFun) {
		ArrayList<FactibleZone> itcp = new ArrayList<FactibleZone>();
		Restriction restriction = new Restriction();
		ObjectiveFunction objetiveFun = new ObjectiveFunction();
		FactibleZone fz = new FactibleZone();
		
		double [] scale = new double [2]; 
		scale = restriction.getHigher(restrics);

		//Resuelve las restricciones
		restrics = restriction.resolveRestrictions(restrics, scale[0], scale[1]);
		
		//Devuelve los puntos que conforman la zona factible
		for (Restriction r : restrics) {
			itcp = fz.makeFatibleZone(restrics, r,itcp);
			
		}
		
		objetiveFun.getZMax(scale, objetiveFun,itcp);
		printArray(itcp);
		
		if(zFun ==1) {
			event.sentResult(objetiveFun.getZMax(scale, objfun,itcp));
		}else if(zFun == 0){
			event.sentResult(objetiveFun.getZMin(scale, objfun,itcp));
		}
		
		System.out.println(" Escalado");
		printArray(itcp);
		
		int [] factPointsY = new int [itcp.size()];
		int [] factPointsX = new int [itcp.size()];
		
		itcp = fz.scalePoints(itcp,scale[0], scale[1]);
		
		for (int i = 0; i< itcp.size() ;i++) {
			factPointsX[i] = (int) itcp.get(i).getFactZX1();
			factPointsY[i] = (int) itcp.get(i).getFactZX2();
		}
		event.sentResolvedRestrictions(restrics, factPointsX, factPointsY, itcp.size());
	}

}
