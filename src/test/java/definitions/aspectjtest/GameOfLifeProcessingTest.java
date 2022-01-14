package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.structures.dynamicsystems.GameOfLife;
import definitions.structures.dynamicsystems.GameOfLifeConstructedBinaries;
import definitions.structures.euclidean.vectors.FiniteVector;
import processing.template.impl.Gui;

/**
 * @author ro
 *
 */
public class GameOfLifeProcessingTest extends Gui {

	private static ApplicationContextAware springConfiguration = SpringConfiguration.getSpringConfiguration();
 
	final private static int size = 15;
	private FiniteVector initialCondition;
	private int lifetime = 50;

	Gui template;

	GameOfLife tmpgol = new GameOfLifeConstructedBinaries(GameOfLifeProcessingTest.size);

	private FiniteVector tmp = tmpgol.createRandomInitialCondition();

	@Override
	public void setup() {
		frameRate(10);
	}

	static GameOfLifeProcessingTest newGameOfLife;

	public static void main(String[] args) {
		GameOfLifeProcessingTest.setSpringConfiguration(GameOfLifeProcessingTest.springConfiguration);
		GameOfLifeProcessingTest.newGameOfLife = new GameOfLifeProcessingTest();
		((Gui) GameOfLifeProcessingTest.newGameOfLife).run("definitions.aspectjtest.GameOfLifeProcessingTest");
	} 
	
	@Test
	public void testGameOfLifeOnProcessing() {
		GameOfLifeProcessingTest.main(args);
	}

	private int generation = 0;

	@Override
	public void draw() {
		tmp = (FiniteVector) tmpgol.getDefiningMapping().get(tmp);
		clear();
		this.background(255);
		stroke(0);
		final int squareSize = (height / GameOfLifeProcessingTest.size) / 2;
		for (int i = 0; i < GameOfLifeProcessingTest.size; i++) {
			for (int j = 0; j < GameOfLifeProcessingTest.size; j++) {
				final int x = squareSize * (1 + (2 * i));
				final int y = squareSize * (1 + (2 * j));
				if (!(tmp.getCoordinates().get(tmpgol.getCoordinates().get(((GameOfLifeProcessingTest.size - j - 1) * GameOfLifeProcessingTest.size) + i)) == tmpgol
						.getBinaries().getNeutralElement())) {
					// fill(255);
					//				} else {
					//					fill(0);
					//				}
					rect(x, y, 2 * squareSize, 2 * squareSize);
				}
			}
		}
		fill(0);
		text("generation: " + generation++, (2 * squareSize * GameOfLifeProcessingTest.size) + 50, GameOfLifeProcessingTest.size);
		if (generation == lifetime) {
			Assert.assertTrue(true);
			//			exit();
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
		return GameOfLifeProcessingTest.springConfiguration;
	}

	/**
	 * @param springConfiguration the springConfiguration to set
	 */
	public static void setSpringConfiguration(ApplicationContextAware springConfiguration) {
		GameOfLifeProcessingTest.springConfiguration = springConfiguration;
	}

}
