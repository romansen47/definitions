package definitions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointcutSearch {

	final static Map<Thread, List<String>> map = new ConcurrentHashMap<>();
	final static Map<Thread, String> tests = new ConcurrentHashMap<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;
	static int actualDepth = 0;

	public static void print(final Thread thread) throws IOException {
		final String testcase = tests.get(thread);
		final String path = PATH + testcase.replace(Pattern.quote("."), "/") + "/" + "pointcut-search.xml";
		new File(PATH + testcase).mkdirs();
		w = new FileWriter(path);
		bw = new BufferedWriter(w);
		bw.write("<" + testcase + ">");
		bw.flush();
		final List<String> list = map.get(thread);
		if (list == null || !list.isEmpty()) {
			org.apache.log4j.Logger.getLogger("PointcutSearch").info("list empty");
		} else {
			for (final String str : list) {
				bw.write(str);
				bw.flush();
			}
		}
		bw.write("</" + testcase + ">");
		bw.flush();
		bw.close();
		w.close();
	}

	public Object log(final ProceedingJoinPoint pjp) throws Throwable {
		List<String> LIST = map.get(Thread.currentThread());
		if (LIST == null) {
			LIST = new ArrayList<>();
			map.put(Thread.currentThread(), LIST);
		}
		String ans = pjp.toShortString();
		int k = ans.length();
		ans = ans.substring(10, k).split("@")[0];
		k = ans.length();
		if (ans.toString().contains(".))")) {
			ans = ans.substring(0, k - 5);
		} else {
			ans = ans.substring(0, k - 3);
		}
		LIST.add("<" + ans + ">\r");
		this.writeArgs(pjp, LIST);
		actualDepth += 1;
		final Object o = pjp.proceed();
		actualDepth -= 1;
		this.writeObject(o, ans, LIST);
		LIST.add("</" + ans + ">\r");
		return o;
	}

	@Around("execution(!final !abstract !static definitions.structures.euclidean..* definitions.structures.abstr..*(..))"
			+ " && !execution(* definitions.structures.euclidean.vectorspaces.*.add(..))"
			+ " && !execution(* definitions.structures.euclidean.vectorspaces.*.stretch(..))"
			+ " && !execution(* definitions.structures.abstr.groups.Monoid.operation(..))")
	public Object run(final ProceedingJoinPoint pjp) throws Throwable {
		return this.log(pjp);
	}

	private void writeArgs(final ProceedingJoinPoint pjp, final List<String> LIST) throws IOException {
		if (pjp.getArgs().length > 0) {
			LIST.add("<arguments>\r");
			for (final Object u : pjp.getArgs()) {
				String p;
				if (u != null && u.toString() != null) {
					p = u.toString();
				} else {
					p = "null";
				}
				if (p.contains(" ")) {
					p = p.split(" ")[1];
				}
				if (p.contains("@")) {
					p = p.split("@")[0];
				}
				if (u.getClass().getSimpleName() != null && u.getClass().getSimpleName() != "") {
					LIST.add("<" + u.getClass().getTypeName() + ">" + p + "</" + u.getClass().getTypeName() + ">\r");
				} else {
					LIST.add(
							"<" + u.getClass().getSimpleName() + ">" + p + "</" + u.getClass().getSimpleName() + ">\r");
				}
			}
			LIST.add("</arguments>\r");
		}
	}

	private void writeObject(final Object o, final String ans, final List<String> LIST) throws IOException {
		if (o != null) {
			String p = o.toString();
			if (p.contains(" ")) {
				p = p.split(" ")[1];
			}
			if (p.contains("@")) {
				p = p.split("@")[0];
			}
			if (o.getClass().getSimpleName() != null && o.getClass().getSimpleName() != "") {
				LIST.add("<" + o.getClass().getTypeName() + ">" + p + "</" + o.getClass().getTypeName() + ">\r");
			} else {
				LIST.add("<" + o.getClass().getSimpleName() + ">" + p + "</" + o.getClass().getSimpleName() + ">\r");
			}
		}
	}

}
