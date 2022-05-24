package view;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Control;
import model.Restriction;

public class IOManager extends JFrame implements CustomEventResponse {

	private JPanel graph;
	private JPanel options;
	private Control c;
	public IOManager() {
		
		c = new Control();
		
		c.setEvent(this);
		
		setSize(1040,670);
		setTitle("Método gráfico");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		options = new Menu();
		options.setBounds(5,5,300,625);
		((Menu)options).setEvent(c);
		add(options);
		
		graph = new Tablero();
		                       //x, y
		graph.setBounds(310,5,710,625);
		((Tablero)graph).setEvent(c);
		add(graph);

	}
	@Override
	public void makeGraph() {
		//((Tablero)graph).makeGraphsGm();
		
	}
	@Override
	public void sentResolvedRestrictions(ArrayList<Restriction> restrics, int[] factPointsX,
																	int[] facPointsY, int i) {
		((Tablero)graph).makeGraphsGm(restrics, factPointsX, facPointsY,i);
		
	}
	@Override
	public void sentResult(String result) {
		((Menu)options).showResults(result);
	}
	

}