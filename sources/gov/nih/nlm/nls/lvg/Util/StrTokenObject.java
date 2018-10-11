package gov.nih.nlm.nls.lvg.Util;
/*****************************************************************************
* This class creates a fundamental string token object for strip function.
* The strip function become complicated if it combines with punctuations.
* This class along with StripToken handle such cases.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see        StripToken
*
* @version    V-2019
****************************************************************************/
public class StrTokenObject
{
    // public constructor
    /**
    * Creates a string token object, using token string and token type
    *
    * @param   tokenStr  the token string
    * @param   tokenType  the type of token
    */
    public StrTokenObject(String tokenStr, int tokenType)
    {
        tokenStr_ = tokenStr;
        tokenType_ = tokenType;
    }
    // public methods
    /**
    * Set the token type of this string token object
    *
    * @param   tokenType token type 
    */
    public void SetTokenType(int tokenType)
    {
        tokenType_ = tokenType;
    }
    /**
    * Get the token type of this token object
    *
    * @return  an integer represents the type of the token
    */
    public int GetTokenType()
    {
        return tokenType_;
    }
    /**
    * Get the token object in a String format
    *
    * @return  toekn string
    */
    public String GetTokenStr()
    {
        return tokenStr_;
    }
    // data member
    /** token type: None */
    public final static int NONE        = 0; 
    /** token type: it is a token, not a delimiter */
    public final static int TOKEN       = 1;
    /** token type: space token, such as " \t" */
    public final static int SPACE_D     = 2;
    /** token type: token is stripped, won't be resotred while restoring */
    public final static int STRIPPED    = 3;
    /** token type: delimiters will be kept while restoring */
    public final static int RESTORE_D   = 4;
    /** token type: delimiters that strip previous chararcter if it's token 
    * type is STRIPPED or in a conflict list
    */
    public final static int STRIPPING_D = 5; 
    /** token type: delimiters that will be striped if it's previous token type
    * is STRIPPED
    */
    public final static int STRIPABLE_D = 6; 
    /** delimiter string: space */
    public final static String SPACE_D_STR = " \t";
    /** delimiter string: restore it after tokenizing */
    public final static String RESTORE_D_STR = "({[<\"'";
    /** delimiter string: strip out */
    public final static String STRIPPING_D_STR = ")}]>!?";
    /** delimiter string: can be stripped */
    public final static String STRIPABLE_D_STR = ",.:;";
    /** delimiter string: conflict list string for reomving character */
    public final static String CONFLICT_STR = " -,:;";
    /** delimiter string: should not be at the end of the string */
    public final static String BAD_END_STR = " -,:;";
    // private data members
    private int tokenType_ = NONE;                // Deliminator type
    private String tokenStr_ = null;            // the token in String format
}
