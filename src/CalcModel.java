
public class CalcModel {
	private double result;
	private double x;
	private double memory;
	
	public CalcModel() {
		reset();
		memory=0;
	}
	
	public void memoryAdd(double y) {
		memory+=y;
	}
	
	public void memoryClear() {
		memory=0;
	}
	
	public double getMemory() {
		return memory;
	}
	
	private void addition(double y) {
		result=x+y;
	}
	
	private void substraction(double y) {
		result=x-y;
	}
	
	private void multiplication(double y) {
		result=x*y;
	}
	
	private void division(double y) {
		if(y!=0) {
			result=x/y;
		}
		else result=0;
	}
	
	public void reset() {
		result=0;
		x=0;
	}
	
	public double getResult() {
		return result;
	}
	
	public void calculate(String operation, double y) {
		if(operation.contains("+")) {
			addition(y);
		}
		else if(operation.contains("-")) {
			substraction(y);
		}
		else if(operation.contains("*")) {
			multiplication(y);
		}
		else if(operation.contains("/")) {
			division(y);
		}
		else {
			result=y;
		}
		x=result;
	}
}
