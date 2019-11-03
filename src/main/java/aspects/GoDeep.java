package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import java.util.List;
import java.util.regex.Pattern;

import settings.annotations.deepSearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Aspect
public class GoDeep {

	static List<String> LIST = new ArrayList<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;
	
	static String invocator=null;

	@Around("execution(definitions.structures.euclidean..* definitions..*(..)) && !execution(* definitions.structures.euclidean.Generator.*(..))")
	public Object lookup(ProceedingJoinPoint pjp) throws Throwable {
		if (invocator==null) {
			invocator=pjp.toShortString().split(Pattern.quote("@"))[0];
		}
		String str = "<" + pjp.toShortString() + ">\r";
		Object o = pjp.proceed();
		str += "<answer>" + o.toString() + "</answer>";
		str += "</" + pjp.toShortString() + ">";
		LIST.add(str);
		return o;
	}
	
	public static void print() throws IOException {
			w = new FileWriter(PATH + "goDeep-test-results.xml");
			bw = new BufferedWriter(w);
			bw.write("<"+invocator+">");
			bw.flush();
			for (String str:LIST) {
				bw.write(str);
				bw.flush();
			}
			bw.write("</"+invocator+">");
			bw.flush();
			bw.close();
			w.close();
			LIST.clear();
	}
}
