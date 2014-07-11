package test.math;

import java.util.HashSet;


public class PowerSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(createPowerSet(new Object[]{"1","2","3","4"}));

	}

	public static HashSet<HashSet> createPowerSet(Object[] array)
	{
	    HashSet<HashSet> powerSet=new HashSet();
	    boolean[] mask= new boolean[array.length];

	    for(int i=0;i<Math.pow(2, array.length);i++)
	    {
	        HashSet set=new HashSet();
	        for(int j=0;j<mask.length;j++)
	        {
	            if(mask[j])
	                set.add(array[j]);
	        }
	        powerSet.add(set);      

	        increaseMask(mask);
	    }

	    return powerSet;
	}

	public static void increaseMask(boolean[] mask)
	{
	    boolean carry=false;

	    if(mask[0])
	        {
	            mask[0]=false;
	            carry=true;
	        }
	    else
	        mask[0]=true;

	    for(int i=1;i<mask.length;i++)
	    {
	        if(mask[i]==true && carry==true)
	        mask[i]=false;
	        else if (mask[i]==false && carry==true)
	        {
	            mask[i]=true;
	            carry=false;
	        }
	        else 
	            break;

	    }

	}
}
