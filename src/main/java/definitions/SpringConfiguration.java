package definitions;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.AbstractApplicationContext;

import definitions.structures.euclidean.Generator;

@Configurable
@EnableSpringConfigured
@EnableLoadTimeWeaving
public class SpringConfiguration implements ApplicationContextAware {

	private static SpringConfiguration springConfiguration;

	@Bean(name = "springConfiguration")
	public static SpringConfiguration getSpringConfiguration() {
		if (springConfiguration == null) {
			springConfiguration = new SpringConfiguration();
		}
		return springConfiguration;
	}

	private ApplicationContext applicationContext = annotationConfigApplicationContext();

	public SpringConfiguration() {
		this.setApplicationContext(applicationContext);
//		((AnnotationConfigApplicationContext) this.applicationContext).scan("settings.*");
		((AnnotationConfigApplicationContext) this.applicationContext).scan("definitions..*");
		((AbstractApplicationContext) this.applicationContext).refresh();
		Generator.setInstance((Generator) applicationContext.getBean("generator"));
	}

	@Bean(name = "annotationConfigApplicationContext")
	public ApplicationContext annotationConfigApplicationContext() {
		applicationContext = new AnnotationConfigApplicationContext();
		return applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = new AnnotationConfigApplicationContext();
	}

}
