package customaspects.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import customaspects.AbstractCustomAspect;
import customaspects.DistributionAspect;

@Aspect
public class ConcreteDistributionAspect extends AbstractCustomAspect implements DistributionAspect {

	private FileWriter w;
	private BufferedWriter bw;

	public ConcreteDistributionAspect() {
		register();
		setGenericName("DistributionAspect");
		this.setThreadToOutputMap(new ConcurrentHashMap<Thread, Map<String, Integer>>());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void print(final Thread thread) throws IOException {
		final String testcase = getTests().get(thread);
		final String path = getPath() + testcase.replace(Pattern.quote("."), "/") + "/" + "stats.xml";
		new File(getPath() + testcase).mkdirs();
		w = new FileWriter(path);
		bw = new BufferedWriter(w);
		bw.write("<" + testcase + ">");
		bw.flush();
		final Map<String, Integer> stats = (Map<String, Integer>) getThreadToOutputMap().get(Thread.currentThread());
		if (stats.isEmpty()) {
			org.apache.log4j.Logger.getLogger("Statistics empty").info("list empty");
		} else {
			for (final String str : stats.keySet()) {
				final Integer times = stats.get(str);
				if (times != 0) {
					LogManager.getLogger(DistributionAspect.class).info(str + " " + stringOf(times) + " times");
					bw.write("<" + str + ">" + stringOf(stats.get(str)) + "</" + str + ">\r");
					bw.flush();
				}
			}
		}
		bw.write("</" + testcase + ">");
		bw.flush();
		bw.close();
		w.close();
	}

	@SuppressWarnings("unchecked")
	@Before("execution(* definitions..*.*(..)) && !execution(* definitions.cache..*.*(..))")
	public void getStats(final JoinPoint jp) {
		final String key = jp.toShortString().split(Pattern.quote("@"))[0].split(Pattern.quote("("))[1]
				.replace(Pattern.quote("."), Pattern.quote("/"));
		final Map<String, Integer> stats = ((Map<Thread, Map<String, Integer>>) getThreadToOutputMap())
				.getOrDefault(Thread.currentThread(), new ConcurrentHashMap<String, Integer>());
		Integer ans = stats.get(key);
		if (ans == null) {
			ans = new Integer(1);
		} else {
			ans += 1;
		}
		stats.put(key, ans);
		((Map<Thread, Map<String, Integer>>) getThreadToOutputMap()).put(Thread.currentThread(), stats);
	}

}
