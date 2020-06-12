/**
 * 
 */
package definitions.aspectjtest;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.dynamicsystems.GameOfLife;
import definitions.structures.dynamicsystems.GameOfLifeConstructedBinaries;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import processing.template.Gui;
import solver.StdDraw;

/**
 * @author ro
 *
 */
public class GameOfLifeTest extends Gui {

	private static ApplicationContextAware springConfiguration;

	final private int size = 20;
	private DynamicSystem gameOfLife;
	private DynamicSystem gameOfLifeConstructedBinaries;
	private FiniteVector initialCondition;
	private EuclideanSpace grid;
	private EuclideanSpace gridOverConstructedBinaries;
	private FiniteVector initialConditionForConstructedBinaries;
	private FiniteVector gol;
	private FiniteVector golocb;
	private int lifetime = 200;

	@BeforeClass
	public static void prepare() {
		setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
	}

	private StdDraw stddraw;
	private final int canvasSize = 500;

	public int getCanvasSize() {
		return canvasSize;
	}
	
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
			if (((FiniteVector) state).getCoordinates().get(tmpgol.getCoordinates().get((size - j - 1) * size + i)) == tmpgol.getBinaries()
					.getNeutralElement()) {
				StdDraw.setPenColor(StdDraw.WHITE);
			} else {
				StdDraw.setPenColor(StdDraw.BLACK);
			}
//			StdDraw.filledSquare(x, y, squareSize);
			StdDraw.circle(x, y, squareSize);
		}
	}
}
	
//	@Test
	public void gameOfLifeTest() {
		setGameOfLife(new GameOfLife(size));
		setGrid((EuclideanSpace) getGameOfLife().getPhaseSpace());
		setInitialCondition(((GameOfLife) getGameOfLife()).createRandomInitialCondition());
		setGol(getInitialCondition());// (FiniteVector) grid.addition(grid.nullVec(), initialCondition);
		draw(getGol());
		for (int a = 0; a < getLifetime(); a++) {
			setGol((FiniteVector) getGameOfLife().getDefiningMapping().get(getGol()));
			draw(getGol());
		}
	}

//	@Test
	public void gameOfLifeTestConstructedBinaries() {
		setGameOfLifeConstructedBinaries(new GameOfLifeConstructedBinaries(size));
		setGridOverConstructedBinaries((EuclideanSpace) getGameOfLifeConstructedBinaries().getPhaseSpace());
		setInitialConditionForConstructedBinaries(
				((GameOfLife) getGameOfLifeConstructedBinaries()).createRandomInitialCondition());
		setGolocb((FiniteVector) getGridOverConstructedBinaries().addition(getGridOverConstructedBinaries().nullVec(),
				getInitialConditionForConstructedBinaries()));
		draw(getGolocb());
		for (int a = 0; a < getLifetime(); a++) {
			setGolocb((FiniteVector) getGameOfLifeConstructedBinaries().getDefiningMapping().get(getGolocb()));
			draw(getGolocb());
		}
	}

	Gui template;

	GameOfLife tmpgol = new GameOfLife(size);

	private FiniteVector tmp = tmpgol.createRandomInitialCondition();

	@Override
	public void setup() {
		frameRate(10);
	}

	static GameOfLifeTest newGameOfLife;

	public static void main(String[] args) {
		setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
		newGameOfLife = new GameOfLifeTest();
		((Gui) newGameOfLife).run("definitions.aspectjtest.GameOfLifeTest");
	}

	@Override
	public void draw() {
		tmp = (FiniteVector) tmpgol.getDefiningMapping().get(tmp);
		this.clear();
		this.background(255);
		final int squareSize = (height / size) / 2;
		StdDraw.clear();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int x = squareSize * (1 + 2 * i);
				int y = squareSize * (1 + 2 * j);
				if (tmp.getCoordinates().get(tmpgol.getCoordinates()
						.get((size - j - 1) * size + i)) == tmpgol.getBinaries().getNeutralElement()) {
					fill(255);
				} else {
					fill(0);
				}
				rect(x, y, squareSize,squareSize);
			}
		}
	}

	/**
	 * @return the initialCondition
	 */
	public FiniteVector getInitialCondition() {
		return initialCondition;
	}

	/**
	 * @param initialCondition the initialCondition to set
	 */
	public void setInitialCondition(FiniteVector initialCondition) {
		this.initialCondition = initialCondition;
	}

	/**
	 * @return the grid
	 */
	public EuclideanSpace getGrid() {
		return grid;
	}

	/**
	 * @param grid the grid to set
	 */
	public void setGrid(EuclideanSpace grid) {
		this.grid = grid;
	}

	/**
	 * @return the gridOverConstructedBinaries
	 */
	public EuclideanSpace getGridOverConstructedBinaries() {
		return gridOverConstructedBinaries;
	}

	/**
	 * @param gridOverConstructedBinaries the gridOverConstructedBinaries to set
	 */
	public void setGridOverConstructedBinaries(EuclideanSpace gridOverConstructedBinaries) {
		this.gridOverConstructedBinaries = gridOverConstructedBinaries;
	}

	/**
	 * @return the gameOfLife
	 */
	public DynamicSystem getGameOfLife() {
		return gameOfLife;
	}

	/**
	 * @param gameOfLife the gameOfLife to set
	 */
	public void setGameOfLife(DynamicSystem gameOfLife) {
		this.gameOfLife = gameOfLife;
	}

	/**
	 * @return the gameOfLifeConstructedBinaries
	 */
	public DynamicSystem getGameOfLifeConstructedBinaries() {
		return gameOfLifeConstructedBinaries;
	}

	/**
	 * @param gameOfLifeConstructedBinaries the gameOfLifeConstructedBinaries to set
	 */
	public void setGameOfLifeConstructedBinaries(DynamicSystem gameOfLifeConstructedBinaries) {
		this.gameOfLifeConstructedBinaries = gameOfLifeConstructedBinaries;
	}

	/**
	 * @return the initialConditionForConstructedBinaries
	 */
	public FiniteVector getInitialConditionForConstructedBinaries() {
		return initialConditionForConstructedBinaries;
	}

	/**
	 * @param initialConditionForConstructedBinaries the
	 *                                               initialConditionForConstructedBinaries
	 *                                               to set
	 */
	public void setInitialConditionForConstructedBinaries(FiniteVector initialConditionForConstructedBinaries) {
		this.initialConditionForConstructedBinaries = initialConditionForConstructedBinaries;
	}

	/**
	 * @return the gol
	 */
	public FiniteVector getGol() {
		return gol;
	}

	/**
	 * @param gol the gol to set
	 */
	public void setGol(FiniteVector gol) {
		this.gol = gol;
	}

	/**
	 * @return the golocb
	 */
	public FiniteVector getGolocb() {
		return golocb;
	}

	/**
	 * @param golocb the golocb to set
	 */
	public void setGolocb(FiniteVector golocb) {
		this.golocb = golocb;
	}

	/**
	 * @return the lifetime
	 */
	public int getLifetime() {
		return lifetime;
	}

	/**
	 * @param lifetime the lifetime to set
	 */
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	/**
	 * @return the springConfiguration
	 */
	public static ApplicationContextAware getSpringConfiguration() {
		return springConfiguration;
	}

	/**
	 * @param springConfiguration the springConfiguration to set
	 */
	public static void setSpringConfiguration(ApplicationContextAware springConfiguration) {
		GameOfLifeTest.springConfiguration = springConfiguration;
	}

}
