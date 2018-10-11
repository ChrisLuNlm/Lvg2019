package gov.nih.nlm.nls.lvg.Trie;
import java.util.*;
import java.lang.reflect.*;
/*****************************************************************************
* This class creates an object of wild cards for trie.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/trie/wildcard.html">
* Design Document</a>
*
* @version    V-2019
****************************************************************************/
final public class WildCard
{
    // constructors
    /**
    * private constructor to make sure no one use it (since all public methods
    * in this class are static)
    */
    private WildCard()
    {
    }
    // public methods
    /**
    * Get a string with modified suffix from a specified string by giving
    * the in suffix and out suffix of a rule to be applied.
    * 
    * @param   inSuffix   the matching suffix of input term
    * @param   outSuffix   the generated output suffix
    * @param   inStr   the string will be modified it's suffix
    * 
    * @return   a string with modified suffix  
    */
    public static String GetSuffix(String inSuffix, String outSuffix,
        String inStr)
    {
        int size = inStr.length(); 
        int inSize = inSuffix.length();
        String endStr = inStr.substring(size-inSize);    // suffix of inStr
        StringBuffer suffixStr = new StringBuffer();
        // go through out all character of out suffix
        for(int i = 0; i < outSuffix.length(); i++)
        {
            char curChar = outSuffix.charAt(i);
            if((IsLegalWildCard(curChar) == true)
            && (curChar != WildCard.END))
            {
                if(i >= inSuffix.length()-1)    // outsuffix is longer
                {
                    // append char from the last of original suffix, 'S'
                    suffixStr.append(endStr.charAt(endStr.length()-2));
                }
                else  // append char from original suffix
                {
                    suffixStr.append(endStr.charAt(i));
                }
            }
            else    // append char form out suffix if not a wild card
            {
                suffixStr.append(curChar);
            }
        }
        return suffixStr.toString();
    }
    /**
    * Check if a specific key matches a character of a suffix.  The suffix is
    * represented as an array of character and is specified by index.
    *
    * @param  key   the key character to be checked for matching
    * @param  index   the index of character in the input array for matching
    * @param  inCharArray   a character array to represent a suffix
    *
    * @return   true or false to represent the specified key does or does not
    * match a specified character in the specified suffix
    */
    public static boolean IsMatchKey(char key, int index, char[] inCharArray)
    {
        boolean matchFlag = false;
        // check illegal key
        if((IsWildCard(key) == true)
        && (IsLegalWildCard(key) == false))
        {
            return false;
        }
        // check if they are matched
        char curChar = inCharArray[index];
        String curStr = String.valueOf(curChar);
        if(key == inCharArray[index])    // not wild card
        {
            matchFlag = true;
        }
        else if((key == VOWEL)
        && (VOWEL_SET.contains(curStr) == true))
        {
            matchFlag = true;
        }
        else if((key == CONSONANT)
        && (CONSONANT_SET.contains(curStr) == true))
        {
            matchFlag = true;
        }
        else if((key == SAME_AS_PREV)
        && (index < Array.getLength(inCharArray)-1)
        && (curChar == inCharArray[index+1]))
        {
            matchFlag = true;
        }
        else if((key == DIGIT) && (Character.isDigit(curChar)))
        {
            matchFlag = true;
        }
        else if((key == LETTER) && (Character.isLetter(curChar))
        && (Character.isLowerCase(curChar)))
        {
            matchFlag = true;
        }
        else if((key == END) && (index == Array.getLength(inCharArray)-1))
        {
            matchFlag = true;
        }
        else if((key == BEGIN) && (index == -1))    // "^" is not included
        {
            matchFlag = true;
        }
        return matchFlag;
    }
    /**
    * Transform a specific character to a wildCard
    *
    * @param  inChar   the character to be transform to a wild card
    *
    * @return   a wild card, which is transformed from the specific character
    */
    public static char WildCardTransform(char inChar)
    {
        String inStr = String.valueOf(inChar);
        char outChar = inChar;
        // Transform Vowel to V
        if(VOWEL_WILD_CARD_SET.contains(inStr) == true)
        {
            outChar = VOWEL;
        }
        else if(CONSONANT_WILD_CARD_SET.contains(inStr) == true)
        {
            outChar = CONSONANT;
        }
        // Transform Consonant to C
        return outChar;
    }
    /**
    * Transform a specific string to a string consist of wildcard.
    * This function does not need to be used in LVG trie rule operations since
    * all rules are transformed into wildcard in the input file.
    *
    * @param  inStr   the string to be transformed to wild cards
    *
    * @return   a string consist of wild cards, transformed from the specific
    * string
    */
    public static String WildCardTransform(String inStr)
    {
        StringBuffer curStr = new StringBuffer(inStr);
        char lastChar = ' ';
        // go through each character of curStr, backward
        for(int i = curStr.length()-1; i >= 0; i--)
        {
            char curChar = curStr.charAt(i);
            if(IsWildCard(curChar) == true)
            {
                if(curChar == lastChar)        // set the 'S' wildcard
                {
                    curStr.setCharAt(i, SAME_AS_PREV);
                }
                else        // set all other wild card
                {
                    curStr.setCharAt(i, WildCardTransform(curChar));
                }
            }
            lastChar = inStr.charAt(i);
        }
        return curStr.toString();
    }
    /**
    * Check if a specific character belong ot a pre-defined wild card.
    *
    * @param  inChar   the character to be test for a wild card
    *
    * @return   true or false to represent the specified character is or is not
    * a wild card
    */
    public static boolean IsWildCard(char inChar)
    {
        // a wildcard must be an upper case letter (not including digit)
        boolean isWildCard = 
            Character.isLetter(inChar) && !Character.isLowerCase(inChar);
        return isWildCard;
    }
    /**
    * test driver for this class.
    *
    * @param args arguments
    */
    public static void main(String[] args)
    {
        String str = "CEXXer$|adj|comparative|CEX$|adj|positive";
        System.out.println("in:  '" + str + "'");
        System.out.println("out: '" + WildCard.WildCardTransform(str) + "'");
        System.out.println("-----------------------");
        //str = "CUAW|CUUW|CUAC|CDDer";
        str = "|CUAB|BDDer|ARRRRT|arrrrt";
        System.out.println("in:  '" + str + "'");
        System.out.println("out: '" + WildCard.WildCardTransform(str) + "'");
        System.out.println("-----------------------");
        System.out.println("IsLegalWildCard('A'): " + 
            WildCard.IsLegalWildCard('A'));
        System.out.println("IsLegalWildCard('S'): " + 
            WildCard.IsLegalWildCard('S'));
    }
    // private method
    private static boolean IsLegalWildCard(char inChar)
    {
        String inStr = String.valueOf(inChar);
        return WILD_CARD_SET.contains(inStr);
    }
    // data member
    /** a character symbol for representing a vowel: [a,e,i,o,u] */
    public final static char VOWEL = 'V';
    /** a character symbol for representing a consonant */
    public final static char CONSONANT = 'C';
    /** a character symbol for representing a same character as the following
    character in the string */
    public final static char SAME_AS_PREV = 'S';    // same char as prievious 
    /** a character symbol for representing a digit [0-9] */
    public final static char DIGIT     = 'D';        // any digit 0-9
    /** a character symbol for representing a letter [a-z] */
    public final static char LETTER    = 'L';        // any letter a-z
    /** a character symbol for represetning the beginning of a string */
    public final static char BEGIN     = '^';        // START
    /** a character symbol for represetning the end of a string */
    public final static char END       = '$';        // END
    /** a character symbol for representing a field separator */
    public final static char FS        = '|';        // Field Separator
    private final static HashSet<String> VOWEL_WILD_CARD_SET 
        = new HashSet<String>(Arrays.asList("A", "E", "I", "O", "U"));
    private final static HashSet<String> CONSONANT_WILD_CARD_SET 
        = new HashSet<String>(Arrays.asList("B", "C", "F", "G", "H",
            "J", "K", "M", "N", "P", "Q", "R", "T", "V", "W",
            "X", "Y", "Z"));
    private final static HashSet<String> WILD_CARD_SET 
        = new HashSet<String>(Arrays.asList(
            new Character(VOWEL).toString(),
            new Character(CONSONANT).toString(),
            new Character(SAME_AS_PREV).toString(),
            new Character(DIGIT).toString(),
            new Character(BEGIN).toString(),
            new Character(END).toString()));
    private final static HashSet<String> VOWEL_SET 
        = new HashSet<String>(Arrays.asList("a", "e", "i", "o", "u",
            // add diacritics
            "\u00E0", "\u00E1", "\u00E2", "\u00E3", "\u00E4",
            "\u00E5", "\u00E8", "\u00E9", "\u00EA", "\u00EB",
            "\u00EC", "\u00ED", "\u00EE", "\u00EF", "\u00F0",
            "\u00F2", "\u00F3", "\u00F4", "\u00F5", "\u00F6",
            "\u00F8", "\u00F9", "\u00FA", "\u00FB", "\u00FC"
            ));
    private final static HashSet<String> CONSONANT_SET 
        = new HashSet<String>(Arrays.asList("b", "c", "d", "f", "g",
            "h", "j", "k", "l", "m", "n", "p", "q", "r", "s",
            "t", "v", "w", "x", "y", "z",
            // add diacritics
            "\u00E7", "\u00F1", "\u00FD", "\u00FE"));
    //private final static HashSet<String> DIGIT_SET = new HashSet<String>();
    private final static HashSet<String> LETTER_SET 
        = new HashSet<String>(Arrays.asList("b", "c", "d", "f", "g",
            "h", "j", "k", "l", "m", "n", "p", "q", "r", "s",
            "t", "v", "w", "x", "y", "z",
            // add diacritics
            "\u00E7", "\u00F1", "\u00FD", "\u00FE",
            "a", "e", "i", "o", "u",
            // add diacritics
            "\u00E0", "\u00E1", "\u00E2", "\u00E3", "\u00E4",
            "\u00E5", "\u00E8", "\u00E9", "\u00EA", "\u00EB",
            "\u00EC", "\u00ED", "\u00EE", "\u00EF", "\u00F0",
            "\u00F2", "\u00F3", "\u00F4", "\u00F5", "\u00F6",
            "\u00F8", "\u00F9", "\u00FA", "\u00FB", "\u00FC"));
}
