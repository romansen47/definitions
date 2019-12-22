/**
 * 
 */
package definitions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ro
 *
 */
public interface IWatcher extends Serializable {

	List<String> list = new ArrayList<>();

	static void add(final String str) {
		getList().add(str);
	}

	static List<String> getList() {
		return list;
	}

	static void show() {
		for (final String string : getList()) {
			System.out.println(string);
		}
	}

	void removeLast();

}
