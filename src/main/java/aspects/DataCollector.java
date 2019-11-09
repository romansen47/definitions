package aspects;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface DataCollector {
	
	static final Map<String,Map<Thread, List<String>>> DATA=new ConcurrentHashMap<>();
	static final Map<String,Map<Thread, String>> TESTS=new ConcurrentHashMap<>();
	
}
