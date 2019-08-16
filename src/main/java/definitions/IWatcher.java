/**
 * 
 */
package definitions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ro
 *
 */
public interface IWatcher {
	
	final List<String> list=new ArrayList<>();
	
	static void add(String str) {
		getList().add(str);
	};

	static List<String> getList() {
		return list;
	}

	void removeLast();
	
	static void show() {
		for (String string:getList()) {
			System.out.println(string);
		}
	}

}
