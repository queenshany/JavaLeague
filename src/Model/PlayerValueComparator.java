package Model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class PlayerValueComparator ~ Comparing players' value
 * 
 * @author ID: 205791056
 * 
 */

public class PlayerValueComparator implements Comparator<Player>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Player p1, Player p2) {
		// TODO Auto-generated method stub
		if (p1 != null && p2 != null) {
		int val = ((Long)p1.getValue()).compareTo(p2.getValue());
			if (val == 0)
				return p1.getId() - p2.getId();
		return val;
		}
//		if (p1 == null && p2 != null)
//			return 1;
//		
//		if (p2 == null && p1 != null)
//			return -1;
//		
		return 0;
	}

}
