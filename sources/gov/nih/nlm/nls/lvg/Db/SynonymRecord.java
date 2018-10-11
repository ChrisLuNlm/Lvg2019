package gov.nih.nlm.nls.lvg.Db;
import gov.nih.nlm.nls.lvg.Lib.*;
/*****************************************************************************
* This class defined the data structure of a synonym record.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/database/synonymTable.html">
*  Desgin Document </a>
*
* @version    V-2019
****************************************************************************/
public class SynonymRecord
{
    // public constructor
    /**
    *  Default constructor for creating a synonym record.
    */
    public SynonymRecord()
    {
    }
    // public methods
    /**
    * Set the string value of key form (no puctuations and lower cases) to 
    * current synonym record.
    *
    * @param  value   string value of key form (no puctuations and lower cases)
    */
    public void SetKeyFormNpLc(String value)
    {
        keyFormNpLc_ = value;
    }
    /**
    * Set the string value of synonym to current synonym record.
    *
    * @param  value   string value of synonym
    */
    public void SetSynonym(String value)
    {
        synonym_ = value;
    }
    /**
    * Set the string value of key form to current synonym record.
    *
    * @param  value   string value of key form
    */
    public void SetKeyForm(String value)
    {
        keyForm_ = value;
    }
    /**
    * Set the integer value of category for key form to current synonym record.
    *
    * @param  value   string value of category for key form
    */
    public void SetCat1(int value)
    {
        cat1_ = value;
    }
    /**
    * Set the integer value of category for synonym to current synonym record.
    *
    * @param  value   string value of category for synonym
    */
    public void SetCat2(int value)
    {
        cat2_ = value;
    }
    /**
    * Set the string value of synonym source to current synonym record.
    *
    * @param  value   string value of synonym source
    */
    public void SetSSource(String value)
    {
        sSource_ = value;
    }
    /**
    * Get the string value of key form (No punctuations lower case) from 
    * current synonym record.
    *
    * @return  the string value of key form (No punctuations lower case) from 
    * current acroynym record
    */
    public String GetKeyFormNpLc()
    {
        return keyFormNpLc_;
    }
    /**
    * Get the string value of key form from current synonym record.
    *
    * @return  the string value of key form from current acroynym record
    */
    public String GetKeyForm()
    {
        return keyForm_;
    }
    /**
    * Get the string value of synonym from current synonym record.
    *
    * @return  the string value of synonym from current acroynym record
    */
    public String GetSynonym()
    {
        return synonym_;
    }
    /**
    * Get the category of key form from current synonym record.
    *
    * @return  category of key form in a integer format from current acroynym 
    * record
    */
    public int GetCat1()
    {
        return cat1_;
    }
    /**
    * Get the category of synonym from current synonym record.
    *
    * @return  category of synonym in a integer format from current acroynym 
    * record
    */
    public int GetCat2()
    {
        return cat2_;
    }
    /**
    * Get the string value of synonym source from current synonym record.
    *
    * @return  the string value of synonym source from current acroynym record
    */
    public String GetSSource()
    {
        return sSource_;
    }
    /**
    * Get the string representation of synonym record object.
    *
    * @return  the string representation of synonym record record.
    */
    public String ToString()
    {
        String outStr = keyFormNpLc_ + GlobalBehavior.FS_STR
            + keyForm_ + GlobalBehavior.FS_STR 
            + cat1_ + GlobalBehavior.FS_STR
            + synonym_ + GlobalBehavior.FS_STR 
            + cat2_ + GlobalBehavior.FS_STR
            + sSource_; 
        return outStr;
    }
    // data member
    private String keyFormNpLc_ = null;   // keyForm: No Puncuation, Lowercase
    private String keyForm_ = null;       // keyForm
    private String synonym_ = null;       // Synonym
    private int cat1_ = 0;                // Category: of keyForm
    private int cat2_ = 0;                // Category: of synonym
    private String sSource_ = null;       // Synonym source
}
