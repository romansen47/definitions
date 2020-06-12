package definitions.aspectjtest;

import java.util.Random;

import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import processing.template.Gui;

/**
 * @author ro
 *
 */
public class NativeGameOfLifeTest extends Gui {

	private static ApplicationContextAware springConfiguration = SpringConfiguration.getSpringConfiguration();

	@Override
	public void setup() {
		frameRate(100);
	}

	final private static int size = 500;
	private boolean[][] initialCondition;
	private int lifetime = 10000;

	Gui template;

	private boolean[][] tmp = createRandomInitialCondition();

	public boolean[][] createRandomInitialCondition() {
		boolean[][] initialCondition = new boolean[size][size];
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (random.nextBoolean()) {
					initialCondition[i][j] = true;
				}
			}
		}
		return initialCondition;
	}

	static NativeGameOfLifeTest newGameOfLife;

	public static void main(String[] args) {
		setSpringConfiguration(springConfiguration);
		newGameOfLife = new NativeGameOfLifeTest();
		((Gui) newGameOfLife).run("definitions.aspectjtest.NativeGameOfLifeTest");
	}

	public boolean[][] doStep(boolean[][] vec) {
		boolean[][] ans = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				ans[i][j] = willBeAlive(vec, i, j);
			}
		}
		return ans;
	}

	private boolean willBeAlive(boolean[][] vec, int i, int j) {
		int count = countNeighbours(vec, i, j);
		boolean ans = false;
		if (vec[i][j] == false) {
			if (count == 3) {
				ans = true;
			}
		} else if (count == 2 || count == 3) {
			ans = true;
		}
		return ans;
	}

	protected int countNeighbours(boolean[][] vec, int i, int j) {
		int count = 0;
		if (vec[i][j]) {
			count = -1;
		}
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

	private int isAlive(boolean[][] vec, int i, int j) {
		boolean ans = vec[(i + size) % size][(j + size) % size];
		int toInt;
		if (ans == false) {
			toInt = 0;
		} else {
			toInt = 1;
		}
		return toInt;
	}

	boolean first = true;
	private int generation=0;

	@Override
	public void draw() {
		if (first) {
			first = false;
		} else {
			tmp = doStep(tmp);
		}
		this.clear();
		this.background(255);
		final int squareSize = (height / size) / 2;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int x = squareSize * (1 + 2 * i);
				int y = squareSize * (1 + 2 * j);
				if (tmp[j][i]) {
					fill(255);
				} else {
					fill(0);
				}
				rect(x, y, 2 * squareSize, 2 * squareSize);
			}
		}
		stroke(1);
		text("generation: "+ ++generation,squareSize * (3 + 2 * size),squareSize);
		if (generation==lifetime) {
			exit();
		}
	}

	/**
	 * @return the initialCondition
	 */
	public boolean[][] getInitialCondition() {
		return initialCondition;
	}

	/**
	 * @param initialCondition the initialCondition to set
	 */
	public void setInitialCondition(boolean[][] initialCondition) {
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
		return springConfiguration;
	}

	/**
	 * @param springConfiguration the springConfiguration to set
	 */
	public static void setSpringConfiguration(ApplicationContextAware springConfiguration) {
		NativeGameOfLifeTest.springConfiguration = springConfiguration;
	}

}
