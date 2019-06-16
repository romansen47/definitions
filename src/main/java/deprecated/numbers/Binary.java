package deprecated.numbers;

public class Binary {

	// Natural attributes

	boolean[] tuple;
	int length;

	// Constructors

	public Binary(final boolean[] TUPLE) {
		this.tuple = TUPLE;
		this.length = this.tuple.length;
		this.normalize();

	}

	public Binary(final int n) {
		int m = n;
		this.tuple = new boolean[1];
		this.tuple[0] = false;
		while (m > 0) {
			this.addone();
			m -= 1;
		}
	}

	public void normalize() {
		if ((this.length > 1) && (this.tuple[this.length - 1] == false)) {
			final boolean[] tmp = new boolean[this.length - 1];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = this.tuple[i];
			}
			this.tuple = tmp;
			this.length = this.length - 1;
			this.normalize();
		}
	}

	// Copy function

	private boolean[] copy(final boolean[] tmp) {
		final boolean[] ans = new boolean[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			ans[i] = tmp[i];
		}
		return (ans);
	}

	public Binary copy() {
		return (new Binary(this.copy(this.tuple)));
	}

	// presentation functions

	public void present() {
		for (int i = 0; i < this.length; i++) {
			if (this.tuple[this.length - 1 - i] == true) {
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
		for (int i = 0; i < this.length; i++) {
			if (this.tuple[this.length - 1 - i] == true) {
				str += 1;
			}
			str += 0;
		}
		return str;
	}

	public void dezimal() {
		if (this.length == 1) {
			if (this.tuple[0] == true) {
				System.out.println(1);
			} else {
				System.out.println(0);
			}
		} else {
			int ans = 0;
			if (this.tuple[0] == true) {
				ans = 1;
			} else {
				ans = 0;
			}
			int basis = 1;
			for (int i = 1; i < this.length; i++) {
				if (this.tuple[i] == true) {
					ans += basis * 2;
				}
				basis = basis * 2;
			}
			System.out.println(ans);
		}
	}

	// private methods

	private boolean reverse(final boolean tmp) {
		if (tmp == true) {
			return (false);
		}
		return (true);
	}

	// public methods

	public void addone() {
		final boolean[] tmp = new boolean[this.length + 1];
		for (int i = 0; i < this.length; i++) {
			tmp[i] = this.tuple[i];
		}
		int i = 0;
		while ((i < this.length) && (this.tuple[i] == true)) {
			tmp[i] = this.reverse(this.tuple[i++]);
		}
		tmp[i] = true;
		this.tuple = tmp;
		this.length = tmp.length;
		this.normalize();
	}

	public void subone() {
		final boolean[] tmp = this.copy(this.tuple);
		int i = 0;
		while ((i < this.length) && (this.tuple[i] == false)) {
			tmp[i] = this.reverse(this.tuple[i++]);
		}
		tmp[i] = false;
		this.tuple = tmp;
		this.normalize();
	}

	public Binary subonebin() {
		final boolean[] tmp = this.copy(this.tuple);
		int i = 0;
		while ((i < this.length) && (this.tuple[i] == false)) {
			tmp[i] = this.reverse(this.tuple[i++]);
		}
		tmp[i] = false;
		this.tuple = tmp;
		this.normalize();
		return (this);
	}
}