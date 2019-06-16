package deprecated.regression;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import deprecated.functions.IFunction;

public class DataSet {

	public static LocalDate beginning = LocalDate.of(2007, 10, 1);

	final int days;
	final LocalDate date;
	final Double value;
	final double valueFromRegression;

	public DataSet(final double days, final Double value, final IFunction fun) {
		this.days = (int) days;
		this.date = beginning.plus((int) days, ChronoUnit.DAYS);
		this.value = value;
		this.valueFromRegression = fun.value(new double[] { days });
	}

	@Override
	public String toString() {
		String ans = this.days + " | " + this.date.toString() + " | ";
		if (this.value == null) {
			ans += "|";
		} else {
			ans += Double.toString(this.value).replace(".", ",") + "|";
		}
		return ans += Double.toString(this.valueFromRegression).replace(".", ",");
	}

}
