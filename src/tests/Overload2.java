package tests;

public class Overload2 {
	
	public int max(int i,int j){
		return (i>j)?i:j;
	}
	
	public int max(int i,short j){
		return (i>j)?i:j;
	}
	
	public double max(double d1,double d2){
		return (d1>d2)?d1:d2;
	}
	
	public double max(double d1,double d2,double d3){
		return (max(d1,d2)>d3)?max(d1,d2):d3;
	}
	
	public void mOL(int i){
		System.out.println(i*i);
	}
	
	public void mOL(int i,int j){
		System.out.println(i*j);
	}

	public void mOL(String str){
		System.out.println(str);
	}

}
