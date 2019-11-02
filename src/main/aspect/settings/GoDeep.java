package settings;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import java.util.List;

import settings.annotations.deepSearch;

import java.util.ArrayList;

@Aspect
public class GoDeep {

	static int depth = 0;

	static List<String> LIST = new ArrayList<>();

	static int maxDepth = 0;

	@Around("@annotation(anno)")
	public Object lookup(ProceedingJoinPoint pjp, deepSearch anno) throws Throwable {
		if (maxDepth == 0) {
			maxDepth = anno.depth();
		}
		return pjp.proceed();
	}

	@Around("execution(* donothing.definitions..*(..))")
	public Object lookup(ProceedingJoinPoint pjp) throws Throwable {
		if (maxDepth == 0 || depth == maxDepth) {
			maxDepth = 0;
			return pjp.proceed();
		}
		depth++;
		String str = "<" + pjp.toShortString() + ">\r";
		str += "<depth>" + depth + "<depth/>\r";
		Object o = pjp.proceed();
		depth--;
		if (o != null && o.toString() != null) {
			str += "<answer>" + o.toString() + "<answer/>";
		}
		str += "<" + pjp.toShortString() + "/>";
		LIST.add(str);
		return o;
	}
	
	public static void print() {
		
	}
}
