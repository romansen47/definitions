package definitions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;

@Aspect
public class DistributionCollector{

	public final static Map<Thread, Map<String, Integer>> map = new ConcurrentHashMap<>();
	public final static Map<Thread, String> tests = new ConcurrentHashMap<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;

	public static void print(final Thread thread) throws IOException {
		final String testcase = tests.get(thread);
		final String path = PATH + testcase.replace(Pattern.quote("."), "/") + "/" + "stats.xml";
		new File(PATH + testcase).mkdirs();
		w = new FileWriter(path);
		bw = new BufferedWriter(w);
		bw.write("<" + testcase + ">");
		bw.flush();
		final Map<String, Integer> STATS = map.get(Thread.currentThread());
		if (STATS.isEmpty()) {
			org.apache.log4j.Logger.getLogger("Statistics empty").info("list empty");
		} else {
			for (final String str : STATS.keySet()) {
				final Integer times = STATS.get(str);
				if (times != 0) {
					LoggerFactory.getLogger(DistributionCollector.class).info(str + " " + times.toString() + " times");
					bw.write("<" + str + ">" + STATS.get(str).toString() + "</" + str + ">\r");
					bw.flush();
				}
			}
		}
		bw.write("</" + testcase + ">");
		bw.flush();
		bw.close();
		w.close();
	} 
	@Before(value = "execution(* definitions.structures..*(..)) && !execution(* *.print(..)) && !execution(* *.toXml(..)) && !execution(* definitions.structures.euclidean.Generator.*(..)) && !within(definitions.structures.abstr.groups.impl.FiniteResidueClassRing)")
	public void getStats(final JoinPoint jp) {
		final String key = jp.getSignature().toShortString().split(Pattern.quote("@"))[0];// jp.toShortString().split(Pattern.quote("@"))[0];
		final Map<String, Integer> STATS = map.getOrDefault(Thread.currentThread(), new ConcurrentHashMap<>());
		Integer ans = STATS.get(key);
		if (ans == null) {
			ans = new Integer(1);
		} else {
			ans += 1;
		}
		STATS.put(key, ans);
	}
}
