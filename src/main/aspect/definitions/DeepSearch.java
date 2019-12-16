package definitions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import definitions.settings.XmlPrintable;

@Component
@Aspect
public class DeepSearch implements Unweavable {

	public final static Logger logger = LoggerFactory.getLogger(DeepSearch.class);

	public final static Map<Thread, List<String>> map = new ConcurrentHashMap<>();
	public final static Map<Thread, String> tests = new ConcurrentHashMap<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;

	public static Boolean active = true;
	static int maxDepth;
	static int depth;

	boolean loggingToConsole=false;
	boolean running = false;

	@Around("@annotation(definitions.Proceed) && !within(definitions.Unweavable)")
	public Object aroundLookup(ProceedingJoinPoint pjp) throws Throwable {
		return this.getLookUp(pjp);
	}

	private Object getLookUp(ProceedingJoinPoint pjp) throws Throwable {
		if (!running) {
			running = true;
		}
		if (loggingToConsole) {
			logger.info(pjp.getThis().getClass().getSimpleName() + ": " + pjp.toShortString());
		}
		this.createEntries(pjp);
		return this.createXmlEntry(pjp);
	}

	public String xmlString(Object o) {
		List<String> ans = new ArrayList<>();
		if (o != null) {
			if (o instanceof Integer || o instanceof String || o instanceof Boolean) {
				ans.add(getEntry(o, ans));
			} else {
				if (o instanceof XmlPrintable) {
					ans.add(((XmlPrintable) o).toXml());
				} else {
					if (o instanceof List<?>) {
						ans.add("<list>");
						for (Object x : (List<?>) o) {
							String str = getEntry(o, ans);
							if (str == "") {
								if (x instanceof XmlPrintable) {
									ans.add(((XmlPrintable) x).toXml());
								} else {
									ans.add("<unknown>" + x.getClass().toString().split("class ")[1] + "</unknown>\r");
								}
							} else {
								ans.add(str);
							}
						}
						ans.add("</list>");
					}
					if (o instanceof Map<?, ?>) {
						ans.add("<map>");
						for (Object x : ((Map<?, ?>) o).keySet()) {
							String strX = "";
							String strY = "";
							if (x instanceof XmlPrintable) {
								strX += ((XmlPrintable) x).toXml();
							} else {
								strX += "<unknown>" + x.getClass().toString().split("class ")[1] + "</unknown>\r";
							}
							Object y = ((Map<?, ?>) o).get(x);
							if (y instanceof XmlPrintable) {
								strY += ((XmlPrintable) y).toXml();
							} else {
								strY += "<unknown>" + x.getClass().toString().split("class ")[1] + "</unknown>\r";
							}
							String entry = "<key>\r" + strX + "\r</key>\r";
							entry += "<value>\r" + strY + "\r</value>\r";
							ans.add(entry);
						}
						ans.add("</map>");
					} else {
						ans.add("<unknown>" + o.getClass().toString().split("class ")[1] + "</unknown>\r");
					}
				}
			}
		}
		String realAns = "";
		for (String str : ans) {
			realAns += str;
		}

		return realAns;
	}

	private String getEntry(Object o, List<String> ans) {
		String str = "";
		if (o instanceof Integer) { // || o instanceof String || o instanceof Boolean) {
			str = "<integer>" + o.toString() + "</integer>\r";
		}
		if (o instanceof Boolean) {
			str = "<boolean>" + o.toString() + "</boolean>\r";
		}
		if (o instanceof String) {
			str = "<string>" + o.toString() + "</string>\r";
		}
		return str;
	}

	private Object createXmlEntry(ProceedingJoinPoint pjp) throws Throwable {
		List<String> list = map.getOrDefault(Thread.currentThread(), new ArrayList<>());

		String str = pjp.toShortString().split(Pattern.quote("("))[1];
		String ans = "<" + str + ">\r";
		Object[] args = pjp.getArgs();
		if (args.length > 0) {
			ans += "<arguments>\r";
			for (Object arg : args) {
				ans += xmlString(arg);
			}
			ans += "</arguments>\r";
		}
		ans += "<executions>\r";
		list.add(ans);
		String ans2 = "";
		Object o = pjp.proceed();
		ans2 += "</executions>\r";
		ans2 += "<return>";
		ans2 += xmlString(o);
		ans2 += "</return>";
		ans2 += "</" + str + ">\r";
		list.add(ans2);
		return o;
	}

	private void createEntries(ProceedingJoinPoint pjp) {
		List<String> list = map.getOrDefault(Thread.currentThread(), new ArrayList<>());
//		try {
		if (list == null) {
			list = new ArrayList<>();
			map.put(Thread.currentThread(), list);
		}
		String invocator = tests.get(Thread.currentThread());
		if (invocator == null) {
			invocator = pjp.getSignature().toShortString().split(Pattern.quote("@"))[0];
			if (invocator.startsWith("execution(")) {
				invocator = invocator.replace("execution(", Pattern.quote("(")).split(Pattern.quote("("))[1];
			} else {
				invocator = invocator.split(Pattern.quote("("))[1];
			}
			tests.put(Thread.currentThread(), invocator);
		}
//		} catch (Exception e) {
//			logger.error(e.getStackTrace()[0].toString());
//		}
	}

	public static void print(Thread thread) throws IOException {
		String testcase = tests.get(thread);
		String path = PATH + testcase.replace(Pattern.quote("."), "/") + "/" + "deep-search.xml";
		new File(PATH + testcase).mkdirs();
		w = new FileWriter(path);
		bw = new BufferedWriter(w);
		bw.write("<" + testcase + ">\r");
		bw.flush();
		List<String> list = map.get(thread);
		if (list == null) {
			org.apache.log4j.Logger.getLogger("DeepSearch").info("list empty");
		} else {
			for (String str : list) {
				bw.write(str);
				bw.flush();
			}
		}
		bw.write("</" + testcase + ">");
		bw.flush();
		bw.close();
		w.close();
	}
}
