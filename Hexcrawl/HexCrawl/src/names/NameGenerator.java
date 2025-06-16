package names;

public abstract class NameGenerator {

	public abstract String getName(int... val);

	protected static String getElementFromArray(String[] array,int val) {
		int index = val%array.length;
		if(index<0) index+=array.length;
		return array[index];
	}
}
