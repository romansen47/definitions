/**
 * 
 */
package settings;

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
		if (aspect==null) {
			aspect=new TestAspect();
		}
		return aspect;
	}
	
	@AfterReturning(pointcut="execution(* cache.CachingAspect.getCoordinateSpace(..))",returning ="space")
	public void getCoordinateSpace(Object space) {
		logger.info("loaded "+space+" from local cache");
	}
	
	@Before("@annotation(anno) && execution(public !final* definitions..*(..))")
	public void inform(Trace anno,JoinPoint jp) {
		logger.info("running "+jp.toShortString());
//		System.out.println("running "+wrapAnnotation.toString());
	}
	
	@After("@annotation(anno) && execution(public !final* definitions..*(..))")
	public void message(Trace anno,JoinPoint jp) {
		logger.info("done with "+jp.toShortString());
//		System.out.println("done with "+wrapAnnotation.toString());
	}
	
//	@Around("@annotation(wrapAnnotation) && execution(* *.*(..))")
//	public Object processSystemRequest(final ProceedingJoinPoint pjp, Trace wrapAnnotation) {
////		if (wrapAnnotation.toString().equals("Measurable")) {
//			Object ans = null;
//
//			logger.info("Executing method " + pjp.toString());
//			Clock clock = Clock.systemDefaultZone();
//			long miliSeconds = clock.millis();
//			try {
//				ans = pjp.proceed();
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			logger.info("Execution duration: "+(clock.millis()-miliSeconds));
//			System.out.println("Execution duration: " + (clock.millis() - miliSeconds));
//			return ans;
////		}
////		return pjp;
//	}

}
