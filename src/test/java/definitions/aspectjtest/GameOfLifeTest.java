/**
 *
 */
package definitions.aspectjtest;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.dynamicsystems.GameOfLife;
import definitions.structures.dynamicsystems.GameOfLifeConstructedBinaries;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import processing.template.impl.Gui;
import solver.StdDraw;

/**
 * @author ro
 *
 */
public class GameOfLifeTest extends Gui {

	private static ApplicationContextAware springConfiguration;

	final private int size = 10;
	private DynamicSystem gameOfLife;
	private DynamicSystem gameOfLifeConstructedBinaries;
	private FiniteVector initialCondition;
	private EuclideanSpace grid;
	private EuclideanSpace gridOverConstructedBinaries;
	private FiniteVector initialConditionForConstructedBinaries;
	private FiniteVector gol;
	private FiniteVector golocb;
	private int lifetime = 50;

	@BeforeClass
	public static void prepare() {
		GameOfLifeTest.setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
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
		EuclideanSpace tmpES=grid!=null?grid:gridOverConstructedBinaries;
		FieldElement zero=tmpES.getField().getNeutralElement();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final int x = squareSize * (1 + (2 * i));
				final int y = squareSize * (1 + (2 * j));
				FiniteVector a=(FiniteVector) ((FiniteVector) state).getCoordinates()
						.get(tmpES.genericBaseToList().get(((size - j - 1) * size) + i));
				if (a.equals(zero)) {
					StdDraw.setPenColor(StdDraw.WHITE);
				} else {
					StdDraw.setPenColor(StdDraw.BLACK);
				}
				StdDraw.filledSquare(x, y, squareSize);
			}
		}
	}

	@Test
	public void gameOfLifeTest() {
		setGameOfLife(new GameOfLife(size));
		setGrid((EuclideanSpace) getGameOfLife().getPhaseSpace());
		setInitialCondition(((GameOfLife) getGameOfLife()).createRandomInitialCondition());
		setGol((FiniteVector) grid.addition(grid.nullVec(), initialCondition));
		draw(getGol());
		for (int a = 0; a < getLifetime(); a++) {
			setGol((FiniteVector) getGameOfLife().getDefiningMapping().get(getGol()));
			draw(getGol());
		}
	}

	@Test
	public void gameOfLifeTestConstructedBinaries() {
		setGameOfLifeConstructedBinaries(new GameOfLifeConstructedBinaries(size));
		setGridOverConstructedBinaries((EuclideanSpace) getGameOfLifeConstructedBinaries().getPhaseSpace());
		setInitialConditionForConstructedBinaries(
				((GameOfLife) getGameOfLifeConstructedBinaries()).createRandomInitialCondition());
		setGolocb((FiniteVector) gridOverConstructedBinaries.addition(gridOverConstructedBinaries.nullVec(), initialConditionForConstructedBinaries));
		draw(getGolocb());
		for (int a = 0; a < getLifetime(); a++) {
			Element newGame = getGameOfLifeConstructedBinaries().getDefiningMapping().get(getGolocb());
			setGolocb((FiniteVector) newGame);
			draw(getGolocb());
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
		this.golocb = new FiniteVector() {

			String toString=null;

			@Override
			public Map<Vector, Scalar> getCoordinates() {
				return golocb.getCoordinates();
			}

			@Override
			public void setCoordinates(Map<Vector, Scalar> coordinates) {
				golocb.setCoordinates(coordinates);
			}

			@Override
			public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
				golocb.setCoordinates(coordinates, space);
			}

			@Override
			public String toString() {
				if (toString==null) {
					toString = "";
					for (Vector vec : getCoordinates().keySet()) {
						toString += vec.toString() + " | ";
					}
				}
				return toString;
			}

		};
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
		return GameOfLifeTest.springConfiguration;
	}

	/**
	 * @param springConfiguration the springConfiguration to set
	 */
	public static void setSpringConfiguration(ApplicationContextAware springConfiguration) {
		GameOfLifeTest.springConfiguration = springConfiguration;
	}

}
