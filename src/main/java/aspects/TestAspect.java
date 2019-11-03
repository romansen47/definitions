/**
 * 
 */
package aspects;

import java.time.Clock;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
	
	@AfterReturning(value="execution(* aspects.CachingAspect.get*(..))",returning ="space")
	public void getCoordinateSpace(Object space) {
		logger.info("loaded "+space+" from local cache");
	}
	
	@Before("execution(* aspects.CachingAspect.get*(..))")
	public void inform(JoinPoint jp) {
		logger.info("running "+jp.toShortString());
	}
	
	@After("execution(* aspects.CachingAspect.get*(..))")
	public void message(JoinPoint jp) {
		logger.info("done with "+jp.toShortString());
	}

}
