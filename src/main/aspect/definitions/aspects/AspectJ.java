package definitions.aspects;



import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Retention(RUNTIME)

@Target({

		FIELD, METHOD, ANNOTATION_TYPE, java.lang.annotation.ElementType.TYPE

})

@Configuration
@Configurable(autowire=Autowire.BY_NAME)
@ComponentScan(basePackages="definitions")
@EnableSpringConfigured
@EnableLoadTimeWeaving(aspectjWeaving=AspectJWeaving.ENABLED)
@EnableAspectJAutoProxy(proxyTargetClass=false,exposeProxy=false)
@Constraint(validatedBy = {})
public @interface AspectJ {

}
