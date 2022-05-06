package controller;

import java.util.ArrayList;
import java.util.Iterator;

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
		Restriction restriction = new Restriction();
		//restrics = restriction.setCantesianPoints(restrics);
		
		restrics = restriction.scaleDisplay(restrics);
		
		event.sentResolvedRestrictions(restrics);
	}

}
