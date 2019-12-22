package definitions;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author ro
 *
 */
@Aspect
//@Component
public class TestAspect {

	public final static Logger logger = LoggerFactory.getLogger(TestAspect.class);

	private final boolean testStarted = false;

	// @After("execution(* definitions.structures..*.*(..)) && !execution(*
	// aspects..*.*(..))")
	public void message(final JoinPoint jp) {
		logger.info("done with " + jp.toShortString());
	}

	private void syncAfter(final JoinPoint jp) throws Throwable {
		final Thread thread = Thread.currentThread();
		synchronized (thread) {

			logger.info("writing data to disk");

			DeepSearch.print(thread);
			DeepSearch.map.get(thread).clear();
			DeepSearch.tests.remove(thread);

			PointcutSearch.print(thread);
			PointcutSearch.map.get(thread).clear();
			PointcutSearch.tests.remove(thread);

			DistributionCollector.print(thread);
			DistributionCollector.map.get(thread).clear();
			DistributionCollector.tests.remove(thread);
		}
	}

	@After("@annotation(org.junit.Test) && execution(* definitions.xmltest..*(..))")
	public void syncAfterTest(final JoinPoint jp) throws Throwable {
		this.syncAfter(jp);
		DeepSearch.active = false;
	}

	private void syncBefore(final JoinPoint jp) throws Throwable {
		final Thread thread = Thread.currentThread();
		synchronized (thread) {
			final String testCaseName = jp.toShortString().split(Pattern.quote("@"))[0].split(Pattern.quote("("))[1]
					.replace(Pattern.quote("."), Pattern.quote("/"));

			DeepSearch.map.putIfAbsent(thread, new ArrayList<>());
			DeepSearch.tests.put(thread, testCaseName);

			PointcutSearch.map.putIfAbsent(thread, new ArrayList<>());
			PointcutSearch.tests.put(thread, testCaseName);

			DistributionCollector.tests.put(thread, testCaseName);
			DistributionCollector.map.putIfAbsent(thread, new ConcurrentHashMap<>());

			logger.info("note testcasename: " + testCaseName);
		}
	}

	@Before("@annotation(org.junit.Test) && execution(* definitions.xmltest..*.*(..))")
	public void syncBeforeTest(final JoinPoint jp) throws Throwable {
		DeepSearch.active = true;
		logger.info("DeepLogging activated in " + jp.toShortString().split(Pattern.quote("("))[1]);
		this.syncBefore(jp);
	}

}
