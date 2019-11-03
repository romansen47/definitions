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
public class GoDeep {

	public final static Map<Thread, List<String>> map = new ConcurrentHashMap<>();
	public final static Map<Thread, String> tests = new ConcurrentHashMap<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;


	@Around("execution(definitions.structures..* definitions.structures.euclidean..*(..)) "
			+ " && !execution(* definitions.structures.euclidean.Generator.*(..))"
			+" && !execution(* definitions.structures.euclidean.vectorspaces.*.add(..))"
			+" && !execution(* definitions.structures.euclidean.vectorspaces.*.stretch(..))")
	public Object lookup(ProceedingJoinPoint pjp) throws Throwable {
		List<String> LIST = map.get(Thread.currentThread());
		if (LIST == null) {
			LIST = new ArrayList<>();
			map.put(Thread.currentThread(), LIST);
		}
		String invocator = tests.get(Thread.currentThread());
		if (invocator == null) {
			invocator = pjp.getSignature().toShortString().split(Pattern.quote("@"))[0];
			invocator = invocator.split(Pattern.quote("("))[1];
			tests.put(Thread.currentThread(), invocator);
		}
		LIST.add("<" + pjp.toShortString() + ">\r");
		Object o = pjp.proceed();
		LIST.add("<answer>" + o.toString().split(Pattern.quote("@"))[0] + "</answer>\r");
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
