package view;

import java.util.ArrayList;

import model.ObjectiveFunction;
import model.Restriction;

public interface CustomEvent {

	void showGrahp();

	void sentRestrictions(ArrayList<Restriction> restrics, ObjectiveFunction objfun, int i);

	void hiddeGrahp();

}
