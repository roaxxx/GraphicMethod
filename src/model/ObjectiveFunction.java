package model;

import java.util.ArrayList;

public class ObjectiveFunction {
	private double zfuntX1;
	private double zfuntX2;
	
	public ObjectiveFunction() {
		
	}

	public ObjectiveFunction(double zfuntionX1, double zfuntionX2) {
		this.zfuntX1 = zfuntionX1;
		this.zfuntX2 = zfuntionX2;
	}

	public double getZfuntX1() {
		return zfuntX1;
	}

	public void setZfuntX1(double zfuntionX1) {
		this.zfuntX1 = zfuntionX1;
	}

	public double getZfuntX2() {
		return zfuntX2;
	}

	public void setZfuntX2(double zfuntionX2) {
		this.zfuntX2 = zfuntionX2;
	}
	
	public String getZMax(double[] scale, ObjectiveFunction obFun,
			ArrayList<FactibleZone> itcp) {
		String result = " ";
		double zValue = 0;
		double actualValue = 0;
		for (FactibleZone facZone : itcp) {
			actualValue = (obFun.getZfuntX1()*facZone.getFactZX1())
					+(obFun.getZfuntX2()*facZone.getFactZX2());
			
			if(zValue<actualValue) {
				zValue = ((obFun.getZfuntX1()*facZone.getFactZX1())
						+(obFun.getZfuntX2()*facZone.getFactZX2()));
				result = facZone.getFactZX1()+" * ("+obFun.getZfuntX1()+") "+" + "
						+facZone.getFactZX2()+" * ("+obFun.getZfuntX2()+") "+" = "+
						zValue;
			}
		}
		if(hasMultipleSolution(scale, itcp)) {
			result = "Tiene multiples soluciones";
		}
		return result;
	}
	
	public String getZMin(double[] scale, ObjectiveFunction obFun,
			ArrayList<FactibleZone> itcp) {
		String result = " ";
		double zValue = 999999999;
		double actualValue = 0;
		for (FactibleZone facZone : itcp) {
			actualValue = (obFun.getZfuntX1()*facZone.getFactZX1())
					+(obFun.getZfuntX2()*facZone.getFactZX2());
			if(zValue>actualValue) {	
				zValue = ((obFun.getZfuntX1()*facZone.getFactZX1())
						+(obFun.getZfuntX2()*facZone.getFactZX2()));
				result = facZone.getFactZX1()+" * ("+obFun.getZfuntX1()+") "+" + "
						+facZone.getFactZX2()+" * ("+obFun.getZfuntX2()+") "+" = "+
						zValue;
			}
		}
		return result;
	}
	public boolean hasMultipleSolution(double[] scale,ArrayList<FactibleZone> itcp) {
		double x = ((600/scale[0]));
		double y = ((500/scale[1])); 
		int count = 0;
		for (FactibleZone factibleZone : itcp) {
			if(factibleZone.getFactZX1()==x && factibleZone.getEqPoints()
					.equals(">=")) {
				count++;
			}else if (factibleZone.getFactZX2()==y && factibleZone.getEqPoints()
					.equals(">=")) {
				count++;
			}	
		}
		if(count ==2) {
			return true;
		}
		return false;
	}
}
