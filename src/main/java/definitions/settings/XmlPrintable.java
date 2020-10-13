package definitions.settings;

public interface XmlPrintable {

	/**
	 * Defining own xml-strings
	 * @return
	 */
	default String toXml() {
		return toString();
	}

}