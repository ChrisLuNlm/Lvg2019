package gov.nih.nlm.nls.lvg.Lib;
import java.io.*;
import java.util.*;
import gov.nih.nlm.nls.lvg.Util.*;
/*****************************************************************************
* This class provides LVg Global Behavior related definition and methods.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class GlobalBehavior
{
    // private constructor, no one can create it
    private GlobalBehavior()
    {
    }
    // public method
    public synchronized static GlobalBehavior GetInstance()
    {
        if(instance_ == null)
        {
            instance_ = new GlobalBehavior();
        }
        return instance_;
    }
    public void SetFieldSeparator(String value)
    {
        fieldSeparator_ = value;
    }
    public String GetFieldSeparator()
    {
        return fieldSeparator_;
    }
    
    // data member
    /** LVG default separator: "|" */
    public final static String LS_STR 
        = System.getProperty("line.separator").toString();    // line sep string
    // private data
    public final static String FS_STR = "|";    // field seperator string
    /** LVG version */
    public final static String YEAR = "2019";    // year of release
    /** LVG jar string */
    public final static String LVG_JAR_FILE = "lvg" + YEAR + "dist.jar";
    private String fieldSeparator_ = FS_STR;
    // singleton instance
    private static GlobalBehavior instance_;
}
