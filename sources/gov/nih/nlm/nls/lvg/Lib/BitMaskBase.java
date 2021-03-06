package gov.nih.nlm.nls.lvg.Lib;
import java.util.*;
/*****************************************************************************
* This class is the base class for Bit Mask operation. 
* It's child class which inherits (extends) from this class need to:
* <ol>
* <li> declare bitStr_
* <li> define the correspond BIT
* </ol>
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/design.html#GENDER">
*      Design document </a>
*
* @version    V-2019
****************************************************************************/
public class BitMaskBase
{
    //public constructor
    /**
    * Create a new BitMaskBase object
    */
    public BitMaskBase()
    {
        value_ = 0;
    }
    /**
    * Create a new BitMaskBase object, using a long integer value
    *
    * @param   value  long value to find for bit index
    */
    public BitMaskBase(long value)
    {
        value_ = value;
    }
    // protected constructor
    protected BitMaskBase(long allBitValue, ArrayList<Vector<String>> bitStr)
    {
        value_ = 0;
        allBitValue_ = allBitValue;
        bitStr_ = bitStr;
    }
    protected BitMaskBase(long value, long allBitValue, 
        ArrayList<Vector<String>> bitStr)
    {
        value_ = value;
        allBitValue_ = allBitValue;
        bitStr_ = bitStr;
    }
    // static public methods
    /**
    * Get the long value of mask at a specified bit
    *
    * @param   bitNum  the bit number that is interested 
    *
    * @return  a long value of the mask at bit number
    */
    public static long GetBitValue(int bitNum)
    {
        return MASK[bitNum];
    }
    /**
    * Convert from a long value to a bit index
    *
    * @param   value  long value to find for bit index
    *
    * @return  the bit index of value, or -1 if no match
    */
    public static int GetBitIndex(long value)
    {
        int bit = -1;
        for(int i = 0; i < MAX_BIT; i++)
        {
            if(value == MASK[i])
            {
                bit = i;
                break;
            }
        }
        return bit;
    }
    /**
    * Check if a given container object contains a object with a given value.  
    * Contain means all bits are true in the tested object are also true
    * in the container object
    *
    * @param   container   a long value of the conatiner
    * @param   value   a long value of the testing object
    *
    * @return   true or false if this object contains or does not contain
    *           the object with the given value
    */
    public static boolean Contains(long container, long value)
    {
        boolean contain = ((container & value) == value); 
        return contain;
    }
    /**
    * Get a Vector of Long - includes all values from the combined value.  
    * For examples, a value of 129 will return a vector contains two elements 
    * (Long) with value of 1 and 128.
    *
    * @param   value  combined value
    * @param   maxBitUsed the max bit
    *
    * @return  a Vector of Long - includes all values from the combined value
    */
    protected static Vector<Long> ToValues(long value, int maxBitUsed)
    {
        // check if the input value is legal
        if((value <= 0) && (value > MASK[maxBitUsed]))
        {
            return null;
        }
        Vector<Long> outs = new Vector<Long>();
        // go through all bits
        for(int i = 0; i < maxBitUsed; i++)
        {
            // check if current bit match, update returnBitStr
            if((value & MASK[i]) != 0)
            {
                outs.addElement(new Long(MASK[i]));
            }
        }
        // Add head and tail
        return outs;
    }
    // object public methods
    /**
    * Set the long value of htis object
    *
    * @param   value  long value to be assigned
    */
    public void SetValue(long value)
    {
        if(value > 0)
        {
            value_ = value;
        }
    }
    /**
    * Get the bit value (true or false) at the specified index of this object
    *
    * @param   maskIndex  index of the bit to find it's value
    *
    * @return  value (true or false) of the specified bit
    */
    public boolean GetBitFlag(int maskIndex)
    {
        boolean bitValue = ((value_ & MASK[maskIndex]) != 0);
        return bitValue;
    }
    /**
    * Set the bit value of the specified bit for this object
    *
    * @param   maskIndex  index of the bit to be set
    * @param   flag  value of the bit to be set (true or false).
    */
    public void SetBitFlag(int maskIndex, boolean flag)
    {
        // use bit or |: bit at MASK[maskIndex] is 1 (true)
        if(flag == true)    
        {
            value_ = value_ | MASK[maskIndex];        
        }
        else    // reverse the mask, then use bit and &
        {
            value_ = value_ & (~MASK[maskIndex]);
        }
    }
    /**
    * Get the long value of this object
    *
    * @return  long value of this object
    */
    public long GetValue()
    {
        return value_;
    }
    /**
    * Get the String value of this object
    *
    * @return  string value of this object
    */
    public String GetName()
    {
        return ToName(value_, allBitValue_, bitStr_);
        // use bit or |: bit at MASK[maskIndex] is 1 (true)
    }
    /**
    * Check if current object contains a object with a given value.  
    * Contain means all bits are true in the tested object are also true
    * in the container (this) object
    *
    * @param   value   a long value of the testing object
    *
    * @return   true or false if this object contains or does not contain
    *           the object with the given value
    */
    public boolean Contains(long value)
    {
        if((value_ & value) == value)
        {
            return true; 
        }
        return false;
    }
    // protected methods
    /**
    * Convert a combined value string to a long value.
    *
    * @param   valueStr  names of bits for finding it's long value
    * @param   bitStr  a Vector of String - bitStr
    *
    * @return  a long value of the specified name
    *
    */
    protected static long ToValue(String valueStr, 
        ArrayList<Vector<String>> bitStr)
    {
        long value = 0;
        Vector<String> valueStrList = GetStringList(valueStr);
        BitMaskBase bm = new BitMaskBase();
        // go through all names
        for(int k = 0; k < valueStrList.size(); k++)
        {
            // go through all bits first
            for(int i =  0; i < MAX_BIT; i++)
            {
                // go through all names of each bit
                for(int j = 0; j < bitStr.get(i).size(); j++)
                {
                    String temp = valueStrList.elementAt(k);
                    if(temp.equals(bitStr.get(i).elementAt(j)) == true)
                    {
                        bm.SetBitFlag(i, true);
                    }
                }
            }
        }
        value = bm.GetValue();
        return value;
    }
    /**
    * Get the combined bit string (abbreviation) of a specified value.  
    *
    * @param   value  nubmer for finding it's combined name
    * @param   allBitValue the value of all bit are used
    * @param   bitStr  a Vector of String - bitStr
    *
    * @return  name in a String format of an bitMask object
    */
    protected static String ToName(long value, long allBitValue, 
        ArrayList<Vector<String>> bitStr)
    {
        // check if it is all bits
        if(value == allBitValue)
        {
            return ALL_STR;
        }
        // get the local copy of bitStr from child
        String returnBitStr = null;
        
        // go through all bits
        for(int i = 0; i < MAX_BIT; i++)
        {
            // check if current bit match, update returnBitStr
            if((bitStr.get(i).size() >= 1) && ((value & MASK[i]) != 0))
            {
                if(returnBitStr == null)
                {
                    returnBitStr = bitStr.get(i).elementAt(0) + SP;
                }
                else
                {
                    returnBitStr += bitStr.get(i).elementAt(0) + SP;
                }
            }
        }
        // remove the last character, separator
        if(returnBitStr != null)
        {
            returnBitStr = returnBitStr.substring(0, returnBitStr.length()-1);
        }
        else
        {
            returnBitStr = new String();
        }
        // Add head and tail
        return returnBitStr;
    }
    /**
    * Get the long value for one single name (no combine names of bits).
    *
    * @param   valueStr  name of a bit for finding it's long value
    * @param   bitStr  a Vector of String - bitStr
    *
    * @return  a long value of the specified name
    */
    protected static long Enumerate(String valueStr, 
        ArrayList<Vector<String>> bitStr)
    {
        long value = 0;
        // Enumerate values: go through all bits first
        for(int i = 0; i < bitStr.size(); i++)
        {
            // go through all names of each bit
            for(int j = 0; j < bitStr.get(i).size(); j++)
            {
                if(valueStr.equals(bitStr.get(i).elementAt(j)))
                {
                    value = MASK[i];
                    break;
                }
            }
        }
        return value;
    }
    /**
    * Get the name at index order of a specified bit (single).  
    *
    * @param   bitValue  bit nubmer for finding it's name
    * @param   index   the order index of the name in bitStr_[]
    * @param   bitStr  a Vector of String - bitStr
    *
    * @return  name at index order of the bit specified
    */
    protected static String GetBitName(int bitValue, int index, 
        ArrayList<Vector<String>> bitStr)
    {
        String returnBitStr = null;
        if(bitValue >= MAX_BIT)
        {
            return returnBitStr;
        }
        // check index
        if(index < bitStr.get(bitValue).size())
        {
            returnBitStr = bitStr.get(bitValue).elementAt(index);
        }
        return returnBitStr;
    }
    /**
    * Test driver for this class 
    *
    * @param args arguments
    */
    public static void main(String[] args)
    {
        System.out.println("----- static methods -------" );
        System.out.println(" -  Gender.ToName(7): " 
            + Gender.ToName(7));
        System.out.println(" -  Gender.ToName(15): " 
            + Gender.ToName(15));
        System.out.println(" -  " + Gender.ToName(6) + " contains " 
            + Gender.ToName(2) + ": " + Gender.Contains(6, 2));
        System.out.println(" -  " + Gender.ToName(6) + " contains " 
            + Gender.ToName(4) + ": " + Gender.Contains(6, 4));
        System.out.println(" -  " + Gender.ToName(6) + " contains " 
            + Gender.ToName(5) + ": " + Gender.Contains(6, 5));
        System.out.println(" -  Category.ToName(1030): " 
            + Category.ToName(1030));
        Vector<Long> values = Category.ToValues(1030);
        for(int i = 0; i < values.size(); i++)
        {
            System.out.println(" - Category.ToValues(1030): " 
                + values.elementAt(i)); 
        }
        System.out.println(" -  Category.ToName(2047): " 
            + Category.ToName(2047));
        System.out.println(" -  Inflection.ToName(2584): " 
            + Inflection.ToName(2584));
        System.out.println(" -  Inflection.ToName(16777215): " 
            + Inflection.ToName(16777215));
        for(int i = 0; i < MAX_BIT; i++)
        {
            System.out.println(i + ": " + MASK[i]);
        }
    }
    // private methods
    // Tokenlize combined String into a Vector<String> format
    private static Vector<String> GetStringList(String value)
    {
        Vector<String> stringList = new Vector<String>();
        StringTokenizer buf = new StringTokenizer(value, SP);
        while(buf.hasMoreTokens() == true)
        {
            stringList.addElement(buf.nextToken());
        }
        return stringList;
    }
    // data members
    /** the maximum number of bits used.  It is used in it's child classes */
    protected final static int MAX_BIT = 63;    // long 16 bytes = 64-1 bits
    private final static String SP = "+";        // separator
    private final static String ALL_STR = "all";
    public final static long[] MASK = new long[] {
        1L, 2L, 4L, 8L, 16L, 32L, 64L, 128L, 256L, 512L,    // 1-10 
        1024L, 2048L, 4096L, 8192L, 16384L,    // 11-15
        32768L, 65536L, 131072L, 262144L, 524288L,    // 16-20
        1048576L, 2097152L, 4194304L, 8388608L, 16777216L,    // 21-25
        33554432L, 67108864L, 134217728L, 268435456L, 536870912L,    // 26-30
        1073741824L, 2147483648L, 4294967296L, 8589934592L, 17179869184L,//35
        34359738368L, 68719476736L, 137438953472L, 274877906944L, 549755813888L, //40
        1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, //44
        17592186044416L, 35184372088832L, 70368744177664L, 140737488355328L,//48
        281474976710656L, 562949953421312L, 1125899906842624L, // 51
        2251799813685248L, 4503599627370496L, 9007199254740992L, // 54
        18014398509481984L, 36028797018963968L, 72057594037927936L, //57
        144115188075855872L, 288230376151711744L, 576460752303423488L, //60
        1152921504606846976L, 2305843009213693952L, 4611686018427387904L //61-63
        };    // initized in init()
    /** hardcode to init values of MASK as assigned by codes below    
    {
        // init MASK, which has a value corresponding to power of 2
        MASK[0] = 1;
        for(int i = 1; i < MAX_BIT; i++)
        {
            MASK[i] = MASK[i-1]*2;     // value of bit mask = power of 2
        }
    }
    **/
    private long value_;                            // use a long for its value
    private long allBitValue_ = -1;                 // object related
    private ArrayList<Vector<String>> bitStr_ 
        = new ArrayList<Vector<String>>(MAX_BIT);        // change from array
}
