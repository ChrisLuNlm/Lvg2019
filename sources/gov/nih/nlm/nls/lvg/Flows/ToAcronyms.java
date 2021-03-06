package gov.nih.nlm.nls.lvg.Flows;
import java.util.*;
import java.sql.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Db.*;
/*****************************************************************************
* This class provides features of generating known acronyms for a specified
* acronym expansion.  The input acronym expansion is stripped punctuationand 
* then lowercase before put into SQL for database query.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/acronym.html">
* Design Document </a>
*
* @version    V-2019
****************************************************************************/
public class ToAcronyms extends Transformation implements Cloneable
{
    // public methods
    /**
    * Performs the mutation of this flow component.
    *
    * @param   in   a LexItem as the input for this flow component
    * @param   conn   LVG database connection
    * @param   detailsFlag   a boolean flag for processing details information
    * @param   mutateFlag   a boolean flag for processing mutate information
    *
    * @return  Results in LexItem list from this flow component 
    *
    * @see DbBase
    */
    public static Vector<LexItem> Mutate(LexItem in, Connection conn, 
        boolean detailsFlag, boolean mutateFlag)
    {
        // mutate: Get acronyms record from Lvg database 
        Vector<AcronymRecord> records = GetAcronyms(in.GetSourceTerm(), conn);
        // update target LexItem by going through all acronym records
        Vector<LexItem> out = new Vector<LexItem>();
        int size = records.size();
        // tag: set true if output is unique, used in frutiful variants
        boolean curTagFlag = ((size==1)?true:false);
        Tag tag = new Tag(in.GetTag());
        tag.SetBitFlag(Tag.UNIQUE_ACR_EXP_BIT, curTagFlag);
        String tagStr = ((curTagFlag)?"Unique":"NotUnique");
        in.SetTag(tag.GetValue());
        for(int i = 0; i < size; i++)
        {
            AcronymRecord record = records.elementAt(i);
            String term = record.GetAcronym();
            // details & mutate
            String details = null;
            String mutate = null;
            if(detailsFlag == true)
            {
                details = INFO + " (" + record.GetType() + ")";
            }
            if(mutateFlag == true)
            {
                String fs = GlobalBehavior.GetInstance().GetFieldSeparator();
                mutate = record.GetType() + fs + tagStr + fs;
            }
            // update output LexItem
            LexItem temp = UpdateLexItem(in, term, Flow.ACRONYMS, 
                Transformation.UPDATE, 
                Inflection.GetBitValue(Inflection.BASE_BIT), 
                details, mutate);
            out.addElement(temp);
        }
        return out;
    }
    /**
    * A unit test driver for this flow component.
    *
    * @param args arguments
    */
    public static void main(String[] args)
    {
        // load config file
        Configuration conf = new Configuration("data.config.lvg", true);
        String testStr = GetTestStr(args, "VERY LOW-DENSITY LIPOPROTEIN");
        // Mutate
        LexItem in = new LexItem(testStr);
        Vector<LexItem> outs = new Vector<LexItem>();
        try
        {
            Connection conn = DbBase.OpenConnection(conf);
            if(conn != null)
            {
                outs = ToAcronyms.Mutate(in, conn, true, true);
            }
            DbBase.CloseConnection(conn, conf);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        PrintResults(in, outs);     // print out results
    }
    // private method
    private static Vector<AcronymRecord> GetAcronyms(String inStr, 
        Connection conn)
    {
        // strip punctuations & lowercase
        String strippedStr = ToStripPunctuation.StripPunctuation(inStr);
        String lcStrippedStr = strippedStr.toLowerCase();
        Vector<AcronymRecord> out = new Vector<AcronymRecord>();
        try
        {
            out = DbAcronym.GetAcronyms(lcStrippedStr, conn);
        }
        catch (SQLException e) { }
        return out;
    }
    // data members
    private final static String INFO = "Generate Acronyms";
}
