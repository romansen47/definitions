package settings;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpringBeanLogging {

	@Around("execution(public static java.lang.Object definitions.SpringConfiguration.getInstance(..))")
	public java.lang.Object postProcessAfterInitialization(ProceedingJoinPoint jp) throws Throwable {
		Logger.getLogger("MAIN LOGGER").info(String.format("Bean instantiated with name %s and class %s", jp.getArgs()[0].toString(),
				((Bean)jp.getArgs()[1]).getClass().getSimpleName()));
		return jp.proceed();
	}
}
