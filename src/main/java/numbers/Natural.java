package numbers;

public class Natural {
	static int MAX = 2147483647;

	int[] tuple;
	int length;

	public Natural(int n) {
		int i = n;
		final int[] tmp = new int[1];
		tmp[0] = 0;
		tuple = tmp;
		length = tmp.length;
		while (i > 0) {
			plusone();
			i--;
		}
	}

	public Natural(int[] value) {
		int[] tmp;
		while (value.length > 1 && value[0] == 0) {
			tmp = new int[value.length - 1];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = value[i + 1];
			}
			value = tmp;
		}
		tuple = value;
		length = value.length;
	}

	public Natural copy() {
		final int[] tmptuple = new int[length];
		for (int i = 0; i < tmptuple.length; i++) {
			tmptuple[i] = tuple[i];
		}
		return (new Natural(tmptuple));
	}

	public int[] tuplecopy() {
		final int[] tmptuple = new int[length];
		for (int i = 0; i < tmptuple.length; i++) {
			tmptuple[i] = tuple[i];
		}
		return (tmptuple);
	}

	public void plusone() {
		int i = 0;
		while (i < length && tuple[length - i - 1] == Natural.MAX - 1) {
			tuple[length - i - 1] = 0;
			i++;
		}
		if (i == length) {
			length += 1;
			final int[] tmp = new int[length];
			tmp[0] = 0;
			for (int j = 0; j < length - 1; j++) {
				tmp[tmp.length - j - 1] = tuple[length - j - 2];
			}
			tmp[0] = 1;
			tuple = tmp;
		} else {
			tuple[length - i - 1] += 1;
		}
	}

	public void minusone() {
		if (length == 1 && tuple[0] == 1) {
			tuple[0] = 0;
		} else {
			int i = 0;
			while (tuple[length - i - 1] == 0 && i < length - 1) {
				tuple[length - i - 1] = Natural.MAX - 1;
				i++;
			}
			tuple[length - i - 1] -= 1;
			if (i == length - 1 && tuple[0] == 0) {
				final int[] tmp = new int[length - 1];
				for (int j = 0; j < tmp.length; j++) {
					tmp[j] = tuple[j + 1];
				}
				tuple = tmp;
				length -= 1;
			}
		}
	}

	public String present() {
		String PRESENT = "", SPACE;
		if (MAX <= 10) {
			SPACE = "";
		} else {
			SPACE = " ";
		}
		for (int i = 0; i < length; i++) {
			PRESENT = PRESENT + SPACE + tuple[i];
		}
		System.out.println(PRESENT);
		return (PRESENT);
	}

}
