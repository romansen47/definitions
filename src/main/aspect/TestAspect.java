import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import definitions.SpringConfiguration;

/**
 * @author ro
 *
 */
@Aspect
public class TestAspect {

	public final static Logger logger = Logger.getLogger("TestAspect");

	@Before("@annotation(org.junit.Test) && execution(* definitions.xmltest..*.*(..))")
	public void syncBeforeTest(JoinPoint jp) throws Throwable {
		DeepSearch.active = true;
		logger.info("DeepLogging activated in " + jp.toShortString().split(Pattern.quote("("))[1]);
		syncBefore(jp);
	}

	private boolean testStarted = false;
	private SpringConfiguration springConfiguration;

	@Before("@annotation(org.junit.Test)")
	public void createContext() {
		if (!testStarted) {
			springConfiguration = SpringConfiguration.getSpringConfiguration();
			testStarted = true;
		}
	}

	@After("@annotation(org.junit.Test) && execution(* definitions.xmltest..*.*(..))")
	public void syncAfterTest(JoinPoint jp) throws Throwable {
		syncAfter(jp);
		DeepSearch.active = false;
	}

	private void syncBefore(JoinPoint jp) throws Throwable {
		final Thread thread = Thread.currentThread();
		synchronized (thread) {
			String testCaseName = jp.toShortString().split(Pattern.quote("@"))[0].split(Pattern.quote("("))[1]
					.replace(Pattern.quote("."), Pattern.quote("/"));

			DeepSearch.map.putIfAbsent(thread, new ArrayList<>());
			PointcutSearch.map.putIfAbsent(thread, new ArrayList<>());
			DistributionCollector.map.putIfAbsent(thread, new ConcurrentHashMap<>());

			DeepSearch.tests.put(thread, testCaseName);
			PointcutSearch.tests.put(thread, testCaseName);
			DistributionCollector.tests.put(thread, testCaseName);

			logger.info("note testcasename: " + testCaseName);
		}
	}

	private void syncAfter(JoinPoint jp) throws Throwable {
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

//	@After("execution(* definitions.structures..*.*(..)) && !execution(* aspects..*.*(..))")
//	public void message(JoinPoint jp) {
//		logger.info("done with "+jp.toShortString());
//	}

}
