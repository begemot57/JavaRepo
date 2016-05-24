package test.collection;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SetSplitTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int maxPurgeChunkSize = 4;
		Set<Long> mr_ids = new TreeSet<>();
		mr_ids.add(0L);
		mr_ids.add(1L);
		mr_ids.add(2L);
		mr_ids.add(3L);
		mr_ids.add(4L);
		mr_ids.add(5L);
		mr_ids.add(6L);
		mr_ids.add(7L);
		mr_ids.add(8L);
		mr_ids.add(9L);
		
		StringBuffer sb;
		boolean stop = false;
		Iterator<Long> it = mr_ids.iterator();
		while (true)
		{
			sb = new StringBuffer();
			for (int j = 0; j < maxPurgeChunkSize; j++)
			{
				if(!it.hasNext())
				{
					stop = true;
					break;
				} 
				sb.append(it.next());
				//make sure there is no "and" at the end
				if(j < maxPurgeChunkSize-1 && it.hasNext())
					sb.append(" and ");
			}
			System.out.println(sb.toString());
			if(stop)
				break;
		}

	}

}
