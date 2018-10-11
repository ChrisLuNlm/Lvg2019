package gov.nih.nlm.nls.lvg.Flows;
import java.util.*;
import java.sql.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Db.*;
/*****************************************************************************
* This class generates synonyms from the specified term.  The synonyms are 
* pre-computed and put in the Synonym table of the LVG database.  The input
* term need to be stripped punctuation and then lowercased in SQL query before 
* being sent to Lvg database.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/synonym.html">
* Design Document </a>
*
* @version    V-2019
****************************************************************************/
public class ToSynonyms extends Transformation implements Cloneable
{
    // public methods
    /**
    * Performs the mutation of this flow component.
    *
    * @param   in   a LexItem as the input for this flow component
    * @param   conn   LVG database connection
    * @param   srcOption   filter option of the synonym source
    * @param   detailsFlag   a boolean flag for processing details information
    * @param   mutateFlag   a boolean flag for processing mutate information
    *
    * @return  the results from this flow component - a collection (Vector)
    * of LexItems
    *
    * @see DbBase
    */
    public static Vector<LexItem> Mutate(LexItem in, Connection conn, 
        int srcOption, boolean detailsFlag, boolean mutateFlag)
    {
        // mutate: 
        Vector<SynonymRecord> records = GetSynonyms(in.GetSourceTerm(), conn,
            srcOption);
        long inCat = in.GetSourceCategory().GetValue();
        // update target LexItem
        Vector<LexItem> out = new Vector<LexItem>();
        for(int i = 0; i < records.size(); i++)
        {
            SynonymRecord record = records.elementAt(i);
            String term = record.GetSynonym();
            long curCat = record.GetCat1();
            // input filter: category
            if(InputFilter.IsLegal(inCat, curCat) == false)
            {
                continue;
            }
            // details & mutate
            String details = null;
            String mutate = null;
            if(detailsFlag == true)
            {
                details = INFO;
            }
            if(mutateFlag == true)
            {
                String fs 
                    = GlobalBehavior.GetInstance().GetFieldSeparator();
                mutate = "FACT" + fs
                    + record.GetKeyFormNpLc() + fs
                    + record.GetKeyForm() + fs
                    + Category.ToName(record.GetCat1()) + fs
                    + record.GetSynonym() + fs
                    + Category.ToName(record.GetCat2()) + fs
                    + record.GetSSource() + fs;
            }
            LexItem temp = UpdateLexItem(in, term, Flow.SYNONYMS, 
                record.GetCat2(), 
                Inflection.GetBitValue(Inflection.BASE_BIT), 
                details, mutate);
            out.addElement(temp);
        }
        return out;
    }
    /**
    * Performs the mutation of this flow component.
    *
    * @param   in   a LexItem as the input for this flow component
    * @param   conn   LVG database connection
    * @param   detailsFlag   a boolean flag for processing details information
    * @param   mutateFlag   a boolean flag for processing mutate information
    *
    * @return  the results from this flow component - a collection (Vector)
    * of LexItems
    *
    * @see DbBase
    */
    public static Vector<LexItem> Mutate(LexItem in, Connection conn, 
        boolean detailsFlag, boolean mutateFlag)
    {
        int srcOption = OutputFilter.S_SRC_ALL;
        Vector<LexItem> out = Mutate(in, conn, srcOption, detailsFlag,
            mutateFlag);
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
        String testStr = GetTestStr(args, "sensation");
        int srcOption = OutputFilter.S_SRC_ALL;
        // Mutate
        LexItem in = new LexItem(testStr, Category.ALL_BIT_VALUE, 
            Inflection.ALL_BIT_VALUE);
        Vector<LexItem> outs = new Vector<LexItem>();
        try
        {
            Connection conn = DbBase.OpenConnection(conf);
            if(conn != null)
            {
                outs = ToSynonyms.Mutate(in, conn, srcOption, true, true);
            }
            DbBase.CloseConnection(conn, conf);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        PrintResults(in, outs);     // print out results
    }
    // private methods
    private static Vector<SynonymRecord> GetSynonyms(String inStr, 
        Connection conn, int srcOption)
    {
        // strip punctuations, remvoe extra space & lowercase
        String strippedStr = ToStripPunctuation.StripPunctuation(inStr);
        String outNoExtraSpace = StringTrim(strippedStr);
        String lcStrippedStr = outNoExtraSpace.toLowerCase();
        Vector<SynonymRecord> out = new Vector<SynonymRecord>();
        // get synonyms from LvgDB
        try
        {
            out = DbSynonym.GetSynonymsBySource(lcStrippedStr, conn, srcOption);
        }
        catch (SQLException e) 
        {
            System.err.println("**Err@GetSynonyms( ): " + e.toString());
        }
        return out;
    }
    private static String StringTrim(String in)
    {
        StringTokenizer buf = new StringTokenizer(in, " \t");
        String out = new String();
        while(buf.hasMoreTokens())
        {
            out += buf.nextToken() + " ";
        }
        return out.trim();
    }
    // data members
    private final static String INFO = "Generate Synonyms";
}
