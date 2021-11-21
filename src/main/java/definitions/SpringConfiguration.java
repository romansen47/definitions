package definitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;

import definitions.cache.CachingAspect;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.Generator;

@EnableLoadTimeWeaving(aspectjWeaving = AspectJWeaving.ENABLED)
@ComponentScan(basePackages = "definitions")
public class SpringConfiguration implements ApplicationContextAware {

	private static ApplicationContextAware springConfiguration;

	private static Logger logger;

	@Bean(name = "springConfiguration")
	public static ApplicationContextAware getSpringConfiguration() {
		if (springConfiguration == null) {
			springConfiguration = new SpringConfiguration();
		}
		return springConfiguration;
	}

	private ApplicationContext applicationContext;

	public SpringConfiguration() {
		setApplicationContext(applicationContext);
		((AnnotationConfigApplicationContext) applicationContext).scan("definitions..*");
		((AbstractApplicationContext) applicationContext).refresh();
		Generator.setInstance((Generator) applicationContext.getBean("generator"));
		logger = logger();
	}

	@Bean(name = "annotationConfigApplicationContext")
	public ApplicationContext annotationConfigApplicationContext() {
		applicationContext = new AnnotationConfigApplicationContext();
		return applicationContext;
	}

	@Bean(name = "definitions.cache.CachingAspect")
	public CachingAspect cachingAspect() {
		return new CachingAspect();
	}

	@Bean(name = "generator")
	public Generator generator() {
		return new Generator();
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Bean(name = "logger")
	public Logger logger() {
		return LogManager.getLogger("spring configuration");
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Real real() {
		return new Real();
	}

	@Bean(name = "realLine")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RealLine realLine() {
		return new RealLine();
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = new AnnotationConfigApplicationContext();
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

}
