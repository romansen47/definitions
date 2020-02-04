/**
 * 
 */
package definitions.aspectjtest;

import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.dynamicsystems.GameOfLife;
import definitions.structures.dynamicsystems.GameOfLifeConstructedBinaries;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class GameOfLifeTest extends AspectJTest {

	final int size = 20;
	private DynamicSystem gameOfLife;
	private DynamicSystem gameOfLifeConstructedBinaries;
	private FiniteVector initialCondition;
	private EuclideanSpace grid;
	private EuclideanSpace gridOverConstructedBinaries;
	private FiniteVector initialConditionForConstructedBinaries;
	private FiniteVector gol;
	private FiniteVector golocb;
	private int lifetime = 200;

	@Test
	public void gameOfLifeTest() {
		setGameOfLife(new GameOfLife(size));
		setGrid((EuclideanSpace) getGameOfLife().getPhaseSpace());
		setInitialCondition(((GameOfLife) getGameOfLife()).createRandomInitialCondition());
		setGol(getInitialCondition());//(FiniteVector) grid.addition(grid.nullVec(), initialCondition);
		((GameOfLife) getGameOfLife()).draw(getGol());
		for (int a = 0; a < getLifetime(); a++) {
			setGol((FiniteVector) getGameOfLife().getDefiningMapping().get(getGol()));
			((GameOfLife) getGameOfLife()).draw(getGol());
		}
	}
	
	@Test
	public void gameOfLifeTestConstructedBinaries() {
		setGameOfLifeConstructedBinaries(new GameOfLifeConstructedBinaries(size));
		setGridOverConstructedBinaries((EuclideanSpace) getGameOfLifeConstructedBinaries().getPhaseSpace());
		setInitialConditionForConstructedBinaries(((GameOfLife) getGameOfLifeConstructedBinaries()).createRandomInitialCondition());
		setGolocb((FiniteVector) getGridOverConstructedBinaries().addition(getGridOverConstructedBinaries().nullVec(), getInitialConditionForConstructedBinaries()));
		((GameOfLife) getGameOfLifeConstructedBinaries()).draw(getGolocb());
		for (int a = 0; a < getLifetime(); a++) {
			setGolocb((FiniteVector) getGameOfLifeConstructedBinaries().getDefiningMapping().get(getGolocb()));
			((GameOfLife) getGameOfLifeConstructedBinaries()).draw(getGolocb());
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
	 * @param initialConditionForConstructedBinaries the initialConditionForConstructedBinaries to set
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

}
