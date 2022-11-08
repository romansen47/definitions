package definitions;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.support.AbstractApplicationContext;

import definitions.structures.euclidean.Generator;

@EnableLoadTimeWeaving(aspectjWeaving = AspectJWeaving.ENABLED)
@ComponentScan(basePackages = "definitions")
public class SpringConfiguration implements ApplicationContextAware {

	private static Logger logger = LogManager.getLogger(SpringConfiguration.class);
	private static ApplicationContextAware instance;
	private ApplicationContext applicationContext;

	public SpringConfiguration() {
		this.setApplicationContext(new AnnotationConfigApplicationContext());
		this.updateLoggers();
		this.getBeans();
		this.setInstances();
	}

	private void setInstances() {
		Generator.setInstance(this.applicationContext.getBean(Generator.class));
	}

	private void getBeans() {
		SpringConfiguration.logger.debug("applicationContext {} scanning in definitions..*",
				this.applicationContext.toString().split(",")[0]);
		((AnnotationConfigApplicationContext) this.applicationContext).scan("definitions..*");
		SpringConfiguration.logger.debug("applicationContext {} refreshing",
				this.applicationContext.toString().split(",")[0]);
		((AbstractApplicationContext) this.applicationContext).refresh();
		logger.debug("Beans we are aware of:");
		for (final String beanName : applicationContext.getBeanNamesForType(Object.class)) {
			logger.debug("bean " + beanName);
		}
		logger.debug("applicationContext {} getting bean generator", this.applicationContext.toString().split(",")[0]);
	}

	private void updateLoggers() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		final LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(Level.INFO);
		ctx.updateLoggers();
	}

	public static ApplicationContextAware getSpringConfiguration() {
		if (instance == null) {
			instance = new SpringConfiguration();
		}
		return instance;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
