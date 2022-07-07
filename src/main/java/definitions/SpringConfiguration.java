package definitions;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
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

	private static ApplicationContextAware instance;

	private static Logger logger = LogManager.getLogger(SpringConfiguration.class);

	public static ApplicationContextAware getSpringConfiguration() {
		if (SpringConfiguration.instance == null) {
			SpringConfiguration.instance = new SpringConfiguration();
		}
		return SpringConfiguration.instance;
	}

	private ApplicationContext applicationContext;

	public SpringConfiguration() {
		updateLoggers();
		setApplicationContext(new AnnotationConfigApplicationContext());
		Configurator.setLevel(logger, Level.INFO);
		logger.info("applicationContext {} scanning in definitions..*", applicationContext);
		((AnnotationConfigApplicationContext) applicationContext).scan("definitions..*");
		logger.info("applicationContext {} refreshing", applicationContext);
		((AbstractApplicationContext) applicationContext).refresh();
		logger.info("applicationContext {} getting bean generator", applicationContext);
		Generator.setInstance((Generator) applicationContext.getBean("generator"));
	}

	public void updateLoggers() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		final LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(Level.INFO);
		ctx.updateLoggers();
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
