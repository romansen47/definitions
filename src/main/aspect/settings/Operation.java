package settings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Component;

import definitions.structures.abstr.fields.scalars.Scalar;


@Aspect
public class Operation {

	final static private String PATH = "C:\\Users\\roman\\";

	private static FileWriter w;

	private static BufferedWriter bw;

	boolean tracing = false;

	static int actualDepth = 0;

	@Around("@annotation(anno) && execution(public !final * donothing.definitions..*(..))")
	public Object processSystemRequest(final ProceedingJoinPoint pjp, Trace anno) throws Throwable {
		if (anno.initial()) {
			System.out.println("OperationAspect enabled");
			tracing = true;
		}
		if (!tracing ) {
			return pjp.proceed();
		}
		boolean tmpTracing = tracing;
		tracing = anno.transit();
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
		writeArgs(pjp);
		actualDepth += 1;
		Object o = pjp.proceed();
		actualDepth -= 1;
		if (actualDepth < anno.depth()) {
			writeObject(o, ans);
		}
		if (o != null) {
			bw.write("</" + ans + ">\r");
			bw.flush();
		}
		if (actualDepth > 0) {
			tracing = tmpTracing;
		} else {
			tracing = false;
		}
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
				bw.write("<" + o.getClass().getTypeName() + ">" + p + "</" + o.getClass().getTypeName() + ">\r");
			} else {
				bw.write("<" + o.getClass().getSimpleName() + ">" + p + "</" + o.getClass().getSimpleName() + ">\r");
			}
			bw.flush();
		}
	}

	private void writeArgs(ProceedingJoinPoint pjp) throws IOException {
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
	}

}
