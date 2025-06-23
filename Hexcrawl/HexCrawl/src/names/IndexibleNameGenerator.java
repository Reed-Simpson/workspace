package names;

import data.Indexible;

public abstract class IndexibleNameGenerator extends NameGenerator{
	public abstract String getName(Indexible obj);

	protected static String getElementFromArray(String[] array,Indexible obj) {
		return array[obj.reduceTempId(array.length)];
	}
	

	@Deprecated
	public String getName(int... val) {
		return getName(new Indexible(val));
	};
}
