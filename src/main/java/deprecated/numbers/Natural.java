package deprecated.numbers;

public class Natural {
	static int MAX = 2147483647;

	int[] tuple;
	int length;

	public Natural(final int n) {
		int i = n;
		final int[] tmp = new int[1];
		tmp[0] = 0;
		this.tuple = tmp;
		this.length = tmp.length;
		while (i > 0) {
			this.plusone();
			i--;
		}
	}

	public Natural(int[] value) {
		int[] tmp;
		while ((value.length > 1) && (value[0] == 0)) {
			tmp = new int[value.length - 1];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = value[i + 1];
			}
			value = tmp;
		}
		this.tuple = value;
		this.length = value.length;
	}

	public Natural copy() {
		final int[] tmptuple = new int[this.length];
		for (int i = 0; i < tmptuple.length; i++) {
			tmptuple[i] = this.tuple[i];
		}
		return (new Natural(tmptuple));
	}

	public int[] tuplecopy() {
		final int[] tmptuple = new int[this.length];
		for (int i = 0; i < tmptuple.length; i++) {
			tmptuple[i] = this.tuple[i];
		}
		return (tmptuple);
	}

	public void plusone() {
		int i = 0;
		while ((i < this.length) && (this.tuple[this.length - i - 1] == (Natural.MAX - 1))) {
			this.tuple[this.length - i - 1] = 0;
			i++;
		}
		if (i == this.length) {
			this.length += 1;
			final int[] tmp = new int[this.length];
			tmp[0] = 0;
			for (int j = 0; j < (this.length - 1); j++) {
				tmp[tmp.length - j - 1] = this.tuple[this.length - j - 2];
			}
			tmp[0] = 1;
			this.tuple = tmp;
		} else {
			this.tuple[this.length - i - 1] += 1;
		}
	}

	public void minusone() {
		if ((this.length == 1) && (this.tuple[0] == 1)) {
			this.tuple[0] = 0;
		} else {
			int i = 0;
			while ((this.tuple[this.length - i - 1] == 0) && (i < (this.length - 1))) {
				this.tuple[this.length - i - 1] = Natural.MAX - 1;
				i++;
			}
			this.tuple[this.length - i - 1] -= 1;
			if ((i == (this.length - 1)) && (this.tuple[0] == 0)) {
				final int[] tmp = new int[this.length - 1];
				for (int j = 0; j < tmp.length; j++) {
					tmp[j] = this.tuple[j + 1];
				}
				this.tuple = tmp;
				this.length -= 1;
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
		for (int i = 0; i < this.length; i++) {
			PRESENT = PRESENT + SPACE + this.tuple[i];
		}
		System.out.println(PRESENT);
		return (PRESENT);
	}

}
