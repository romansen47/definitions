import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author ro
 *
 */

@Aspect
public class TestAspect {

	public final static Logger logger = LogManager.getLogger(TestAspect.class);

	private final boolean testStarted = false;

	/**
	 * @return the testStarted
	 */
	public boolean isTestStarted() {
		return this.testStarted;
	}

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

			DistributionCollector.print(thread);
			DistributionCollector.map.get(thread).clear();
			DistributionCollector.tests.remove(thread);
		}
	}

	@After("@annotation(org.junit.Test) && execution(* definitions.xmltest..*(..))")
	public void syncAfterTest(final JoinPoint jp) throws Throwable {
		DeepSearch.active = false;
		this.syncAfter(jp);
	}

	private void syncBefore(final JoinPoint jp) throws Throwable {
		final Thread thread = Thread.currentThread();
		synchronized (thread) {
			final String testCaseName = jp.toShortString().split(Pattern.quote("@"))[0].split(Pattern.quote("("))[1]
					.replace(Pattern.quote("."), Pattern.quote("/"));

			DeepSearch.map.putIfAbsent(thread, new ArrayList<>());
			DeepSearch.tests.put(thread, testCaseName);

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
