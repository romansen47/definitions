package definitions.structures.dynamicsystems;

import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * Implementation of Conway's Game Of Life using the constructed version of
 * binaries
 * 
 * @author ro
 *
 */
public class GameOfLifeConstructedBinaries extends GameOfLife {

	/*
	 * relacing binaries by the constructed version
	 */
	public GameOfLifeConstructedBinaries(int size) {
		this.size = size;
		binaries = Generator.getInstance().getGroupGenerator().getConstructedBinaries();
		grid = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(binaries, size * size);
		coordinates = grid.genericBaseToList();
	}

}
