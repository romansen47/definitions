package aspects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Component;

import definitions.structures.abstr.fields.scalars.Scalar;

@Aspect
public class Operation {

	final private static List<String> LIST = new ArrayList<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;
	static int actualDepth = 0;

//	@Around("execution(public * nothing.definitions.structures.abstr.vectorspaces..*(..))")
	@Around("execution(!final !static !abstract definitions..* definitions..*(..))")
	public Object processSystemRequest(final ProceedingJoinPoint pjp) throws Throwable {
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
		writeArgs(pjp);
		actualDepth += 1;
		Object o = pjp.proceed();
		actualDepth -= 1;
		writeObject(o, ans);
		LIST.add("</" + ans + ">\r");

		return o;
	}

	private void writeObject(Object o, String ans) throws IOException {
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

	private void writeArgs(ProceedingJoinPoint pjp) throws IOException {
		if (pjp.getArgs().length > 0) {
			LIST.add("<arguments>\r");
			for (Object u : pjp.getArgs()) {
				String p = u.toString();
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

	public static void print() throws IOException {
		if (w == null) {
			w = new FileWriter(PATH + "operation-test-results.xml");
			bw = new BufferedWriter(w);
			for (String str : LIST) {
				bw.write(str);
				bw.flush();
			}
			w.close();
			bw.close();
			LIST.clear();
		}
	}

}
