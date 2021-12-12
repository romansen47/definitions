package definitions.structures.dynamicsystems;

import java.util.List;
import java.util.Random;

import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class GameOfLife implements MultiDimensionalDynamicSystem {

	protected int size;
	protected PrimeField binaries;

	protected GameOfLife() {
		size = 0;
	}

	public PrimeField getBinaries() {
		return binaries;
	}

	public void setBinaries(PrimeField binaries) {
		this.binaries = binaries;
	}

	public EuclideanSpace getGrid() {
		return grid;
	}

	public void setGrid(EuclideanSpace grid) {
		this.grid = grid;
	}

	public List<Vector> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Vector> coordinates) {
		this.coordinates = coordinates;
	}

	protected EuclideanSpace grid;
	protected List<Vector> coordinates;

	public GameOfLife(int size) {
		this.size = size;
		this.setBinaries(GroupGenerator.getInstance().getBinaries());
		grid = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(this.getBinaries(), size * size);
		coordinates = grid.genericBaseToList();
	}

	/**
	 * @return the grid
	 */
	@Override
	public EuclideanSpace getPhaseSpace() {
		return grid;
	}

	@Override
	public VectorSpaceSelfMapping getDefiningMapping() {
		return new VectorSpaceSelfMapping() {

			@Override
			public Element get(Element vec) {
				final FiniteVector ans = (FiniteVector) grid.nullVec();
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						final boolean survives = this.willBeAlive(vec, i, j);
						if (survives) {
							ans.getCoordinates().put(coordinates.get((i * size) + j),
									GameOfLife.this.getBinaries().getOne());
						}
					}
				}
				return ans;
			}

			private boolean willBeAlive(Element vec, int i, int j) {
				final int count = GameOfLife.this.countNeighbours(vec, i, j);
				boolean ans = false;
				if (((FiniteVector) vec).getCoordinates().get(coordinates.get((i * size) + j)) == GameOfLife.this
						.getBinaries().getZero()) {
					if (count == 3) {
						ans = true;
					}
				} else if ((count == 2) || (count == 3)) {
					ans = true;
				}
				return ans;
			}

			@Override
			public VectorSpace getSource() {
				return grid;
			}

		};
	}

	protected int countNeighbours(Element vec, int i, int j) {
		int count = ((FiniteVector) vec).getCoordinates().get(coordinates.get((i * size) + j))
				.equals(grid.getField().getNeutralElement()) ? 0 : -1;
		for (int u = -1; u < 2; u++) {
			for (int v = -1; v < 2; v++) {
				count += this.isAlive(vec, i + u, j + v);
				if (count > 3) {
					return 4;
				}
			}
		}
		return count;
	}

	private int isAlive(Element vec, int i, int j) {
		final Element ans = ((FiniteVector) vec).getCoordinates()
				.get(coordinates.get((((i + size) % size) * size) + ((j + size) % size)));
		int toInt;
		if (ans == this.getBinaries().getNeutralElement()) {
			toInt = 0;
		} else {
			toInt = 1;
		}
		return toInt;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	public String printState(FiniteVector gol) {
		String ans = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (gol.getCoordinates().get(coordinates.get((i * size) + j)) == this.getBinaries()
						.getNeutralElement()) {
					ans += " dead ";
				} else {
					ans += " alive ";
				}
			}
			ans += "\r";
		}
		return ans;
	}

	public FiniteVector createRandomInitialCondition() {
		final FiniteVector initialCondition = (FiniteVector) this.getPhaseSpace().nullVec();
		final Random random = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (random.nextBoolean()) {
					initialCondition.getCoordinates().put(coordinates.get((i * size) + j), this.getBinaries().getOne());
					// = (FiniteVector) grid.addition(initialCondition,
					// grid.genericBaseToList().get(i * size + j));
				} else {
					initialCondition.getCoordinates().put(coordinates.get((i * size) + j),
							(Scalar) this.getBinaries().getZero());
				}
			}
		}
		return initialCondition;
	}

	@Override
	public Group getTimeSpace() {
		return GroupGenerator.getInstance().getIntegers();
	}

}
