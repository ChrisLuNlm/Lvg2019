package gov.nih.nlm.nls.lvg.Lib;
import java.util.*;
/*****************************************************************************
* This class provides key for derivational LexItem used in recursively 
* derivational flow.  This overrides the hashcode() and equals() for using 
* hashtable and hashSet.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class TermCatCatKey
{
    // public constructor
    /**
    * Create a key object, using a specified term and categories
    *
    * @param term input String format
    * @param inCat input category
    * @param outCat output category
    */
    public TermCatCatKey(String term, int inCat, int outCat)
    {
        term_ = term;
        inCat_ = inCat;
        outCat_ = outCat;
    }
    /**
    * Get the term of this object
    *
    * @return term in String format
    */
    public String GetTerm()
    {
        return term_;
    }
    /**
    * Get the category value of this object
    *
    * @return category value
    */
    public int GetInCat()
    {
        return inCat_;
    }
    /**
    * Get the category value of this object
    *
    * @return category value
    */
    public int GetOutCat()
    {
        return outCat_;
    }
    public String ToString()
    {
        String outStr = term_ + GlobalBehavior.FS_STR + inCat_ 
            + GlobalBehavior.FS_STR + outCat_;
        return outStr;    
    }
    public int hashCode()
    {
        return outCat_;
    }
    public boolean equals(Object anObject)
    {
        if((anObject != null) && (anObject instanceof TermCatCatKey))
        {
            TermCatCatKey temp = (TermCatCatKey) anObject;
            if((term_.equals(temp.GetTerm()))
            && (inCat_ == temp.GetInCat())
            && (outCat_ == temp.GetOutCat()))
            {
                return true;
            }
            return false;
        }
        else
        {
            return false;
        }
    }
    private String term_ = null;
    private int inCat_ = (int) Category.NO_BIT_VALUE;
    private int outCat_ = (int) Category.NO_BIT_VALUE;
}
