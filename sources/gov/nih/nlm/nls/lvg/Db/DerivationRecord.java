package gov.nih.nlm.nls.lvg.Db;
import gov.nih.nlm.nls.lvg.Lib.*;
/*****************************************************************************
* This class defined the data structure of a derivation record.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see 
* <a href="../../../../../../../designDoc/UDF/database/derivationTable.html">
* Desgin Document </a>
*
* @version    V-2019
****************************************************************************/
public class DerivationRecord
{
    // public constructor
    /**
    * Create a derivation record, using source term, source category,
    * target term, and target category.
    *
    * @param source input source string
    * @param sourceCat input source category
    * @param target output target string
    * @param targetCat output target category
    */
    public DerivationRecord(String source, int sourceCat, String target, 
        int targetCat)
    {
        source_ = source;
        target_ = target;
        sourceCat_ = sourceCat;
        targetCat_ = targetCat;
    }
    /**
    * Create a derivation record, using source term, source category,
    * target term, and target category.
    *
    * @param source input source string
    * @param sourceCat input source category
    * @param sourceEui input source EUI
    * @param target output target string
    * @param targetCat output target category
    * @param targetEui input target EUI
    * @param dType derivatin type - P|S|Z
    * @param negation - negation - N|O
    * @param prefix prefix for derivation, none for S|Z
    */
    public DerivationRecord(String source, int sourceCat, String sourceEui, 
        String target, int targetCat, String targetEui, String dType,
        String negation, String prefix)
    {
        source_ = source;
        target_ = target;
        sourceCat_ = sourceCat;
        targetCat_ = targetCat;
        sourceEui_ = sourceEui;
        targetEui_ = targetEui;
        dType_ = dType;
        negation_ = negation;
        prefix_ = prefix;
    }
    // public methods
    /**
    * Get the string value of source term from current derivation record.
    *
    * @return  the string value of source term from current derivation record
    */
    public String GetSource()
    {
        return source_;
    }
    /**
    * Get the string value of target term from current derivation record.
    *
    * @return  the string value of target term from current derivation record
    */
    public String GetTarget()
    {
        return target_;
    }
    /**
    * Get the source category from current derivation record.
    *
    * @return  integer value of source category from current derivation record
    */
    public int GetSourceCat()
    {
        return sourceCat_;
    }
    /**
    * Get the target category from current derivation record.
    *
    * @return  integer value of target category from current derivation record
    */
    public int GetTargetCat()
    {
        return targetCat_;
    }
    /**
    * Get the target EUI from current derivation record.
    *
    * @return  target EUI from current derivation record
    */
    public String GetTargetEui()
    {
        return targetEui_;
    }
    /**
    * Get the source EUI from current derivation record.
    *
    * @return  source EUI from current derivation record
    */
    public String GetSourceEui()
    {
        return sourceEui_;
    }
    /**
    * Get the derivation type of the dPair.
    *
    * @return  the derivation type of the dPair
    */
    public String GetDerivationType()
    {
        return dType_;
    }
    /**
    * Get the negation of the dPair.
    *
    * @return  the negation of the dPair
    */
    public String GetNegation()
    {
        return negation_;
    }
    /**
    * Get the prefix of the dPair.
    *
    * @return  the prefix of the prefixD, "none" otherwise. 
    */
    public String GetPrefix()
    {
        return prefix_;
    }
    /**
    * Get the whole record in a string format.  The format is
    * <br>src|src cat|scr EUI|tar|tar cat|tar EUI|type|negation|prefix|
    * <br> where categories are integers
    *
    * @param fs field separator
    *
    * @return  the string value of whole current derivation record
    */
    public String GetString(String fs)
    {
        String out = source_ + fs + sourceCat_ + fs + sourceEui_ + fs 
            + target_ + fs + targetCat_ + fs + targetEui_ + fs
            + dType_ + fs + negation_ + fs + prefix_ + fs;
        return out;
    }
    /**
    * Get the whole record in a pure string format.  The format is
    * <br>src|src cat|scr EUI|tar|tar cat|tar EUI|type|negation|prefix|
    * <br> where categories are in string names.
    *
    * @param fs field separator
    * @return  the string value of whole current derivation record
    */
    public String GetPureString(String fs)
    {
        String out = source_ + fs + Category.ToName(sourceCat_) + fs 
            + sourceEui_ + fs + target_ + fs + Category.ToName(targetCat_) + fs
            + targetEui_ + fs + dType_ + fs + negation_ + fs + prefix_ + fs;
        return out;
    }
    // data member
    private String source_ = null;        // source: input term
    private String target_ = null;        // target: output term
    private int sourceCat_ = 0;           // source Category 
    private int targetCat_ = 0;           // target Category 
    private String sourceEui_ = new String();    // source EUI: input EUI
    private String targetEui_ = new String();    // target EUI: output EUI
    private String dType_ = new String();    // derivation type
    private String negation_ = new String();    // negation
    private String prefix_ = new String();    // prefix
}
