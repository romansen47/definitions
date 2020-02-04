package definitions.structures.dynamicsystems;

import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class GameOfLifeConstructedBinaries extends GameOfLife {

	public GameOfLifeConstructedBinaries(int size) {
		super(size);
		this.binaries = Generator.getInstance().getGroupGenerator().getConstructedBinaries();
		this.grid = (EuclideanSpace) SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(this.binaries,
				size * size);
		this.coordinates = grid.genericBaseToList();
	}

}
