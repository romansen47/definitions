package subspaces.functionalspaces;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Constant;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public class TrigonometricSpaceWithLinearGrowthTest {

	static IFiniteDimensionalFunctionSpace space;
	static IFiniteDimensionalFunctionSpace newSpace;

	protected static final String PATH = "src/main/resources/test.csv";
	protected static final String PATH2 = "src/main/resources/test2.csv";
	
	protected static double[][] values;
	protected static double[][] values2;
	
	static Function fun;
	static Function fun2;
	static Function normalizedId;

	static Vector toFourier;
	static Vector toFourier2;
	static Vector toFourier3;
	
	static double a=-Math.PI;
	static double b=Math.PI;
	
	static int dim=0;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {
		space=SpaceGenerator.getInstance().getTrigonometricSpace(dim);
		normalizedId=new FunctionTuple(new double[] {1}) {
			@Override
			public double value(double input) {
				return input;//*3/(2*Math.pow(Math.PI,3));
			}
		};
		Function idToFourier=new FunctionTuple(normalizedId.getCoordinates(space));
		Function orthoProj=new FunctionTuple(new double[] {1}) {
			@Override
			public double value(double input) throws Throwable {
				return normalizedId.value(input)-idToFourier.value(input);
			}
			@Override
			public String toString() {
				return "orthonormal projection of identity: ";
			}
		};
		newSpace=getExtendedTrigonometricSpace(dim,orthoProj);
	}

//	@Test
	public void test() throws Throwable {

		values = readFile(PATH);
		double[] para=new double[] {1};
		
		fun = new FunctionTuple(para) {
			int length=(int) values[0][values[0].length-1];
			@Override
			public double value(double input) {
				double newInput=(length/(2*Math.PI))*input+length/2.;
				int k=0;
				int l=(int)(newInput-newInput%1);
				while (values[0][k]<l) {
					k++;
				}
				return values[1][k];
			}
		};
		
		toFourier = newSpace.getCoordinates(fun);

		fun.plotCompare(a, b, (Function)toFourier);
		
		String ans="";
		
		for (Entry<Vector, Double> x:toFourier.getCoordinates().entrySet()) {
			ans+=x.toString()+"\r";
		}
		
		System.out.println(ans);
		
		System.out.println(((Function)toFourier).value(3*Math.PI));
		
	}
	
//	@Test
	public void test2() throws Throwable {

		values2 = readFile(PATH2);
		double[] para2=new double[] {1};
		
		fun2 = new FunctionTuple(para2) {
			int length=(int) values2[0][values2[0].length-1];
			@Override
			public double value(double input) {
				double newInput=(length/(2*Math.PI))*input+length/2.;
				int k=0;
				int l=(int)(newInput-newInput%1);
				while (values2[0][k]<l) {
					k++;
				}
				return values2[1][k];
			}
		};
		
		toFourier2 = newSpace.getCoordinates(fun2);

		fun2.plotCompare(a, b, (Function)toFourier2);
		
		String ans="";
		
		for (Entry<Vector, Double> x:toFourier2.getCoordinates().entrySet()) {
			ans+=x.toString()+"\r";
		}
		
		System.out.println(ans);
		
		System.out.println(((Function)toFourier2).value(3*Math.PI));
		
	}
	
	@Test
	public void test3() throws Throwable {
		
		toFourier3 = newSpace.getCoordinates(normalizedId);

		Function test1=(Function) newSpace.normalize(toFourier3);
		
		normalizedId.plotCompare(a, b, (Function)toFourier3);
		
		String ans="";
		
		for (Entry<Vector, Double> x:toFourier3.getCoordinates().entrySet()) {
			ans+=x.toString()+"\r";
		}
		
		System.out.println(normalizedId.value(1));
		
		System.out.println(ans);
		System.out.println(((Function)toFourier3).value(1));
		
	}
	
	protected static double[][] readFile(String string) throws IOException {
		final List<double[]> values = new ArrayList<>();
		final BufferedReader br = new BufferedReader(new FileReader(string));
		String line = "";
		LocalDate firstDate = null;
		try {
			while ((line = br.readLine()) != null) {
				final String[] parts = line.split(";");
				String[] tmpdate = parts[0].split("-");
				final LocalDate date = LocalDate.of(Integer.parseInt(tmpdate[0]), Integer.parseInt(tmpdate[1]),
						Integer.parseInt(tmpdate[2]));
				if (firstDate == null) {
					firstDate = date;
				}
				final double[] tmp = new double[2];
				tmp[0] = firstDate.until(date, ChronoUnit.DAYS);
				tmp[1] = Double.parseDouble(parts[1]);
				values.add(tmp);
			}
		} finally {
			br.close();
		}
		final double[][] ans = new double[2][values.size()];
		for (int i = 0; i < values.size(); i++) {
			ans[0][i] = values.get(i)[0];
			ans[1][i] = values.get(i)[1];
		}
		return ans;
	}

	protected static IFiniteDimensionalFunctionSpace getExtendedTrigonometricSpace(int n,Function fun) throws Throwable {
		List<Vector> tmpBase=new ArrayList<>();
		EuclideanSpace space=SpaceGenerator.getInstance().
								getFiniteDimensionalVectorSpace(2*n+2);
		List<Vector> coordinates=space.genericBaseToList();
		tmpBase.add(new Constant(coordinates.get(0).
						getGenericCoordinates(),1./Math.sqrt(2*Math.PI)));
		for (int i=0;i<n;i++) {
			final int j=i+1;
			Vector sin=new FunctionTuple(coordinates.get(2*i+1).getCoordinates()) {
				@Override
				public double value(double input) {
					return Math.sin(j*input)/Math.sqrt(Math.PI);
				}
				@Override
				public String toString() {
					return "x -> "+1/Math.sqrt(Math.PI)+"*sin("+j+"*x)";
				}
			};
			tmpBase.add(sin);
		}
		for (int i=1;i<n+1;i++) {
			final int j=i;
			Vector cos=new FunctionTuple(coordinates.get(2*j).getCoordinates()) {
				@Override
				public double value(double input) {
					return Math.cos(j*input)/Math.sqrt(Math.PI);
				}
				@Override
				public String toString() {
					return "x -> "+1/Math.sqrt(Math.PI)+"*cos("+j+"*x)";
				}
			};
			tmpBase.add(cos);
		}
		double[] lastCoords=new double [2*n+2];
		lastCoords[2*n+1]=1;
		Function newFun=new FunctionTuple(lastCoords) {
			@Override
			public double value(double input) throws Throwable {
				return fun.value(input);
			}
		};
		
		tmpBase.add(fun);
		return SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(tmpBase,-Math.PI,Math.PI);
	}
}
