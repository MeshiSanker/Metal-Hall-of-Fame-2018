package Ex2;

import java.util.Comparator;

public class BandsComparatorByName implements Comparator<Band>{
	public int compare(Band b1, Band b2) {
		 if (b1.getName().compareTo(b2.getName())>0)
				return 1;
			else
				return 0;
	}
}
