package gov.nih.nlm.nls.lvg.Db;
import java.sql.*;
import java.util.*;
import gov.nih.nlm.nls.lvg.Lib.*;
/*****************************************************************************
* This class provides high level interfaces to Synonym table in LVG database.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see        SynonymRecord
* @see <a href="../../../../../../../designDoc/UDF/database/synonymTable.html">
* Desgin Document </a>
*
* @version    V-2019
****************************************************************************/
public class DbSynonym 
{
    /**
    * Get all synonym records for a specified term from LVG database.
    *
    * @param  inStr  key form (no punctuation lower case)
    * @param  conn  database connection
    * @param  srcType  source type
    *
    * @return  all synonym records for a speficied term, inStr
    *
    * @exception  SQLException if there is a database error happens
    */
    public static Vector<SynonymRecord> GetSynonymsBySource(String inStr, 
        Connection conn, int srcType) throws SQLException
    {
        Vector<SynonymRecord> synonyms = new Vector<SynonymRecord>();
        // get data from itable inflection
        PreparedStatement ps 
            = GetPreparedStatementBySource(inStr, conn, srcType);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            SynonymRecord synonymRecord = new SynonymRecord();
            synonymRecord.SetKeyFormNpLc(rs.getString(1));  // keyFormNpLc
            synonymRecord.SetKeyForm(rs.getString(2));      // keyForm
            synonymRecord.SetSynonym(rs.getString(3));      // aSynonym
            synonymRecord.SetCat1(rs.getInt(4));            // cat1
            synonymRecord.SetCat2(rs.getInt(5));            // cat2
            synonymRecord.SetSSource(rs.getString(6));      // sSource
            synonyms.addElement(synonymRecord);
        }
        // Clean up
        rs.close();
        ps.close();
        // sort
        SynonymComparator<SynonymRecord> sc 
            = new SynonymComparator<SynonymRecord>();
        Collections.sort(synonyms, sc);
        return (synonyms);
    }
    /**
    * Get all synonym records for a specified term from LVG database.
    *
    * @param  inStr  key form (no punctuation lower case)
    * @param  conn  database connection
    *
    * @return  all synonym records for a speficied term, inStr
    *
    * @exception  SQLException if there is a database error happens
    */
    public static Vector<SynonymRecord> GetSynonyms(String inStr, 
        Connection conn) throws SQLException
    {
        Vector<SynonymRecord> synonyms = GetSynonymsBySource(inStr, conn, 
            OutputFilter.S_SRC_ALL);
        return synonyms;
    }
    public static PreparedStatement GetPreparedStatementBySource(String inStr,
        Connection conn, int srcType) throws SQLException
    {
        String query = "SELECT keyFormNpLc, keyForm, aSynonym, cat1, cat2, sSource FROM LexSynonym WHERE keyFormNpLc = ?"; 
        switch(srcType)
        {
            // CUI format: C1234567
            case OutputFilter.S_SRC_CUI:
                query += " AND (sSource LIKE 'C_______')";
                break;
            // EUI format: E1234567, the first digit will stay 0 for a long time
            case OutputFilter.S_SRC_EUI:
                query += " AND (sSource LIKE 'E0______')";
                break;
            case OutputFilter.S_SRC_NLP:
                query += " AND (sSource LIKE 'NLP_%')";
                break;
            case OutputFilter.S_SRC_CUI_EUI:
                query += " AND (sSource LIKE 'C_______' OR sSource LIKE 'E0______')";
                break;
            case OutputFilter.S_SRC_CUI_NLP:
                query += " AND (sSource LIKE 'C_______' OR sSource LIKE 'NLP_%')";
                break;
            case OutputFilter.S_SRC_EUI_NLP:
                query += " AND (sSource LIKE 'E0______' OR sSource LIKE 'NLP_%')";
                break;
            case OutputFilter.S_SRC_ALL:
            default:
                break;
        }
        // get the PreparedStatement
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, inStr);
        return ps;
    }
            
    /**
    * Test driver for this class.
    *
    * @param args arguments
    */
    public static void main (String[] args)
    {
        String testStr = "sensation";
        if(args.length == 1)
        {
            testStr = args[0];
        }
        System.out.println("--- TestStr:  " + testStr);
        // read in configuration file
        Configuration conf = new Configuration("data.config.lvg", true);
        // obtain a connection
        try
        {
            Connection conn = DbBase.OpenConnection(conf);
            if(conn != null)
            {
                // Get Synonyms
                Vector<SynonymRecord> synonymList = GetSynonyms(testStr, conn);
                System.out.println("----- Total Synonyms found: " +
                        synonymList.size());
                synonymList.stream()
                    .map(rec -> rec.ToString())
                    .forEach(str -> System.out.println(str));
                // Get Synonyms by source
                PrintSQueryResultBySrc(testStr, conn, OutputFilter.S_SRC_ALL);
                PrintSQueryResultBySrc(testStr, conn, OutputFilter.S_SRC_CUI);
                PrintSQueryResultBySrc(testStr, conn, OutputFilter.S_SRC_EUI);
                PrintSQueryResultBySrc(testStr, conn, OutputFilter.S_SRC_NLP);
                PrintSQueryResultBySrc(testStr, conn, OutputFilter.S_SRC_CUI_EUI);
                PrintSQueryResultBySrc(testStr, conn, OutputFilter.S_SRC_CUI_NLP);
                PrintSQueryResultBySrc(testStr, conn, OutputFilter.S_SRC_EUI_NLP);
                DbBase.CloseConnection(conn, conf);
            }
        }
        catch (SQLException sqle)
        {
            System.err.println(sqle.getMessage());
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    // private method
    private static void PrintSQueryResultBySrc(String inStr, Connection conn, 
        int sSource) throws SQLException
    {
        String srcStr = "ALL";
        switch(sSource)
        {
            case OutputFilter.S_SRC_CUI:
                srcStr = "CUI";
                break;
            case OutputFilter.S_SRC_EUI:
                srcStr = "EUI";
                break;
            case OutputFilter.S_SRC_NLP:
                srcStr = "NLP";
                break;
            case OutputFilter.S_SRC_CUI_EUI:
                srcStr = "CUI_EUI";
                break;
            case OutputFilter.S_SRC_CUI_NLP:
                srcStr = "CUI_NLP";
                break;
            case OutputFilter.S_SRC_EUI_NLP:
                srcStr = "EUI_NLP";
                break;
            case OutputFilter.S_SRC_ALL:
            default:
                srcStr = "ALL";
                break;
        }
           System.out.println("--- Synonyms By Source (" + srcStr +") ---");
        GetSynonymsBySource(inStr, conn, sSource).stream()
            .map(rec -> rec.ToString())
            .forEach(str -> System.out.println(str));
    }
}
