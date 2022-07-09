package exceptions;

import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public class ExtendingFailedException extends Exception {
	private static final long serialVersionUID = 8045812065455777899L;

	public ExtendingFailedException(VectorSpace space, Vector fun) {
		super("Extention for " + space + " by " + fun + " failed for some reason");
	}
}
