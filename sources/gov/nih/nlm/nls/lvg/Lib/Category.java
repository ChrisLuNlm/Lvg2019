package gov.nih.nlm.nls.lvg.Lib;
import java.util.*;
import gov.nih.nlm.nls.lvg.Util.*;
/*****************************************************************************
* This Category class extends BitMaskBase class and performs as LVG category.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/design.html#CATEGORY">
*      Design document </a>
*
* @version    V-2019
****************************************************************************/
public class Category extends BitMaskBase
{
    // public constructors
    /**
    *    Creates a default category object
    */
    public Category()
    {
        super(ALL_BIT_VALUE, BIT_STR);
    }
    /**
    * Creates a default category object, using a long value
    *
    * @param   value  long value to find for bit index
    */
    public Category(long value)
    {
        super(value, ALL_BIT_VALUE, BIT_STR);
    }
    /**
    * Convert a combined value string to a long category value.
    *
    * @param   valueStr  combined name in String format
    *
    * @return  a long value of the specified name
    */
    public static long ToValue(String valueStr)
    {
        return ToValue(valueStr, BIT_STR);
    }
    /**
    * Convert a long category value to a combined string (abbreviation).
    *
    * @param   value  number for finding it's combined name
    *
    * @return  name in a String format
    */
    public static String ToName(long value)
    {
        return ToName(value, ALL_BIT_VALUE, BIT_STR);
    }
    /**
    * Get the name (first in the name list) of a specified bit (single).
    * The first one in LVG is the abbreviation name.
    *
    * @param   bitValue  bit nubmer for finding it's name
    *
    * @return  name of the bit specified
    */
    public static String GetBitName(int bitValue)
    {
        return GetBitName(bitValue, 0);
    }
    /**
    * Get the name at index order of a specified bit (single).
    *
    * @param   bitValue  bit nubmer for finding it's name
    * @param   index   the order index of the name in BIT_STR[]
    *
    * @return  name at index order of the bit specified
    */
    public static String GetBitName(int bitValue, int index)
    {
        return GetBitName(bitValue, index, BIT_STR);
    }
    /**
    * Get the long value for one single name (no combine names of bits).
    *
    * @param   valueStr  name of a bit for finding it's long value
    *
    * @return  a long value of the specified name
    *
    */
    public static long Enumerate(String valueStr)
    {
        return Enumerate(valueStr, BIT_STR);
    }
    /**
    * Get a Vector of Long includes all values from the combined value.  
    * For examples, a value of 129 will return a vector contains two elements 
    * (Long) with value of 1 and 128.
    *
    * @param   value  combined value
    *
    * @return  A Vector of Long - all values from the combined value
    */
    public static Vector<Long> ToValues(long value)
    {
        return ToValues(value, TOTAL_BITS);
    }
    /**
    * Get an array includes all values from the combined value.  For example,
    * a value of 129 will return an array contains two elements (long) with
    * value of 1 and 128.
    *
    * @param   value  combined value
    *
    * @return  an array includes all values from the combined value
    */
    public static long[] ToValuesArray(long value)
    {
        Vector<Long> out = ToValues(value, TOTAL_BITS);
        return Vec.ToArray(out);
    }
    // public method
    /** 
    * Test driver for this class
    *
    * @param args arguments
    */
    public static void main(String[] args)
    {
        // static methods
        System.out.println("----- static methods -----");
        System.out.println(" -  ToValue(adv+adv+noun+verb): " +
            Category.ToValue("adv+adj+noun+verb"));
        System.out.println(" -  ToName(1155): " + Category.ToName(1155));
        Vector<Long> values = Category.ToValues(1155);
        for(int i = 0; i < values.size(); i++)
        {
            System.out.println(" - Category.ToValues(1155): "
                + values.elementAt(i));
        }
        long[] valuesArray = Category.ToValuesArray(1155);
        for(int i = 0; i < valuesArray.length; i++)
        {
            System.out.println(" - Category.ToValues(1155): " + valuesArray[i]);
        }
        System.out.println(" -  ToValue(noun): " + Category.ToValue("noun"));
        System.out.println(" -  GetBitValue(Category.NOUN_BIT): " 
            + Category.GetBitValue(Category.NOUN_BIT));
        System.out.println("----- object methods -----");
        Category c1 = new Category(1155);
        System.out.println(" - c1.GetValue(): " + c1.GetValue());
        System.out.println(" - c1.GetName(): " + c1.GetName());
        c1.SetValue(2047);
        System.out.println(" - c1.GetValue(): " + c1.GetValue());
        System.out.println(" - c1.GetName(): " + c1.GetName());
    }
    // data members
    /** Adjective bit for category */
    public final static int ADJ_BIT   = 0;        // Adjective
    /** Adverb bit for category */
    public final static int ADV_BIT   = 1;        // Adverb
    /** Auxiliary bit for category */
    public final static int AUX_BIT   = 2;        // Auxiliary
    /** Complementizer bit for category */
    public final static int COMPL_BIT = 3;        // Complementizer
    /** Conjunction bit for category */
    public final static int CONJ_BIT  = 4;        // Conjunction
    /** Determiner bit for category */
    public final static int DET_BIT   = 5;        // Determiner
    /** Modal bit for category */
    public final static int MODAL_BIT = 6;        // Modal
    /** Noun bit for category */
    public final static int NOUN_BIT  = 7;        // Noun
    /** Preposition bit for category */
    public final static int PREP_BIT  = 8;        // Preposition
    /** Pronoun bit for category */
    public final static int PRON_BIT  = 9;        // Pronoun
    /** Verb bit for category */
    public final static int VERB_BIT  = 10;        // Verb
    /** All bits for category */
    public final static int TOTAL_BITS = 11;          // total bits used
    public final static long ALL_BIT_VALUE = 2047;    // value for all bits 
    public final static long NO_BIT_VALUE = 0;        // value for no bit 
    private final static ArrayList<Vector<String>> BIT_STR 
        = new ArrayList<Vector<String>>(MAX_BIT);  // Must have
    static
    {
        for(int i = 0; i < MAX_BIT; i++)
        {
            BIT_STR.add(i, new Vector<String>());
        }
        // define all bit string
        BIT_STR.get(ADJ_BIT).addElement("adj");
        BIT_STR.get(ADJ_BIT).addElement("adjective");
        BIT_STR.get(ADJ_BIT).addElement("ADJ");
        BIT_STR.get(ADV_BIT).addElement("adv");
        BIT_STR.get(ADV_BIT).addElement("adverb");
        BIT_STR.get(ADV_BIT).addElement("ADV");
        BIT_STR.get(AUX_BIT).addElement("aux");
        BIT_STR.get(AUX_BIT).addElement("auxiliary");
        BIT_STR.get(COMPL_BIT).addElement("compl");
        BIT_STR.get(COMPL_BIT).addElement("complementizer");
        BIT_STR.get(CONJ_BIT).addElement("conj");
        BIT_STR.get(CONJ_BIT).addElement("conjunction");
        BIT_STR.get(CONJ_BIT).addElement("CON");
        BIT_STR.get(CONJ_BIT).addElement("con");
        BIT_STR.get(DET_BIT).addElement("det");
        BIT_STR.get(DET_BIT).addElement("determiner");
        BIT_STR.get(DET_BIT).addElement("DET");
        BIT_STR.get(MODAL_BIT).addElement("modal");
        BIT_STR.get(NOUN_BIT).addElement("noun");
        BIT_STR.get(NOUN_BIT).addElement("NOM");
        BIT_STR.get(NOUN_BIT).addElement("NPR");
        BIT_STR.get(PREP_BIT).addElement("prep");
        BIT_STR.get(PREP_BIT).addElement("preposition");
        BIT_STR.get(PREP_BIT).addElement("PRE");
        BIT_STR.get(PREP_BIT).addElement("pre");
        BIT_STR.get(PRON_BIT).addElement("pron");
        BIT_STR.get(PRON_BIT).addElement("pronoun");
        BIT_STR.get(VERB_BIT).addElement("verb");
        BIT_STR.get(VERB_BIT).addElement("VER");
        BIT_STR.get(VERB_BIT).addElement("ver");
    }
}
