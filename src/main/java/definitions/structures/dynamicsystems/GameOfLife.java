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

/**
 * Implementationof Conway's Game Of Life as a
 * {@link definitions.structures.dynamicsystems.MultiDimensionalDynamicSystem}
 * 
 * @author ro
 */
public class GameOfLife implements MultiDimensionalDynamicSystem {

	/**
	 * size of the game
	 */
	protected int size;

	/**
	 * the binaries
	 */
	protected PrimeField binaries;

	/**
	 * the grid
	 */
	protected EuclideanSpace grid;

	/**
	 * the coordinates
	 */
	protected List<Vector> coordinates;

	/**
	 * Constructor
	 * 
	 * @param size the size
	 * 
	 */
	public GameOfLife(int size) {
		this.size = size;
		this.setBinaries(GroupGenerator.getInstance().getBinaries());
		grid = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(this.getBinaries(), size * size);
		coordinates = grid.genericBaseToList();
	}

	/**
	 * Constructor
	 */
	protected GameOfLife() {
		this(0);
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

			/**
			 * method to determine if a given cell will survice/be reborn depending on given
			 * civilization
			 * 
			 * @param vec the given civilization
			 * @param i   vertical coordinate of the cell
			 * @param j   horizontal coordinate of the cell
			 * @return true, if cell will be alive
			 */
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

	/**
	 * method to count the amount of neigbours.
	 * 
	 * @param vec the vector of states of the cell
	 * @param i   vertical coordinate of the cell
	 * @param j   horizontal coordinate of the cell
	 * @return the amount of living neighbours
	 */
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

	/**
	 * method to determine if a given cell is alive in given civilization
	 * 
	 * @param vec the civilization
	 * @param i   vertical coordinate of the cell
	 * @param j   horizontal coordinate of the cell
	 * @return 1 if cell is alive
	 */
	private int isAlive(Element vec, int i, int j) {
		final Element ans = ((FiniteVector) vec).getCoordinates()
				.get(coordinates.get((((i + size) % size) * size) + ((j + size) % size)));
		return ans == this.getBinaries().getNeutralElement() ? 0 : 1;
	}

	/**
	 * @return the size of the games
	 */
	public int getSize() {
		return size;
	}

	public FiniteVector createRandomInitialCondition() {
		final FiniteVector initialCondition = (FiniteVector) this.getPhaseSpace().nullVec();
		final Random random = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (random.nextBoolean()) {
					initialCondition.getCoordinates().put(coordinates.get((i * size) + j), this.getBinaries().getOne());
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

	/**
	 * getter for binaries
	 * 
	 * @return the binaries
	 */
	public PrimeField getBinaries() {
		return binaries;
	}

	/**
	 * setter for binaries
	 * 
	 * @param binaries the binaries to set
	 */
	public void setBinaries(PrimeField binaries) {
		this.binaries = binaries;
	}

	/**
	 * getter for the grid
	 * 
	 * @return the grid
	 */
	public EuclideanSpace getGrid() {
		return grid;
	}

	/**
	 * Setter for the grid
	 * 
	 * @param grid the grid
	 */
	public void setGrid(EuclideanSpace grid) {
		this.grid = grid;
	}

	/**
	 * getter for the coordinates
	 * 
	 * @return the coordinates
	 */
	public List<Vector> getCoordinates() {
		return coordinates;
	}

	/**
	 * setter for the coordinates
	 * 
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(List<Vector> coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the grid
	 */
	@Override
	public EuclideanSpace getPhaseSpace() {
		return grid;
	}

}
