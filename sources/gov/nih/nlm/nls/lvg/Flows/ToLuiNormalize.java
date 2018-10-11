package gov.nih.nlm.nls.lvg.Flows;
import java.util.*;
import java.sql.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Db.*;
import gov.nih.nlm.nls.lvg.Trie.*;
/*****************************************************************************
* This class provides features of generating luiNorm for a specified term.
* This flow component is consist of 10 other flow components in a serial order.
* They are: q7, g, rs, o, t, l, B, C, w, q8.
*
* <p>If the flow component B generates multiple outputs in LuiNorm,
* the results are determined as shown in the following two cases: 
* <ul>
* <li> Fact:
*   <br> All citation forms are in the database and thus the results will 
*   be all the same (after canonicalized).  The program will use the first
*   output as the input for next flow component, Canonicalize.
* <li> Uninflected by rules:
*   <br> In most cases, the uninflected forms will not in the Lvg database.
*   Thus, different results will be generated by canonicalization
*   depending on which uninflected term the program pick.  In this release, 
*   the results from uninflected terms (by rules) is sorted by an alphabetical 
*   order.  Thus, theis program pick up the first one from the uninflected 
*   terms for canonicalization since it is unique and consistent.
* </ul>
* Accordingly, the output should only contain one LexItem.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/luiNormalize.html">
* Design Document </a>
* @see ToUnicodeCoreNorm
* @see ToRemoveGenitive
* @see ToRemoveS
* @see ToReplacePunctuationWithSpace
* @see ToStripStopWords
* @see ToLowerCase
* @see ToUninflectWords
* @see ToCanonicalize
* @see ToStripMapUnicode
* @see ToSortWordsByOrder
*
* @version    V-2019
****************************************************************************/
public class ToLuiNormalize extends Transformation implements Cloneable
{
    // public methods
    /**
    * Performs the mutation of this flow component.
    *
    * @param   in   a LexItem as the input for this flow component
    * @param   maxTerm   tthe maxinum number of permutation term (uninflect)
    * @param   stopWords  A Vector of String - stop wrods list
    * @param   conn   LVG database connection
    * @param   trie   LVG ram trie
    * @param   symbolMap   a hash table contains the unicode symbols mapping
    * @param   unicodeMap   a hash table contains the unicode mapping
    * @param   ligatureMap   a hash table contains the mapping of ligatures
    * @param   diacriticMap  a hash table contains the mapping of diacritics
    * @param   nonStripMap   a hash table contains the non-Strip map unicode
    * @param   removeSTree   a reverse trie tree of removeS pattern rules
    * @param   detailsFlag   a boolean flag for processing details information
    * @param   mutateFlag   a boolean flag for processing mutate information
    *
    * @return  the results from this flow component - a collection (Vector)
    * of LexItems
    *
    * @exception SQLException if errors occurr while connect to LVG database.
    *
    * @see DbBase
    */
    public static Vector<LexItem> Mutate(LexItem in, int maxTerm, 
        Vector<String> stopWords, Connection conn, RamTrie trie, 
        Hashtable<Character, String> symbolMap,
        Hashtable<Character, String> unicodeMap,
        Hashtable<Character, String> ligatureMap, 
        Hashtable<Character, Character> diacriticMap,
        Hashtable<Character, String> nonStripMap,
        RTrieTree removeSTree, boolean detailsFlag, boolean mutateFlag) 
        throws SQLException
    {
        Vector<LexItem> outList = new Vector<LexItem>();
        Vector<LexItem> inList = new Vector<LexItem>();
        inList.addElement(in);
        // go through all 10 flow components: q7, g, rs, o, t, l, B, C, w, q8
        for(int i = 0; i < LUI_NORM_STEPS; i++)
        {
            outList = GetLuiNormBySteps(i, inList, maxTerm, stopWords, conn, 
                trie, symbolMap, unicodeMap, ligatureMap, diacriticMap,
                nonStripMap, removeSTree, detailsFlag, mutateFlag);
            // convert current out to next in
            inList.removeAllElements();
            for(int j = 0; j < outList.size(); j++)
            {
                LexItem out = outList.elementAt(j);
                LexItem temp = LexItem.TargetToSource(out);
                inList.addElement(temp);
            }
        }
        // no need to update history since it's done in each flow component
        // reset mutate information
        for(int i = 0; i < outList.size(); i++)
        {
            outList.elementAt(i).SetMutateInformation(
                Transformation.NO_MUTATE_INFO);
        }
        return outList;
    }
    /**
    * A unit test driver for this flow component.
    *
    * @param args arguments
    */
    public static void main(String[] args)
    {
        // read in configuration file
        Configuration conf = new Configuration("data.config.lvg", true);
        String testStr = GetTestStr(args, "fingers");
        int minTermLen = Integer.parseInt(
            conf.GetConfiguration(Configuration.MIN_TERM_LENGTH));
        String lvgDir = conf.GetConfiguration(Configuration.LVG_DIR);
        int maxTerm = Integer.parseInt(
            conf.GetConfiguration(Configuration.MAX_UNINFLS));
        Vector<String> stopWords = ToStripStopWords.GetStopWordsFromFile(conf);
        Hashtable<Character, String> symbolMap
            = ToMapSymbolToAscii.GetSymbolMapFromFile(conf);
        Hashtable<Character, String> unicodeMap
            = ToMapUnicodeToAscii.GetUnicodeMapFromFile(conf);
        Hashtable<Character, String> ligatureMap 
            = ToSplitLigatures.GetLigatureMapFromFile(conf);
        Hashtable<Character, Character> diacriticMap 
            = ToStripDiacritics.GetDiacriticMapFromFile(conf);
        Hashtable<Character, String> nonStripMap
            = ToStripMapUnicode.GetNonStripMapFromFile(conf);
        RTrieTree removeSTree = ToRemoveS.GetRTrieTreeFromFile(conf);
        // connect to DB
        LexItem in = new LexItem(testStr);
        Vector<LexItem> outs = new Vector<LexItem>();
        try
        {
            Connection conn = DbBase.OpenConnection(conf);
            boolean isInflection = true;
            RamTrie trie = new RamTrie(isInflection, minTermLen, lvgDir, 0);
            if(conn != null)
            {
                outs = ToLuiNormalize.Mutate(in, maxTerm, stopWords, conn, 
                    trie, symbolMap, unicodeMap, ligatureMap, diacriticMap,
                    nonStripMap, removeSTree, true, true);
            }
            DbBase.CloseConnection(conn, conf);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        // print out results
        PrintResults(in, outs);
    }
    // private method
    private static Vector<LexItem> GetLuiNormBySteps(int step, 
        Vector<LexItem> ins, int maxTerm, Vector<String> stopWords, 
        Connection conn, RamTrie trie,
        Hashtable<Character, String> symbolMap,
        Hashtable<Character, String> unicodeMap,
        Hashtable<Character, String> ligatureMap,
        Hashtable<Character, Character> diacriticMap, 
        Hashtable<Character, String> nonStripMap,
        RTrieTree removeSTree, boolean detailsFlag, boolean mutateFlag) 
        throws SQLException
    {
        Vector<LexItem> outs = new Vector<LexItem>();
        int index = 0;
        // go through all elements for the ins
        for(int i = 0; i < ins.size(); i++)
        {
            LexItem in = ins.elementAt(i);
            Vector<LexItem> tempOuts = new Vector<LexItem>();
            switch(step)
            {
                case 0:         // -f:q7
                    tempOuts = ToUnicodeCoreNorm.Mutate(in, symbolMap,
                        unicodeMap, ligatureMap, diacriticMap,
                        detailsFlag, mutateFlag);
                    break;
                case 1:         // -f:g
                    tempOuts = ToRemoveGenitive.Mutate(in, detailsFlag, 
                        mutateFlag);
                    break;
                case 2:         // -f:rs
                    tempOuts = ToRemoveS.Mutate(in, removeSTree,
                        detailsFlag, mutateFlag);
                    break;
                case 3:         // -f:o
                    tempOuts = ToReplacePunctuationWithSpace.Mutate(in, 
                        detailsFlag, mutateFlag);
                    break;
                case 4:         // -f:t
                    tempOuts = ToStripStopWords.Mutate(in, stopWords,
                        detailsFlag, mutateFlag);
                    break;
                case 5:         // -f:l
                    tempOuts = ToLowerCase.Mutate(in, detailsFlag, mutateFlag);
                    break;
                case 6:         // -f:B
                    tempOuts = ToUninflectWords.Mutate(in, maxTerm, conn, trie, 
                        detailsFlag, mutateFlag);
                    break;
                case 7:         // -f:C
                    tempOuts = ToCanonicalize.Mutate(in, conn, detailsFlag, 
                        mutateFlag);
                    break;
                case 8:         // -f:q8
                    tempOuts = ToStripMapUnicode.Mutate(in, 
                        nonStripMap, detailsFlag, mutateFlag);
                    break;
                case 9:         // -f:w
                    tempOuts = ToSortWordsByOrder.Mutate(in, detailsFlag, 
                        mutateFlag);
                    break;
            }
            outs.addAll(tempOuts);
            // only take the first output for Canonicalize
            if(step == 7)
            {
                break;
            }
        }
        return outs;
    }
    // data members
    private final static int LUI_NORM_STEPS = 10;
}