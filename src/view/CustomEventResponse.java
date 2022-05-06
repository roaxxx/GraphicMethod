package view;

import java.util.ArrayList;

import model.Restriction;

public interface CustomEventResponse {

	void makeGraph();

	void sentResolvedRestrictions(ArrayList<Restriction> restrics);

}
