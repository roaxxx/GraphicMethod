package view;

import java.awt.Color;

import java.awt.BasicStroke;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.Restriction;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Tablero extends JPanel{
	private JLabel equality1;
	private JLabel equality2;
	private JLabel equality3;
	private JLabel [] pointX;
	private JLabel [] pointY;
	private CustomEvent event;
	private boolean flag;
	private int [] vectorX;
	private int [] vectorY;
	private int factPointN;
	private ArrayList<Restriction> rRestrictions;

	public Tablero() {
		//this.setBackground(new Color(11, 83, 69 ));
		this.setLayout(null);
		this.setBorder(new TitledBorder(null, "Tablero",
				TitledBorder.CENTER, TitledBorder .TOP , null,Color.black));	
		this.setBackground(new Color(213, 245, 227));
		flag = false;
		equality1 = new JLabel("   =");
		equality1.setBounds(150,30,30,15);
		equality1.setOpaque(true);
		equality1.setBackground(new Color(169, 50, 38));
		equality1.setForeground(Color.white);
		equality1.setVisible(false);
		add(equality1);

		equality2 = new JLabel("  <=");
		equality2.setBounds(190,30,30,15);
		equality2.setOpaque(true);
		equality2.setBackground(new Color(244, 81, 30));
		equality2.setForeground(Color.white);
		equality2.setVisible(false);
		add(equality2);

		equality3 = new JLabel("  >=");
		equality3.setBounds(230,30,30,15);
		equality3.setOpaque(true);
		equality3.setBackground(new Color(69, 39, 160));
		equality3.setForeground(Color.white);
		equality3.setVisible(false);
		add(equality3);
				
		createXpoints();
		createYpoints();
	}

	public void createXpoints() {
		pointX = new JLabel[7];
		int size= 40;
		for (int i=0;i<7;i++) { 
			pointX[i]= new JLabel();
			add(pointX[i]);
		}
	}

	public void createYpoints() {
		pointY = new JLabel[7];
		int size= 40;

		for (int i=0;i<7;i++) { 
			pointY[i]= new JLabel();
			add(pointY[i]);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		if(flag) {	

			Graphics2D g2d = (Graphics2D) g;
			BasicStroke grosor = new BasicStroke(2);

			g2d.setStroke(grosor);

			g.setColor(new Color(0, 255, 255 ));
			g.fillPolygon(vectorX, vectorY,factPointN);
			//xi,yi,xf,yf
			g2d.setColor(Color.black);
			g2d.drawLine(60,60,60,560);//y
			g2d.drawLine(60,560,660,560);//X

			
			for (Restriction r : rRestrictions) {

				int x = r.getPixelsX();
				int y = r.getPixelsY();
				if(r.getEquality().equals("=")) {			
					g.setColor(new Color(169, 50, 38));
				} else if(r.getEquality().equals("<=")) {
					g.setColor(new Color(244, 81, 30));
				} else {
					g.setColor(new Color(69, 39, 160 ));
				}
				if(y == 0 && x != 0) {      	
					g.drawLine(x,60,x,560);
				}else if(x == 0 && y != 0) {   
					g.drawLine(60,y,600,y);
				}else if (r.getResourse()==0 &&(x != 0 || y != 0)) {
					g.drawLine(60,560,660,y);	 
				}else if(x == 0 && y == 0){
					g.drawLine(60,560,61,561);
				}else {	
					g.drawLine(60,y,x,560);
				}

			}
		}
	}

	public void makeGraphsGm(ArrayList<Restriction> restrics, int[] factPointsX,
			int[] facPointsY, int i) {
		this.vectorX = factPointsX;
		this.vectorY = facPointsY;
		this.factPointN = i;
		rRestrictions = restrics;
		flag = true;
		System.out.println("Llego");
		repaint();
		equality1.setVisible(true);
		equality2.setVisible(true);
		equality3.setVisible(true);
		setPoints();
	}

	public void setPoints() {

		for(int i = 0;i <rRestrictions.size() ;i++) {
			int x = rRestrictions.get(i).getPixelsX();
			int y = rRestrictions.get(i).getPixelsY();
			pointX[i].setText(String.valueOf(rRestrictions.get(i).getCutPoints().getX()));
			pointY[i].setText(String.valueOf(rRestrictions.get(i).getCutPoints().getY()));
			pointX[i].setToolTipText(String.valueOf(rRestrictions.get(i).getCutPoints().getX()));
			pointY[i].setToolTipText(String.valueOf(rRestrictions.get(i).getCutPoints().getY()));
			if(x == 0 && y != 0) {
				pointX[i].setBounds((x+60),570,50,10);
				pointY[i].setBounds(20,y,50,10);		
			}else if(x != 0 && y == 0) {
				pointX[i].setBounds(x,570,50,10);
				pointY[i].setBounds(20,y+560,50,10);			
			}else if(x == 0 && y == 0) {
				pointX[i].setBounds((30),570,50,10);
			}else {
				pointX[i].setBounds(x,570,50,10);
				pointY[i].setBounds(20,y,50,10);
			}
		}
	}



	public CustomEvent getEvent() {
		return event;
	}

	public void setEvent(CustomEvent event) {
		this.event = event;
	}

	public void makeInvisibleGraph() {
		flag = false;
		repaint();
		for ( int i = 0; i<pointX.length;i++) {
			pointX[i].setText("");
			pointY[i].setText("");
			pointX[i].setToolTipText("");
			pointY[i].setToolTipText("");
		}
		
	}


}
