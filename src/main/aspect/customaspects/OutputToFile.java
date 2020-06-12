package customaspects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import customaspects.impl.ConcreteTestAspect;

public interface OutputToFile extends CustomAspect {

	final TestAspect testAspect = ConcreteTestAspect.getInstance();

	void print(String path) throws IOException;

	default void register() {
		testAspect.getRelevantAspects().add(this);
	}

	default void print(final Thread thread) throws IOException {
		final String testcase = getTests().get(thread);
		String[] structure = testcase.split(Pattern.quote("."));
		new File(getPath() + structure[0]).mkdirs(); 
		String newPath= getPath() + structure[0] + "/" + structure[1] + ".profiling.xml";
		setFileWriter(new FileWriter(newPath));
		setBufferedWriter(new BufferedWriter(getFileWriter()));
		getBufferedWriter().write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r");
		getBufferedWriter().flush();
		getBufferedWriter().write("<Test>\r");
		getBufferedWriter().flush();
		@SuppressWarnings("unchecked")
		final List<String> list = (List<String>) getThreadToOutputMap().get(thread);
		if (list == null || list.isEmpty()) {
			log("list empty", false);
		} else {
			for (final String str : list) {
				getBufferedWriter().write(str);
				getBufferedWriter().flush();
			}
		}
		list.clear(); 
		getBufferedWriter().write("</Test>");
		getBufferedWriter().flush();
		getBufferedWriter().close();
		getFileWriter().close();
	}

	@Override
	default String xmlString(Object o) {
		return CustomAspect.super.xmlString(o);
	}

	String getPath(); 

	void setBufferedWriter(BufferedWriter bw);

	BufferedWriter getBufferedWriter();

	void setFileWriter(FileWriter w);

	FileWriter getFileWriter();

	public Map<Thread, String> getTests();

	public Map<?, ?> getThreadToOutputMap();
}
