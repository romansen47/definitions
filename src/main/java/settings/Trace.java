package settings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Trace {
	
	boolean trace() default true;
	
	int depth();
	
	boolean initial();
	
	boolean transit() default true;
}
