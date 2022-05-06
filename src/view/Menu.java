package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import model.Restriction;

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
	private JTextField [] resGird;
	private JComboBox [] equalityCBox;

	public Menu() {

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
		zFuntion.setBounds(40,160,20,20);
		add(zFuntion);

		zx1 = new JTextField();
		zx1.setBounds(70,160,40,20);
		add(zx1);

		zx2 = new JTextField();
		zx2.setBounds(150,160,40,20);
		add(zx2);

		zX1Label = new JLabel("X1 +");
		zX1Label.setBounds(115,160,40,20);
		add(zX1Label);
		//        zx1Label = new JLabel();

		zX2Label = new JLabel("X2");
		zX2Label.setBounds(195,160,40,20);
		add(zX2Label);

		numRestics = new JSpinner(new  SpinnerNumberModel(3, 3, 5, 1));
		numRestics.setBounds(140,133,100,20);
		add(numRestics);

		resolve = new JButton("CONTINUAR");
		resolve.setBounds(90,560,120,30);
		resolve.addActionListener(this);
		add(resolve);
		
		inputTestric = new JLabel("X1         x2         igualdad      cantidad");
		inputTestric.setBounds(60,200,220,20);
		add(inputTestric);
		inputTestric.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String nameBtn = ((AbstractButton) e.getSource()).getText();
        int numRestrictions;
		if(nameBtn.equals("CONTINUAR")) {
			resolve.setText("GRÁFICAR");
			numRestrictions = (int) numRestics.getValue();
			textRestics.setVisible(false);
			numRestics.setVisible(false);
			createRestrictionsLabel(numRestrictions);
			createEqualityComboBox(numRestrictions);
			inputTestric.setVisible(true);
		}else if(nameBtn.equals("GRÁFICAR")){
			numRestrictions = (int) numRestics.getValue();
			getParameters(numRestrictions);
			resolve.setText("VOLVER");
			inputTestric.setVisible(false);
			event.showGrahp();
		}else {
			resolve.setText("CONTINUAR");
			textRestics.setVisible(true);
			numRestics.setVisible(true);
		}

	}

	public void getParameters(int numRestrictions) {
		ArrayList<Restriction> restrics = new ArrayList<Restriction>();
		
		for(int i=0;i<numRestrictions*3;i++) {
			Restriction restriction = new Restriction();
			restriction.setX1(resGird[i].getText());
			restriction.setX2(resGird[i+1].getText());
			restriction.setResourse(resGird[i+2].getText());
			restrics.add(restriction);
			i=i+2;
		}
		
		for(int i=0;i<restrics.size();i++) {
			restrics.get(i).setEquality(String.valueOf(equalityCBox[i]
					.getSelectedItem()));
		}
		event.sentRestrictions(restrics);
	}

	public void createRestrictionsLabel(int restricsNum) {
		resGird = new JTextField[restricsNum*3];
		int size= 40;
		int y=230;
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
			add(resGird[i]);
		}
	} 
	
	public void createEqualityComboBox(int restricsNum) {
		equalityCBox = new JComboBox[restricsNum];
		int y = 230;
		int x = 150;
		
		for(int i=0; i<restricsNum;i++) {
			equalityCBox[i] = new JComboBox();
			equalityCBox[i].setBounds(x,y,60,20);
			equalityCBox[i].addItem("=");
			equalityCBox[i].addItem(">=");
			equalityCBox[i].addItem("<=");
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


}
