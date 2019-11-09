package aspects;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DistributionCollector {

	static final Map<String, Integer> STATS = new ConcurrentHashMap<>();

	@Before(value = "execution(* definitions..*.*(..))")
	public void getStats(JoinPoint jp) {
		String key = jp.toShortString().split(Pattern.quote("@"))[0];
		Integer ans = STATS.get(key);
		if (ans == null) {
			ans = new Integer(0);
		} else {
			ans += 1;
		}
		STATS.putIfAbsent(key, ans);
	}

}
