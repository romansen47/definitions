/**
 * 
 */
package aspects;

import java.io.IOException;
import java.time.Clock;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
			GoDeep.tests.put(thread, testCaseName);
			Operation.tests.put(thread, testCaseName);
			logger.info("note testcasename: " + testCaseName);
			jp.proceed();
			logger.info("writing data to disk");
			GoDeep.print(thread);
			Operation.print(thread);
			GoDeep.map.get(thread).clear();
			GoDeep.tests.remove(thread);
			Operation.map.get(thread).clear();
			Operation.tests.remove(thread);
		}
	}

//	@After("execution(* definitions.structures..*.*(..)) && !execution(* aspects..*.*(..))")
//	public void message(JoinPoint jp) {
//		logger.info("done with "+jp.toShortString());
//	}

}
