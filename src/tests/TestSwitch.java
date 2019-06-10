package tests;

public class TestSwitch {
	
	public void test1() {
		int x = 2;
		
		switch(x) {
		case(2):
			break;
		case(3):
			break;
		}
		
		Square s = null;
		
		switch(s.getLength()) {
		case(10):
			break;
		}

	}
	
	class Square {

		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

}
