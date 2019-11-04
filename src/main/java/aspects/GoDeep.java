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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import settings.Trace;

@Aspect
public class GoDeep {

	public final static Map<Thread, List<String>> map = new ConcurrentHashMap<>();
	public final static Map<Thread, String> tests = new ConcurrentHashMap<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;

	static Boolean active = null;
	static int maxDepth;
	static int depth;

	@Around("execution(!final !static * definitions..*.*(..))")
	public Object aroundLookup(ProceedingJoinPoint pjp) throws Throwable {
		if (active != null && active) {
			return getLookUp(pjp);
		} else {
			return pjp.proceed();
		}
	}

	@Before("@annotation(trace)")
	public void beforeLookup(JoinPoint jp, Trace trace) throws Throwable {
		if (active == null) {
			active = true;
		}
		else if (active) {
			active=false;
		}
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
		LIST.add("<" + pjp.toShortString() + ">\r");
		Object o = pjp.proceed();
		if (o != null) {
			LIST.add("<returnValue>" + o.toString().split(Pattern.quote("@"))[0] + "</returnValue>\r");
		}
		LIST.add("</" + pjp.toShortString() + ">\r");
		return o;
	}

	public static void print(Thread thread) throws IOException {
		String testcase = tests.get(thread);
		String path = PATH + testcase + "/" + "goDeep-test-results.xml";
		new File(PATH + testcase).mkdirs();
		w = new FileWriter(path);
		bw = new BufferedWriter(w);
		bw.write("<" + testcase + ">");
		bw.flush();
		for (String str : map.get(thread)) {
			bw.write(str);
			bw.flush();
		}
		bw.write("</" + testcase + ">");
		bw.flush();
		bw.close();
		w.close();
	}
}
