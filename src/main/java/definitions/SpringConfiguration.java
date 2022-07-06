package definitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static ApplicationContextAware instance;

	private static Logger logger = LogManager.getLogger(SpringConfiguration.class);;

	public static ApplicationContextAware getSpringConfiguration() {
		if (SpringConfiguration.instance == null) {
			SpringConfiguration.instance = new SpringConfiguration();
		}
		return SpringConfiguration.instance;
	}

	private ApplicationContext applicationContext;

	public SpringConfiguration() {
		setApplicationContext(new AnnotationConfigApplicationContext());
		logger.info("applicationContext {} scanning in definitions..*", applicationContext);
		((AnnotationConfigApplicationContext) applicationContext).scan("definitions..*");
		logger.info("applicationContext {} refreshing", applicationContext);
		((AbstractApplicationContext) applicationContext).refresh();
		logger.info("applicationContext {} getting bean generator", applicationContext);
		Generator.setInstance((Generator) applicationContext.getBean("generator"));
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean(name = "definitions.cache.CachingAspect")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CachingAspect cachingAspect() {
		return new CachingAspect();
	}

	@Bean(name = "generator")
	public Generator generator() {
		cachingAspect();
		return new Generator();
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

}
