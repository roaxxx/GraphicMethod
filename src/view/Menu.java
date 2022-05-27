package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import model.*;

public class Menu extends JPanel implements ActionListener {
	
	private JButton resolve;
	private Image jdcIcon;
	private JLabel iconFrame;
	private JLabel textRestics;
	private JSpinner numRestics;
	private CustomEvent event;
	private JLabel zFuntion;
	private JLabel zX1Label;
	private JTextField zx1;
	private JTextField zx2;
	private JLabel zX2Label;
	private JLabel inputTestric;
	private JRadioButton maximize;
	private JRadioButton minimize;
	private ButtonGroup bg;
	private JTextField [] resGird;
	private JComboBox [] equalityCBox;
	private JLabel answerZfuntion;

	public Menu() {
		this.setBackground(new Color(214, 234, 248 ));
		this.setLayout(null);
		this.setBorder(new TitledBorder(null, "Menu",
				TitledBorder.CENTER, TitledBorder .TOP , null,Color.black));
		jdcIcon = new ImageIcon(getClass().getResource("/Images/jdc.png"))
				.getImage();

		iconFrame = new JLabel();
		iconFrame.setBounds(80,20,220,100);
		iconFrame.setIcon(new ImageIcon(jdcIcon.
				getScaledInstance(140,100,Image.SCALE_SMOOTH)));
		add(iconFrame);

		textRestics = new JLabel("Restricciones:");
		textRestics.setBounds(40,130,240,20);
		add(textRestics);

		zFuntion = new JLabel("Z =");
		zFuntion.setBounds(40,170,20,20);
		add(zFuntion);

		zx1 = new JTextField();
		zx1.setBounds(70,170,40,20);
		add(zx1);

		zx2 = new JTextField();
		zx2.setBounds(150,170,40,20);
		add(zx2);

		zX1Label = new JLabel("X1 +");
		zX1Label.setBounds(115,170,40,20);
		add(zX1Label);
		//        zx1Label = new JLabel();

		zX2Label = new JLabel("X2");
		zX2Label.setBounds(195,170,40,20);
		add(zX2Label);

		numRestics = new JSpinner(new  SpinnerNumberModel(2, 2, 5, 1));
		numRestics.setBounds(140,133,100,20);
		add(numRestics);

		resolve = new JButton("CONTINUAR");
		resolve.setBounds(90,560,120,30);
		resolve.addActionListener(this);
		add(resolve);
		
		inputTestric = new JLabel("X1         x2         igualdad      cantidad");
		inputTestric.setBounds(60,250,260,20);
		add(inputTestric);
		inputTestric.setVisible(false);
		
		answerZfuntion = new JLabel();
		answerZfuntion.setBounds(20,510,260,30);
		answerZfuntion.setOpaque(true);
		answerZfuntion.setBackground(new Color(214, 234, 248 ));
		add(answerZfuntion); 
		
		bg = new ButtonGroup();
		
		maximize = new JRadioButton("Máximizar");
		maximize.setBounds(60,220,90,20);
		maximize.setBackground(new Color(214, 234, 248));
		add(maximize);
		
		bg.add(maximize);
		
		minimize = new JRadioButton("Minimizar");
		minimize.setBounds(160,220,90,20);
		minimize.setBackground(new Color(214, 234, 248));
		add(minimize);
		createEqualityComboBox();
		createRestrictionsLabel();
		bg.add(minimize);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String nameBtn = ((AbstractButton) e.getSource()).getText();
        int numRestrictions;
		if(nameBtn.equals("CONTINUAR")) {
			resolve.setText("GRÁFICAR");
			numRestrictions = (int) numRestics.getValue();
			showItems(numRestrictions);
			inputTestric.setVisible(true);
		}else if(nameBtn.equals("GRÁFICAR")){
			numRestrictions = (int) numRestics.getValue();
			getParameters(numRestrictions);
			resolve.setText("VOLVER");
			event.showGrahp();
		}else {
			resolve.setText("CONTINUAR");
			event.hiddeGrahp();
			resetAllImtems();
			inputTestric.setVisible(false);
			textRestics.setVisible(true);
			numRestics.setVisible(true);
		}
	}

	private void resetAllImtems() {
		for(int i = 0; i< resGird.length;i++) {
			resGird[i].setVisible(false);
			resGird[i].setText("");
		}
		for ( int i = 0; i<equalityCBox.length;i++) {
			equalityCBox[i].setVisible(false);
		}
		
	}

	private void showItems(int numRestrictions) {
		for(int i = 0; i< ((numRestrictions)*3);i++) {
			resGird[i].setVisible(true);
		}
		for ( int i = 0; i<numRestrictions;i++) {
			equalityCBox[i].setVisible(true);
		}
	}

	public void getParameters(int numRestrictions) {
		ArrayList<Restriction> restrics = new ArrayList<Restriction>();
		restrics.add(new Restriction(1.0, 0.0, ">=", 0.0,null, null, 0, 0));
		for(int i=0;i<numRestrictions*3;i++) {
			Restriction restriction = new Restriction();
			restriction.setX1(Double.parseDouble(resGird[i].getText()));
			restriction.setX2(Double.parseDouble(resGird[i+1].getText()));
			restriction.setResourse(Double.parseDouble(resGird[i+2].getText()));
			restrics.add(restriction);
			i=i+2;
	}
		
		for(int i=0;i<restrics.size()-1;i++) {
			restrics.get(i+1).setEquality(String.valueOf(equalityCBox[i]
					.getSelectedItem()));
		}
		ObjectiveFunction objfun = new ObjectiveFunction(Double.parseDouble(zx1.getText()),
				Double.parseDouble(zx2.getText()));
		if(maximize.isSelected()) {
			event.sentRestrictions(restrics,objfun,1);	
		}else if(minimize.isSelected()){
			event.sentRestrictions(restrics,objfun,0);	
		}
		System.out.println("Termino");
	}

	public void createRestrictionsLabel() {
		resGird = new JTextField[21];
		int size= 40;
		int y=280;
		int x=40;
		for (int i=0;i<resGird.length;i++) { 
			resGird[i]= new JTextField();
			if((i+1)%3==0) {
				resGird[i].setBounds(x+60,y,size,20);
				y+=size+10;
				x=40;   	   
			}else {
				resGird[i].setBounds(x,y,size,20);
				x+=size+20;
			}
			resGird[i].setVisible(false);
			add(resGird[i]);
		}
	} 
	
	public void createEqualityComboBox() {
		equalityCBox = new JComboBox[7];
		int y = 280;
		int x = 150;
		
		for(int i=0; i<7;i++) {
			equalityCBox[i] = new JComboBox();
			equalityCBox[i].setBounds(x,y,60,20);
			equalityCBox[i].addItem("=");
			equalityCBox[i].addItem(">=");
			equalityCBox[i].addItem("<=");
			equalityCBox[i].setVisible(false);
			add(equalityCBox[i]);
			y = y+50;
		}
	}

	public CustomEvent getEvent() {
		return event;
	}

	public void setEvent(CustomEvent event) {
		this.event = event;
	}

	public void showResults(String result) {
		answerZfuntion.setText("Z = "+result);
		answerZfuntion.setToolTipText(result);
	}


}
