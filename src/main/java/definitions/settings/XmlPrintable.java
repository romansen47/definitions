package definitions.settings;

public interface XmlPrintable {

	default String toXml() {
		return toString();
	}

}