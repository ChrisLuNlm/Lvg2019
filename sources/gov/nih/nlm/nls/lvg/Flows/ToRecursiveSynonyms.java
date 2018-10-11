package gov.nih.nlm.nls.lvg.Flows;
import java.util.*;
import java.sql.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Util.*;
import gov.nih.nlm.nls.lvg.Db.*;
/*****************************************************************************
* This class generates synonyms from the input term, recursively, until there 
* are no more, or until a cycle is detected.  The input term needs to be 
* stripped punctuationand then lowercased in SQL query.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/rSynonym.html">
* Design Document </a>
* @see ToSynonyms
*
* @version    V-2019
****************************************************************************/
public class ToRecursiveSynonyms extends Transformation implements Cloneable
{
    // public methods
    /**
    * Performs the mutation of this flow component.
    *
    * @param   in   a LexItem as the input for this flow component
    * @param   conn   LVG database connection
    * @param   srcOption     the filter option of synonym source
    * @param   detailsFlag   a boolean flag for processing details information
    * @param   mutateFlag   a boolean flag for processing mutate information
    * @param   detailFlowFlag   a boolean flag for showing the flow history in
    * details
    *
    * @return  the results from this flow component - a collection (Vector)
    * of LexItems
    *
    * @see DbBase
    */
    public static Vector<LexItem> Mutate(LexItem in, Connection conn, 
        int srcOption, boolean detailsFlag, boolean mutateFlag, 
        boolean detailFlowFlag)
    {
        ToRecursiveSynonyms toRecursiveSynonyms = new ToRecursiveSynonyms();
        // mutate: 
        // set the input to the 1st term 
        toRecursiveSynonyms.SetSynonymVector(in);  
        toRecursiveSynonyms.GetRecursiveSynonyms(in, conn, srcOption, INFO, 
            true, detailsFlag, mutateFlag, null);
        // remove the original term and updated the history
        toRecursiveSynonyms.CleanSynonymVector(detailFlowFlag);    
        Vector<LexItem> outs = toRecursiveSynonyms.GetSynonymVector();
        return outs;
    }
    /**
    * Performs the mutation of this flow component.
    *
    * @param   in   a LexItem as the input for this flow component
    * @param   conn   LVG database connection
    * @param   detailsFlag   a boolean flag for processing details information
    * @param   mutateFlag   a boolean flag for processing mutate information
    * @param   detailFlowFlag   a boolean flag for showing the flow history in
    * details
    *
    * @return  the results from this flow component - a collection (Vector)
    * of LexItems
    *
    * @see DbBase
    */
    public static Vector<LexItem> Mutate(LexItem in, Connection conn, 
        boolean detailsFlag, boolean mutateFlag, boolean detailFlowFlag)
    {
        int srcOption = OutputFilter.S_SRC_ALL;
        Vector<LexItem> out = Mutate(in, conn, srcOption, detailsFlag, 
            mutateFlag, detailFlowFlag);
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
        String testStr = GetTestStr(args, "chest");
        // Mutate
        LexItem in = new LexItem(testStr, Category.ALL_BIT_VALUE, 
            Inflection.ALL_BIT_VALUE);
        Vector<LexItem> outs = new Vector<LexItem>();
        int srcOption = OutputFilter.S_SRC_ALL;
        try
        {
            Connection conn = DbBase.OpenConnection(conf);
            if(conn != null)
            {
                outs = ToRecursiveSynonyms.Mutate(in, conn, srcOption,
                    true, true, false);
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
    // Get synonyms, recursively
    private void GetRecursiveSynonyms(LexItem in, Connection conn,
        int srcOption, String infoStr, boolean topLevel, boolean detailsFlag, 
        boolean mutateFlag, String rFlowHistory)
    {
        // calculate detail recursive history
        // if first time, set flow history to original history
        StringBuffer buffer = new StringBuffer();
        if(rFlowHistory == null)    // update rFlow history
        {
            String prevHistory = in.GetFlowHistory();
            rFlowHistory = new String();
            if(prevHistory == null)    // first time
            {
                rFlowHistory = new String();
            }
            else
            {
                buffer.append(prevHistory);    // for serial flows
                buffer.append("+");
            }
        }
        buffer.append(Flow.GetBitName(Flow.SYNONYMS, 1));
        rFlowHistory += buffer.toString();
        // debug print out
        // the mutateFlag must set to be true, so we can find the sSource
        Vector<LexItem> temp = GetSynonyms(in, conn, srcOption, infoStr, 
            false, detailsFlag, true, rFlowHistory);
        // go through all elements in new Vector
        for(int i = 0; i < temp.size(); i++)
        {
            boolean filterFlag = false;
            LexItem tempRec = temp.elementAt(i);
            // update the sSource if it is topLevel
            if(topLevel == true)
            {
                sSource_ = GetSSource(tempRec.GetMutateInformation());
                filterFlag = true;
            }
            else    // Add sSource filter: CUI, EUI, NLP if not topLevel
            {
                int sSourceType = GetSSourceType(sSource_);
                String curSSource = GetSSource(tempRec.GetMutateInformation());
                int curSSourceType = GetSSourceType(curSSource);
                // CUI: must have same CUI
                if((sSourceType == OutputFilter.S_SRC_CUI)
                && (curSSourceType == OutputFilter.S_SRC_CUI)
                && (sSource_.equals(curSSource) == true))
                {
                    filterFlag = true;
                }
                // EUI: must also a EUI
                else if((sSourceType == OutputFilter.S_SRC_EUI)
                && (curSSourceType == OutputFilter.S_SRC_EUI))
                {
                    filterFlag = true;
                }
                // NLP: must be the same NLP source
                else if((sSourceType == OutputFilter.S_SRC_NLP)
                && (curSSourceType == OutputFilter.S_SRC_NLP)
                && (sSource_.equals(curSSource) == true))
                {
                    filterFlag = true;
                }
            }
        
            // go through all filtered elements
            // throw away non-filtered elements
            if(filterFlag == true)
            {
                String tempMutationInformation = tempRec.GetMutateInformation();
                String tempHistory = GetRFlowHistory(tempMutationInformation);
                // check if exist in acumulated synonyms by going through 
                // all elements, if exist, 
                // update the recursive path in rFlowHistory to the shortest
                boolean existed = false;
                for(int j = 0; j < synonyms_.size(); j++)
                {
                    // Update category and inflection if the term exists
                    LexItem orgRec = synonyms_.elementAt(j);
                    // check the term with the first element - original term
                    if(j == 0)
                    {
                        if(orgRec.GetTargetTerm().equals(tempRec.GetTargetTerm()))
                        {
                            existed = true;
                            break;
                        }
                    }
                    else
                    {
                        // lexItems are same if they have same target term, cat,
                        // infl;
                        // inflection are all set to base (1), no need to check
                        if((orgRec.GetTargetTerm().equals(tempRec.GetTargetTerm()))
                        && (orgRec.GetTargetCategory().GetValue() ==
                            tempRec.GetTargetCategory().GetValue()))
                        {
                            // bug fixed for 2018 
                            // update the history to the shortest one
                            // in history and mutateInformation 
                            // this is needed for mutationInfo in fruitful flow
                            String orgHistory = GetRFlowHistory(
                                orgRec.GetMutateInformation());
                            if(tempHistory.length() < orgHistory.length())
                            {
                                orgRec.SetFlowHistory(tempHistory);
                                orgRec.SetMutateInformation(
                                    tempMutationInformation);
                            }
                            existed = true;
                            break;
                        }
                    }
                }
                // if the synonym does not pre-exist, add into Vector
                // call the recursive synonym 
                if(existed == false)
                {
                    synonyms_.addElement(tempRec);
                    LexItem newLexItem = LexItem.TargetToSource(tempRec);
                    // set the topLevel to false,
                    // the mutateFlag must be true to get the sSource
                    GetRecursiveSynonyms(newLexItem, conn, srcOption, infoStr, 
                        false, detailsFlag, true, rFlowHistory);
                }
            }
        }
    }
    // reset the synonym vector by adding a new synonym record with a string
    private void SetSynonymVector(LexItem in)
    {
        synonyms_.removeAllElements();
        LexItem lexItem = new LexItem(in, true);
        lexItem.SetTargetTerm(in.GetSourceTerm());
        synonyms_.addElement(lexItem);
    }
    // remove the very first elements since it is the original term
    private void CleanSynonymVector(boolean detailFlowFlag)
    {
        // revove the first LexItem, which is the original input
        synonyms_.removeElementAt(0);
        // change the history to "r"
        if(detailFlowFlag == false)
        {
            String flowName = Flow.GetBitName(Flow.RECURSIVE_SYNONYMS, 1);
            for(int i = 0; i < synonyms_.size(); i++)
            {
                LexItem temp = synonyms_.elementAt(i);
                temp.SetFlowHistory(flowName);
            }
        }
    }
    // Get the synonym vector, all recursive synonym records
    private Vector<LexItem> GetSynonymVector()
    {
        return synonyms_;
    }
    // get synonym from Lvg Db
    private Vector<LexItem> GetSynonyms(LexItem in, Connection conn,
        int srcOption, String infoStr, boolean appendFlowHistory, 
        boolean detailsFlag, boolean mutateFlag, String rFlowHistory)
    {
        String inStr = in.GetSourceTerm();
        Vector<LexItem> out = new Vector<LexItem>();
        // strip punctuations & lowercase
        String strippedStr = ToStripPunctuation.StripPunctuation(inStr);
        String lcStrippedStr = strippedStr.toLowerCase();
        // set flow history for detail information
        String flowName = rFlowHistory;
        try
        {
            // get synonyms from database
            Vector<SynonymRecord> records 
                = DbSynonym.GetSynonymsBySource(lcStrippedStr, conn, srcOption);
            long inCat = in.GetSourceCategory().GetValue();
            // update LexItems
            for(int i = 0; i < records.size(); i++)
            {
                SynonymRecord record = records.elementAt(i);
                String term = record.GetSynonym();
                long curCat = record.GetCat1();
                // input filter: category
                // No inflection infomation in the database table
                if(InputFilter.IsLegal(inCat, curCat) == false)
                {
                    continue;
                }
                // details & mutate
                String details = null;
                String mutate = null;
                if(detailsFlag == true)
                {
                    details = infoStr;
                }
                if(mutateFlag == true)
                {
                    String fs 
                        = GlobalBehavior.GetInstance().GetFieldSeparator();
                    mutate = "FACT" + fs
                        + record.GetKeyForm() + fs
                        + Category.ToName(record.GetCat1()) + fs
                        + record.GetSynonym() + fs
                        + Category.ToName(record.GetCat2()) + fs
                        + record.GetSSource() + fs
                        + rFlowHistory + fs;
                }
                LexItem temp = UpdateLexItem(in, term, flowName, 
                    record.GetCat2(), 
                    Inflection.GetBitValue(Inflection.BASE_BIT), 
                    details, mutate, false);
                out.addElement(temp);
            }
        }
        catch (SQLException e) { }
        return out;
    }
    private static int GetSSourceType(String sSource)
    {
        // default type
        int sSourceType = OutputFilter.S_SRC_NLP;
        String cuiPattern = "C[0-9]{7}";
        String euiPattern = "E[0-9]{7}";
        // sSource: CUI
        if(Pattern.matches(cuiPattern, sSource) == true)
        {
            sSourceType = OutputFilter.S_SRC_CUI;
        }
        else if(Pattern.matches(euiPattern, sSource) == true)
        {
            sSourceType = OutputFilter.S_SRC_EUI;
        }
        return sSourceType;
    }
    private static String GetSSource(String mutateInformation)
    {
        String sSource = mutateInformation.split("\\|")[5];
        return sSource;
    }
    private static String GetRFlowHistory(String mutateInformation)
    {
        String rFlowHistory = mutateInformation.split("\\|")[6];
        return rFlowHistory;
    }
    // data members
    private final static String INFO = "Recursive Synonyms";
    private Vector<LexItem> synonyms_ = new Vector<LexItem>();
    private String sSource_ = new String();    //used as filter for recursive syn
}
