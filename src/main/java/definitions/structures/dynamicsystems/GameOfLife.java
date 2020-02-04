package definitions.structures.dynamicsystems;

import java.util.List;
import java.util.Random;

import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.SelfMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import solver.StdDraw;

@SuppressWarnings("serial")
public class GameOfLife implements DynamicSystem {

	protected final int size;
	protected PrimeField binaries;
	
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

	public int getCanvasSize() {
		return canvasSize;
	}

	protected EuclideanSpace grid;
	protected List<Vector> coordinates;

	public GameOfLife(int size) {
		this.size = size;
		this.setBinaries(Generator.getInstance().getGroupGenerator().getBinaries());
		this.grid = (EuclideanSpace) SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(getBinaries(),
				size * size);
		coordinates = grid.genericBaseToList();
	}

	/**
	 * @return the grid
	 */
	@Override
	public VectorSpace getPhaseSpace() {
		return grid;
	}

	@Override
	public SelfMapping getDefiningMapping() {
		return new SelfMapping() {

			@Override
			public Element get(Element vec) {
				FiniteVector ans = (FiniteVector) grid.nullVec();
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						boolean survives = willBeAlive(vec, i, j);
						if (survives) {
							ans.getCoordinates().put(coordinates.get(i * size + j), getBinaries().getOne());
						}
					}
				}
				return ans;
			}

			private boolean willBeAlive(Element vec, int i, int j) {
				int count = countNeighbours(vec, i, j);
				boolean ans = false;
				if (((FiniteVector) vec).getCoordinates().get(coordinates.get(i * size + j)) == getBinaries().getZero()) {
					if (count == 3) {
						ans = true;
					}
				} else if (count == 2 || count == 3) {
					ans = true;
				}
				return ans;
			}

			@Override
			public Monoid getSource() {
				return grid;
			}

		};
	}

	protected int countNeighbours(Element vec, int i, int j) {
		int count = (int) -((FiniteVector) vec).getCoordinates().get(coordinates.get(i * size + j)).getRepresentant();
		for (int u = -1; u < 2; u++) {
			for (int v = -1; v < 2; v++) {
				count += isAlive(vec, i + u, j + v);
				if (count > 3) {
					return 4;
				}
			}
		}
		return count;
	}

	private int isAlive(Element vec, int i, int j) {
		Element ans = ((FiniteVector) vec).getCoordinates()
				.get(coordinates.get(((i + size) % size) * size + ((j + size) % size)));
		int toInt;
		if (ans == getBinaries().getNeutralElement()) {
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
				if (gol.getCoordinates().get(coordinates.get(i * size + j)) == getBinaries().getNeutralElement()) {
					ans += " dead ";
				} else {
					ans += " alive ";
				}
			}
			ans += "\r";
		}
		return ans;
	}

	private StdDraw stddraw;
	private final int canvasSize = 500;

	public void draw(Element state) {
		if (stddraw == null) {
			stddraw = new StdDraw();
			stddraw.setCanvasSize(canvasSize, canvasSize);
			StdDraw.setXscale(0, canvasSize);
			StdDraw.setYscale(0, canvasSize);
		}
		final int squareSize = (canvasSize / size) / 2;
		StdDraw.clear();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int x = squareSize * (1 + 2 * i);
				int y = squareSize * (1 + 2 * j);
				if (((FiniteVector) state).getCoordinates().get(coordinates.get((size - j - 1) * size + i)) == getBinaries()
						.getNeutralElement()) {
					StdDraw.setPenColor(StdDraw.WHITE);
				} else {
					StdDraw.setPenColor(StdDraw.BLACK);
				}
//				StdDraw.filledSquare(x, y, squareSize);
				StdDraw.circle(x, y, squareSize);
			}
		}
	}

	public FiniteVector createRandomInitialCondition() {
		FiniteVector initialCondition = (FiniteVector) ((EuclideanSpace) getPhaseSpace()).nullVec();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (random.nextBoolean()) {
					initialCondition.getCoordinates().put(coordinates.get(i * size + j), getBinaries().getOne());
					// = (FiniteVector) grid.addition(initialCondition,
					// grid.genericBaseToList().get(i * size + j));
				}
			}
		}
		return initialCondition;
	}

}
