package definitions.structures.dynamicsystems;

import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class GameOfLifeConstructedBinaries extends GameOfLife {

	public GameOfLifeConstructedBinaries(int size) {
		this.size = size;
		binaries = Generator.getInstance().getGroupGenerator().getConstructedBinaries();
		grid = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(binaries, size * size);
		coordinates = grid.genericBaseToList();
	}

}
