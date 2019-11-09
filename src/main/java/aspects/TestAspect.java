/**
 * 
 */
package aspects;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ro
 *
 */
@Aspect
@Configuration
public class TestAspect {

	@Autowired
	public final static Logger logger = Logger.getLogger("Stats");

	@Autowired
	private static TestAspect aspect;

	@Bean
	public static TestAspect getInstance() {
		return aspect;
	}

	@Around("@annotation(test) && execution(* definitions.structures..*.*(..))")
	public void syncAround(ProceedingJoinPoint jp, Test test) throws Throwable {
		sync(jp, test);
	}

	private void sync(ProceedingJoinPoint jp, Test test) throws Throwable {
		final Thread thread = Thread.currentThread();
		synchronized (thread) {
			String testCaseName = jp.toShortString().split(Pattern.quote("@"))[0].split(Pattern.quote("("))[1];
			DeepSearch.map.putIfAbsent(thread,new ArrayList<>());
			PointcutSearch.map.putIfAbsent(thread,new ArrayList<>());
			DeepSearch.tests.put(thread, testCaseName);
			PointcutSearch.tests.put(thread, testCaseName);
			logger.info("note testcasename: " + testCaseName);
			jp.proceed();
			logger.info("writing data to disk");
			DeepSearch.print(thread);
			PointcutSearch.print(thread);
			DeepSearch.map.get(thread).clear();
			DeepSearch.tests.remove(thread);
			PointcutSearch.map.get(thread).clear();
			PointcutSearch.tests.remove(thread);
		}
	}

//	@After("execution(* definitions.structures..*.*(..)) && !execution(* aspects..*.*(..))")
//	public void message(JoinPoint jp) {
//		logger.info("done with "+jp.toShortString());
//	}

}
