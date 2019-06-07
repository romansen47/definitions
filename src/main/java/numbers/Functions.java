package numbers;

public class Functions {
	public Functions() {
	}

	public static Binary add(Binary A, Binary B) {
		final Binary ans = A.copy();
		final Binary tmp = B.copy();
		while (tmp.length > 1 || (tmp.length == 1 && tmp.tuple[0] == true)) {
			ans.addone();
			tmp.subone();
		}
		return (ans);
	}

	public static Binary add2(Binary A, Binary B) {
		final int max = Math.max(A.length, B.length);
		final boolean[] TMP1 = new boolean[max + 1];
		final boolean[] TMP2 = new boolean[max + 1];
		final boolean[] ans = new boolean[max + 1];
		for (int i = 0; i < A.length; i++) {
			TMP1[i] = A.tuple[i];
		}
		for (int i = 0; i < B.length; i++) {
			TMP2[i] = B.tuple[i];
		}
		boolean tmp = false;
		for (int i = 0; i < max + 1; i++) {
			if (tmp == false) {
				if (TMP1[i] == TMP2[i] && TMP1[i] == true) {
					tmp = true;
					ans[i] = false;
				} else {
					ans[i] = (TMP1[i] || TMP2[i]);
				}
			} else {
				if (TMP1[i] == false && TMP2[i] == false) {
					ans[i] = true;
					tmp = false;
				} else {
					if ((TMP1[i] == false && TMP2[i] == true) || (TMP1[i] == true && TMP2[i] == false)) {
						ans[i] = false;
						tmp = true;
					} else {
						ans[i] = true;
					}
				}
			}
		}
		return (new Binary(ans));
	}

	public static Binary mult(Binary A, Binary B) {
		Binary ans = A.copy();
		final Binary tmp = B.copy();
		// tmp.subone();
		while (tmp.length > 1) {
			ans = add2(ans, A);
			tmp.subone();
		}
		return (ans);
	}

	public static Binary shiftright(Binary tmp) {
		final boolean[] ans = new boolean[tmp.length + 1];
		for (int i = 0; i < tmp.length; i++) {
			ans[i + 1] = tmp.tuple[i];
		}
		return (new Binary(ans));
	}

	public static Binary shiftleft(Binary tmp) {
		final boolean[] ans = new boolean[tmp.length - 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = tmp.tuple[i + 1];
		}
		return (new Binary(ans));
	}

	public static Binary mult2(Binary tmp1, Binary tmp2) {
		Binary A = tmp1.copy();
		Binary tmp;
		if (tmp2.tuple[0] == false) {
			tmp = new Binary(0);
		} else {
			tmp = A.copy();
		}
		for (int i = 1; i < tmp2.length; i++) {
			if (tmp2.tuple[i] == true) {
				tmp = add2(tmp, shiftright(A));
			}
			A = shiftright(A);
		}
		return (tmp);
	}

	public Binary fak(Binary m) {
		final Binary n = m.copy();
		if (n.length == 1) {
			return (new Binary(1));
		} else {
			Binary fak = n.copy();
			n.subone();
			while (n.length > 1) {
				fak = mult2(fak, n);
				n.subone();
			}
			return (fak);
		}
	}

	public Binary fak(int n) {
		final Binary index = new Binary(1);
		Binary FAK = new Binary(1);
		for (int i = 0; i < n; i++) {
			FAK = mult2(FAK, index);
			index.addone();
		}
		return (FAK);
	}

	public Binary fakimpl(Binary n) {
		if (n.length == 1) {
			return (new Binary(1));
		} else {
			Binary tmp = n.copy();
			final Binary tmp2 = tmp.copy();
			tmp.subone();
			tmp = fakimpl(tmp);
			return (mult2(tmp, tmp2));
		}
	}

	// function definition for naturals

	public static Natural add(Natural tmp1, Natural tmp2) {
		final int LENGTH = Math.max(tmp1.length, tmp2.length) + 1;
		final int min = Math.min(tmp1.length, tmp2.length);
		final int[] tuple = new int[LENGTH];
		{
			int rest = 0;
			if (tmp1.length == tmp2.length) {
				for (int i = 0; i < LENGTH - 1; i++) // LENGTH-1
				{
					final int TMP = tmp1.tuple[LENGTH - 2 - i] + tmp2.tuple[LENGTH - i - 2] + rest;
					tuple[LENGTH - i - 1] = TMP % Natural.MAX;
					rest = TMP / Natural.MAX;
				}
				tuple[0] = rest;
			} else {
				for (int i = 0; i < min; i++) // LENGTH-1
				{
					final int TMP = tmp1.tuple[tmp1.length - i - 1] + tmp2.tuple[tmp2.length - i - 1] + rest;
					tuple[LENGTH - i - 1] = TMP % Natural.MAX;
					rest = TMP / Natural.MAX;
				}
				if (tmp1.length < tmp2.length) {
					for (int i = min; i < LENGTH - 1; i++) // LENGTH-1
					{
						final int TMP = tmp2.tuple[LENGTH - i - 2] + rest;
						tuple[LENGTH - i - 1] = TMP % Natural.MAX;
						rest = TMP / Natural.MAX;
					}
				} else {
					for (int i = min; i < LENGTH - 1; i++) // LENGTH-1
					{
						final int TMP = tmp1.tuple[LENGTH - i - 2] + rest;
						tuple[LENGTH - i - 1] = TMP % Natural.MAX;
						rest = TMP / Natural.MAX;
					}
				}
				tuple[0] = rest;
			}
		}
		final Natural answer = new Natural(tuple);
		return (answer);
	}

	public static Natural mult(Natural tmp1, Natural tmp2) {

		Natural ans = tmp1.copy();
		final Natural tmp = tmp2.copy();
		while (tmp.tuple.length > 1 || (tmp.tuple.length == 1 && tmp.tuple[0] > 1)) {
			ans = add(ans, tmp1);
			tmp.minusone();
		}
		// System.out.println(tmp1.present()+" * "+tmp2.present()+" = "+ans.present());
		return (ans);
	}

	public static Natural mult(int n, Natural N) {
		if (n == 0 || (N.length == 0 && N.tuple[0] == 0)) {
			return (new Natural(0));
		}
		int i = n;
		Natural tmp = N.copy();
		while (i > 1) {
			tmp = add(tmp, N);
			i--;
		}
		// System.out.println(n+" * "+N.present()+" = "+tmp.present());
		return (tmp);
	}

	public static Natural mult2(Natural tmp1, Natural tmp2) {
		Natural A = tmp1.copy();
		Natural tmp = new Natural(0);
		for (int i = 0; i < tmp2.length; i++) {
			final Natural test = mult(tmp2.tuple[tmp2.length - 1 - i], A);
			tmp = add(tmp, test);
			A = shiftleft(A);
		}
		return (tmp);
	}

	static Natural faculty(Natural param) {
		Natural FAK = param.copy();
		while (param.length > 1 || (param.length == 1 && param.tuple[0] > 1)) {
			param.minusone();
			FAK = mult2(FAK, param);
		}
		return (FAK);
	}

	public static Natural shiftleft(Natural tmp) {
		final int[] ans = new int[tmp.length + 1];
		for (int i = 0; i < tmp.length; i++) {
			ans[i] = tmp.tuple[i];
		}
		return (new Natural(ans));
	}

	/*
	 * public static natural mult2(natural tmp1, natural tmp2) {
	 *
	 * }
	 */
}