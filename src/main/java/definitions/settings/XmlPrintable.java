package definitions.settings;

public interface XmlPrintable {

	/**
	 * Defining own xml-strings
	 *
	 * @return the toString as an xml tag.
	 */
	default String toXml() {
		return toString();
	}

}