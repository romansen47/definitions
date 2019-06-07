package numbers;

public class Binary {

	// Natural attributes

	boolean[] tuple;
	int length;

	// Constructors

	public Binary(boolean[] TUPLE) {
		tuple = TUPLE;
		length = tuple.length;
		normalize();

	}

	public Binary(int n) {
		int m = n;
		tuple = new boolean[1];
		tuple[0] = false;
		while (m > 0) {
			addone();
			m -= 1;
		}
	}

	public void normalize() {
		if (length > 1 && tuple[length - 1] == false) {
			final boolean[] tmp = new boolean[length - 1];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = tuple[i];
			}
			tuple = tmp;
			length = length - 1;
			normalize();
		}
	}

	// Copy function

	private boolean[] copy(boolean[] tmp) {
		final boolean[] ans = new boolean[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			ans[i] = tmp[i];
		}
		return (ans);
	}

	public Binary copy() {
		return (new Binary(copy(tuple)));
	}

	// presentation functions

	public void present() {
		for (int i = 0; i < length; i++) {
			if (tuple[length - 1 - i] == true) {
				System.out.print(1);
			} else {
				System.out.print(0);
			}
		}
		System.out.println();
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < length; i++) {
			if (tuple[length - 1 - i] == true) {
				str += 1;
			}
			str += 0;
		}
		return str;
	}

	public void dezimal() {
		if (length == 1) {
			if (tuple[0] == true) {
				System.out.println(1);
			} else {
				System.out.println(0);
			}
		} else {
			int ans = 0;
			if (tuple[0] == true) {
				ans = 1;
			} else {
				ans = 0;
			}
			int basis = 1;
			for (int i = 1; i < length; i++) {
				if (tuple[i] == true) {
					ans += basis * 2;
				}
				basis = basis * 2;
			}
			System.out.println(ans);
		}
	}

	// private methods

	private boolean reverse(boolean tmp) {
		if (tmp == true) {
			return (false);
		}
		return (true);
	}

	// public methods

	public void addone() {
		final boolean[] tmp = new boolean[length + 1];
		for (int i = 0; i < length; i++) {
			tmp[i] = tuple[i];
		}
		int i = 0;
		while (i < length && tuple[i] == true) {
			tmp[i] = reverse(tuple[i++]);
		}
		tmp[i] = true;
		tuple = tmp;
		length = tmp.length;
		normalize();
	}

	public void subone() {
		final boolean[] tmp = copy(tuple);
		int i = 0;
		while (i < length && tuple[i] == false) {
			tmp[i] = reverse(tuple[i++]);
		}
		tmp[i] = false;
		tuple = tmp;
		normalize();
	}

	public Binary subonebin() {
		final boolean[] tmp = copy(tuple);
		int i = 0;
		while (i < length && tuple[i] == false) {
			tmp[i] = reverse(tuple[i++]);
		}
		tmp[i] = false;
		tuple = tmp;
		normalize();
		return (this);
	}
}