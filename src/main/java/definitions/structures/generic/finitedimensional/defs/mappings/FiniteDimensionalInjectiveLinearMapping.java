package definitions.structures.generic.finitedimensional.defs.mappings;

public class FiniteDimensionalInjectiveLinearMapping extends FiniteDimensionalLinearMapping
		implements IFiniteDimensionalInjectiveLinearMapping {

	protected FiniteDimensionalInjectiveLinearMapping(IFiniteDimensionalLinearMapping mapping) throws Throwable {
		super(mapping.getSource(), mapping.getTarget(), mapping.getLinearity());
	}

}
