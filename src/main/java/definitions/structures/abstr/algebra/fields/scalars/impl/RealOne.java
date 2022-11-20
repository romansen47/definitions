/**
 *
 */
package definitions.structures.abstr.algebra.fields.scalars.impl;

import org.springframework.stereotype.Component;

/**
 * @author RoManski
 *
 */
@Component
public class RealOne extends Real {

	private static final long serialVersionUID = 1L;

	public RealOne() {
		super();
		this.setRepresentant(1d);
	}

}
