package definitions;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.AbstractApplicationContext;

import definitions.cache.CachingAspect;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.groups.impl.Int;
import definitions.structures.euclidean.Generator;

@EnableSpringConfigured
@EnableLoadTimeWeaving(aspectjWeaving = AspectJWeaving.ENABLED)
@Configuration
public class SpringConfiguration implements ApplicationContextAware {

	private static SpringConfiguration springConfiguration;

	private ApplicationContext applicationContext = annotationConfigApplicationContext();

	@Bean(name = "generator")
	public Generator generator() {
		return new Generator();
	}

	public SpringConfiguration() {
		this.setApplicationContext(applicationContext);
		((AnnotationConfigApplicationContext) this.applicationContext).scan("definitions..*");
		((AbstractApplicationContext) this.applicationContext).refresh();
		Generator.setInstance((Generator) applicationContext.getBean("generator"));
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = new AnnotationConfigApplicationContext();
	}

	@Bean(name = "springConfiguration")
	public static SpringConfiguration getSpringConfiguration() {
		if (springConfiguration == null) {
			springConfiguration = new SpringConfiguration();
		}
		return springConfiguration;
	}

	@Bean(name = "annotationConfigApplicationContext")
	public ApplicationContext annotationConfigApplicationContext() {
		applicationContext = new AnnotationConfigApplicationContext();
		return applicationContext;
	}

	@Bean(name="aspects.CachingAspect")
	public CachingAspect cachingAspect(){
		return new CachingAspect();
	}
	
	@Bean(name = "realLine")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RealLine realLine() {
		return new RealLine();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Real real() {
		return new Real();
	}

	@Bean(name = "int")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Int integer() {
		return new Int();
	}

	@Bean(name = "logger")
	public Logger logger() {
		return Logger.getLogger("DEFINITIONS:");
	}

}
