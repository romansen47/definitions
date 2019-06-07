package regression;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import functions.IFunction;

public class DataSet {

	public static LocalDate beginning = LocalDate.of(2007, 10, 1);

	final int days;
	final LocalDate date;
	final Double value;
	final double valueFromRegression;

	public DataSet(double days, Double value, IFunction fun) {

		this.days = (int) days;
		date = beginning.plus((int) days, ChronoUnit.DAYS);
		this.value = value;
		valueFromRegression = fun.value(new double[] { days });
	}

	@Override
	public String toString() {
		String ans = days + " | " + date.toString() + " | ";
		if (value == null) {
			ans += "|";
		} else {
			ans += Double.toString(value).replace(".", ",") + "|";
		}
		return ans += Double.toString(valueFromRegression).replace(".", ",");
	}

}
