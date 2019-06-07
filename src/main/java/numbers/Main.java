package numbers;

public class Main {

	public static void main(String[] args) {
		final Functions fun = new Functions();
		final int NUMBER = 5000;
		final Binary bin = new Binary(NUMBER);

		// fun.faculty(new natural(NUMBER)).present();
		fun.fak(NUMBER).present();
		fun.fak(bin).present();
	}
}
