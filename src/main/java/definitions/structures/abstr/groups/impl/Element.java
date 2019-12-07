package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.vectorspaces.RingElement;

public class Element implements RingElement {

		private static final long serialVersionUID = 1L;
		int representant;
		
		protected Element(int r) {
			representant = r;
		}

		@Override
		public String toXml() {
			return "<representant>" + representant + " </representant>";
		}

		public int getRepresentant() {
			return representant;
		}

	}