package aspects;

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

@Aspect
public class DeepSearch {

	public final static Map<Thread, List<String>> map = new ConcurrentHashMap<>();
	public final static Map<Thread, String> tests = new ConcurrentHashMap<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;

	static Boolean active = null;
	static int maxDepth;
	static int depth;

	@Around("execution(!final !static * definitions..*.*(..)) && !execution(* definitions..*Test.*(..))")
	public Object aroundLookup(ProceedingJoinPoint pjp) throws Throwable {
		if (active != null && active) {
			return getLookUp(pjp);
		} else {
			return pjp.proceed();
		}
	}

	@Around("@annotation(trace)")
	public Object aroundAnnotated(ProceedingJoinPoint jp, org.junit.Test trace) throws Throwable {
		if (active == null) {
			active = true;
		} 
		Object o= jp.proceed();
		active=false;
		return o;
//		else if (active) {
//			active = false;
//		}
	}

	private Object getLookUp(ProceedingJoinPoint pjp) throws Throwable {
		List<String> LIST = map.get(Thread.currentThread());
		if (LIST == null) {
			LIST = new ArrayList<>();
			map.put(Thread.currentThread(), LIST);
		}
		String invocator = tests.get(Thread.currentThread());
		if (invocator == null) {
			invocator = pjp.getSignature().toShortString().split(Pattern.quote("@"))[0];
			invocator = invocator.replace("execution(", Pattern.quote("(")).split(Pattern.quote("("))[1];
			tests.put(Thread.currentThread(), invocator);
		}
		String str=pjp.toShortString().split(Pattern.quote("("))[1];
		LIST.add("<" + str + ">\r");
		Object o = pjp.proceed();
		if (o != null) {
			LIST.add("<returnValue>" + o.toString().split(Pattern.quote("@"))[0] + "</returnValue>\r");
		}
		LIST.add("</" + str + ">\r");
		return o;
	}

	public static void print(Thread thread) throws IOException {
		String testcase = tests.get(thread);
		String path = PATH + testcase.replace(Pattern.quote("."),"/") + "/" + "deep-search.xml";
		new File(PATH + testcase).mkdirs();
		w = new FileWriter(path);
		bw = new BufferedWriter(w);
		bw.write("<" + testcase + ">");
		bw.flush();
		List<String> list = map.get(thread);
		if (list == null) {
			java.util.logging.Logger.getLogger("DeepSearch").info("list empty");
		} else {
			for (String str : list) {
				bw.write(str);
				bw.flush();
			}
		}
		bw.write("</" + testcase + ">");
		bw.flush();
		bw.close();
		w.close();
	}
}
