package settings;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import definitions.structures.abstr.fields.scalars.Scalar;

@Aspect
public class Operation {

	final static private String PATH = "target/";

	private static FileWriter w;

	private static BufferedWriter bw;

	private static int i = 0;

	boolean tracing = false;

	@Around("@annotation( wrapAnnotation ) && execution(* *(..))")
	public Object processSystemRequest(final ProceedingJoinPoint pjp, Trace wrapAnnotation) throws Throwable {
		if (wrapAnnotation.initial()) {
			tracing = true;
		}
		if (!tracing || !wrapAnnotation.trace()) {
			return pjp.proceed();
		}
		boolean tmpTracing = tracing;
		tracing = wrapAnnotation.transit();
		if (w == null) {
			String name = PATH + pjp.getThis().getClass().getSimpleName();
			w = new FileWriter(PATH + "test-results.xml");
			bw = new BufferedWriter(w);
			bw.write("");
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
		bw.write("<" + ans + ">\r");
		bw.flush();
		if (pjp.getArgs().length > 0) {
			bw.write("<arguments>\r");
			bw.flush();
			for (Object u : pjp.getArgs()) {
				String p = u.toString();
				if (p.contains(" ")) {
					p = p.split(" ")[1];
				}
				if (p.contains("@")) {
					p = p.split("@")[0];
				}
				if (u.getClass().getSimpleName() != null && u.getClass().getSimpleName() != "") {
					bw.write("<" + u.getClass().getTypeName() + ">" + p + "</" + u.getClass().getTypeName() + ">\r");
				} else {
					bw.write(
							"<" + u.getClass().getSimpleName() + ">" + p + "</" + u.getClass().getSimpleName() + ">\r");
				}
				bw.flush();
			}
			bw.write("</arguments>\r");
			bw.flush();
		}
		Object o = pjp.proceed();
//		if (o != null) {
//			String p = o.getClass().getSimpleName();
//			if (p != null && p!="") {
//				bw.write("<" + p + ">" + o.toString() + "</" + p
//						+ ">\r");
//			} else {
//				bw.write("<" + o.getClass().getTypeName() + ">" + o + "</" + o.getClass().getTypeName() + ">\r");
//				
//			}
//		}
		if (o != null) {
			String p = o.toString();
			if (p.contains(" ")) {
				p = p.split(" ")[1];
			}
			if (p.contains("@")) {
				p = p.split("@")[0];
			}
			if (o.getClass().getSimpleName() != null && o.getClass().getSimpleName() != "") {
				bw.write("<" + o.getClass().getTypeName() + ">" + p + "</" + o.getClass().getTypeName() + ">\r");
			} else {
				bw.write("<" + o.getClass().getSimpleName() + ">" + p + "</" + o.getClass().getSimpleName() + ">\r");
			}
			bw.flush();
		}
		bw.write("</" + ans + ">\r");
		bw.flush();
		tracing = tmpTracing;
		return o;
	}

}
