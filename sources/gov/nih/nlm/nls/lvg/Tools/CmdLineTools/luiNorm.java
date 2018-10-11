package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.util.*;
import java.sql.*;
import java.io.*;
import gov.nih.nlm.nls.lvg.CmdLineSyntax.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Util.*;
import gov.nih.nlm.nls.lvg.Flows.*;
import gov.nih.nlm.nls.lvg.Db.*;
import gov.nih.nlm.nls.lvg.Trie.*;
/*****************************************************************************
* This class is the command line program for LuiNorm.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class luiNorm extends SystemOption
{
    // public constructor
    public luiNorm()
    {
        super();
        Init();
    }
    // public method
    /** The flow chart for user-interface are:
    * main() -> LuiNorm.ExecuteCommands: to setup flags
    * main() -> LuiNorm.GetVariants() -> LineHandler()
    */
    public static void main(String[] args)
    {
        // form option String if there are arguments
        Option io = Option.GetOptonByArgs(args);
        // define the system option flag & argument
        luiNorm luiNormObj = new luiNorm();
        Out out = new Out();
        // execute command according to option & argument
        if(SystemOption.CheckSyntax(io, luiNormObj.GetOption(), false, true) 
            == true)
        {
            // decode input option to form options
            luiNormObj.ExecuteCommands(io, luiNormObj.GetOption(), out);
            // get config file from environment variable
            luiNormObj.GetConfig();
            // Init vars by config
            luiNormObj.InitByConfig(luiNormObj, out);
        }
        else
        {
            LuiNormHelp.LuiNormHelp(luiNormObj.GetOutWriter(), 
                luiNormObj.GetFileOutput(), out);
        }
        // close files & database
        luiNormObj.Close();    
    }
    /**
    * Read data from user's line inputs
    */
    public void GetVariants(Out out)
    {
        // decide the input source: from screen or file
        if(inReader_ == null)
        {
            try
            {
                inReader_ = new BufferedReader(new InputStreamReader(
                    System.in, "UTF-8"));
            }
            catch (IOException e)
            {
                System.err.println("**Error: problem of reading std-in");
            }
        }
        int minTermLen = Integer.parseInt(
            conf_.GetConfiguration(Configuration.MIN_TERM_LENGTH));
        String lvgDir = conf_.GetConfiguration(Configuration.LVG_DIR);
        int minTrieStemLength = Integer.parseInt(
            conf_.GetConfiguration(Configuration.DIR_TRIE_STEM_LENGTH));
        // perform LuiNorm operation
        try
        {
            conn_ = DbBase.OpenConnection(conf_);
            ramTrie_ = new RamTrie(true, minTermLen, lvgDir, minTrieStemLength);
            while(true) // Loop forever - to go into prompt
            {
                if(promptFlag_ == true)
                {
                    GetPrompt(out);     // Display a prompt to the user
                }
                // Read a line from the input source
                if(LineHandler(inReader_.readLine(), out) == false)
                {
                    break;
                }
            }
            Close();    // close files & database
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    // protected methods
    protected void ExecuteCommand(OptionItem optionItem, Option systemOption,
        Out out)
    {
        OptionItem nameItem =
            OptionUtility.GetItemByName(optionItem, systemOption, false);
        Vector<OptionItem> systemItems = systemOption.GetOptionItems();
        if(CheckOption(nameItem, "-ci") == true)
        {
            try
            {
                // get config file from environment variable
                boolean useClassPath = false;
                String configFile = configFile_;
                if(configFile == null)
                {
                    useClassPath = true;
                    configFile = "data.config.lvg";
                }
                Configuration conf = conf_;
                if(conf == null)
                {
                    conf = new Configuration(configFile, useClassPath);
                }
                out.Println(outWriter_, conf.GetInformation(), fileOutput_,
                    false);
            }
            catch (IOException e) { }
            runFlag_ = false;
        }
        else if(CheckOption(nameItem, "-d") == true)
        {
            detailsFlag_ = true;
        }
        else if(CheckOption(nameItem, "-h") == true)
        {
            LuiNormHelp.LuiNormHelp(outWriter_, fileOutput_, out);
            runFlag_ = false;
        }
        else if(CheckOption(nameItem, "-hs") == true)
        {
            systemOption.PrintOptionHierachy();        //not UTF-8
            runFlag_ = false;
        }
        else if(CheckOption(nameItem, "-i:STR") == true)
        {
            String inFile = nameItem.GetOptionArgument();
            if(inFile != null)
            {
                try
                {
                    inReader_ = new BufferedReader(new InputStreamReader(
                        new FileInputStream(inFile), "UTF-8"));
                }
                catch (IOException e)
                {
                    runFlag_ = false;
                    System.err.println(
                        "**Error: problem of opening/reading file " + inFile);
                }
            }
        }
        else if(CheckOption(nameItem, "-n") == true)
        {
            noOutputFlag_ = true;
        }
        else if(CheckOption(nameItem, "-o:STR") == true)
        {
            String outFile = nameItem.GetOptionArgument();
            if(outFile != null)
            {
                try
                {
                    fileOutput_ = true;
                    outWriter_ = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(outFile), "UTF-8"));
                }
                catch (IOException e)
                {
                    runFlag_ = false;
                    System.err.println(
                        "**Error: problem of opening/writing file " + outFile);
                }
            }
        }
        else if(CheckOption(nameItem, "-p") == true)
        {
            promptFlag_ = true;
        }
        else if(CheckOption(nameItem, "-s:STR") == true)
        {
            separator_ = nameItem.GetOptionArgument();
            // if sub by tab
            if(separator_.equals("\\t"))
            {
                separator_ = new Character((char)(9)).toString();    // tab
            }
        }
        else if(CheckOption(nameItem, "-t:INT") == true)
        {
            termFieldNum_ = Integer.parseInt(nameItem.GetOptionArgument());
        }
        else if(CheckOption(nameItem, "-ti") == true)
        {
            filterInputFlag_ = true;
        }
        else if(CheckOption(nameItem, "-v") == true)
        {
            try
            {
                String releaseStr = "luiNorm." + GlobalBehavior.YEAR;
                out.Println(outWriter_, releaseStr, fileOutput_, false);
            }
            catch (IOException e) { }
            runFlag_ = false;
        }
        else if(CheckOption(nameItem, "-x:STR") == true)
        {
            configFile_ = nameItem.GetOptionArgument();
        }
    }
    protected void DefineFlag()
    {
        // define all option flags & arguments by giving a option string
        String flagStr = "-ci -d -h -hs -i:STR -n -o:STR -p -s:STR -t:INT -ti -v -x:STR";
        
        // init the system option
        systemOption_ = new Option(flagStr);
        // Add the full name for flags
        systemOption_.SetFlagFullName("-ci", "Print_Config_Info");
        systemOption_.SetFlagFullName("-d", "Print_Details_Operations");
        systemOption_.SetFlagFullName("-h", "Help");
        systemOption_.SetFlagFullName("-hs", "Hierarchy_Struture");
        systemOption_.SetFlagFullName("-i", "Input_File");
        systemOption_.SetFlagFullName("-n", "No_Output");
        systemOption_.SetFlagFullName("-o", "Output_File");
        systemOption_.SetFlagFullName("-p", "Show_Prompt");
        systemOption_.SetFlagFullName("-s", "Field_Separator");
        systemOption_.SetFlagFullName("-t", "Term_Field");
        systemOption_.SetFlagFullName("-ti", "Display_Filtered_Input");
        systemOption_.SetFlagFullName("-v", "Version");
        systemOption_.SetFlagFullName("-x", "Load_Configuration_file");
    }
    // private methods
    private void Close()
    {
        try
        {
            if(outWriter_ != null)
            {
                outWriter_.close();
            }
            if(inReader_ != null)
            {
                inReader_.close();
            }
            if(conn_ != null)
            {
                DbBase.CloseConnection(conn_, conf_);  // close db connection
            }
        }
        catch (Exception e)
        {
            System.err.println("Err@luiNorm.Close(): " + e.toString());
        }
    }
    private void GetPrompt(Out out) throws IOException
    {
        out.Println(outWriter_, promptStr_, fileOutput_, false);
    }
    private boolean LineHandler(String line, Out out)
        throws SQLException, IOException
    {
        // check input for quitiing
        if(line == null)
        {
            return false;
        }
        // Get LuiNorm result by going through flow:g:p:t:l:Bn:C:w
        // get the term from appropriate field
        String inTerm = In.GetField(line, separator_, termFieldNum_);
        LexItem in = new LexItem(inTerm);
        Vector<LexItem> result = ToLuiNormalize.Mutate(in, maxTerm_, stopWords_,
            conn_, ramTrie_, symbolMap_, unicodeMap_, ligatureMap_, 
            diacriticMap_, nonStripMap_, removeSTree_, detailsFlag_, false);
        // print results
        // check no output flag
        if((noOutputFlag_ == true) // no output flag
        && ((result.size() == 0)    // no output LexItem
         || ((result.size() == 1)    // one output lexItem with empty string
          && (result.elementAt(0).GetTargetTerm().length() == 0))))
        {
            String outStr = line;
            if(filterInputFlag_ == true)
            {
                outStr = in.GetOriginalTerm();
            }
            out.Println(outWriter_, (outStr + separator_ + noOutputStr_),
                fileOutput_, false);
        }
        else
        {
            for(int i = 0; i < result.size(); i++)
            {
                LexItem temp = result.elementAt(i);
                String outStr = line;
                if(filterInputFlag_ == true)
                {
                    outStr = temp.GetOriginalTerm();
                }
                out.Println(outWriter_, (outStr + separator_
                    + temp.GetTargetTerm()), fileOutput_, false);
                // print details
                if(detailsFlag_ == true)
                {
                    out.Println(outWriter_, temp.GetDetailInformation(), 
                        fileOutput_, false);
                }
            }
        }
        return true;
    }
    private boolean GetFileOutput()
    {
        return fileOutput_;
    }
    private BufferedWriter GetOutWriter()
    {
        return outWriter_;
    }
    private void Init()
    {
        // set default promt
        if(Platform.IsWindow() == true)
        {
            promptStr_ =
            "- Please input a term (type \"Ctl-z\" then \"Enter\" to quit) >";    
        }
        else
        {
            promptStr_ = "- Please input a term (type \"Ctl-d\" to quit) >";
        }
        try
        {
            outWriter_ = new BufferedWriter(new OutputStreamWriter(
                System.out, "UTF-8"));
        }
        catch (IOException e)
        {
            System.err.println("**Error: problem of opening Std-out.");
        }
    }
    private void GetConfig()
    {
        boolean useClassPath = false;
        if(configFile_ == null)
        {
            useClassPath = true;
            configFile_ = "data.config.lvg";
        }
        // read in configuration file
        if(conf_ == null)
        {
            conf_ = new Configuration(configFile_, useClassPath);
        }
    }
    private void InitByConfig(luiNorm luiNormObj, Out out)
    {
        // check config size 
        if(conf_.GetSize() <= 0)
        {
            runFlag_ = false;
        }
        else
        {
            if(maxTerm_ == -1)
            {
                maxTerm_ = Integer.parseInt(
                    conf_.GetConfiguration(Configuration.MAX_UNINFLS));
            }
            if(conf_.GetConfiguration(Configuration.NO_OUTPUT) != null)
            {
                noOutputStr_ = conf_.GetConfiguration(Configuration.NO_OUTPUT);
            }
            if(stopWords_ == null)
            {
                stopWords_ = ToStripStopWords.GetStopWordsFromFile(conf_);
            }
            if(symbolMap_ == null)
            {
                symbolMap_ = ToMapSymbolToAscii.GetSymbolMapFromFile(conf_);
            }
            if(unicodeMap_ == null)
            {
                unicodeMap_ = ToMapUnicodeToAscii.GetUnicodeMapFromFile(conf_);
            }
            if(diacriticMap_ == null)
            {
                diacriticMap_ 
                    = ToStripDiacritics.GetDiacriticMapFromFile(conf_);
            }
            if(ligatureMap_ == null)
            {
                ligatureMap_ = ToSplitLigatures.GetLigatureMapFromFile(conf_);
            }
            if(nonStripMap_ == null)
            {
                nonStripMap_ = ToStripMapUnicode.GetNonStripMapFromFile(conf_);
            }
            if(removeSTree_ == null)
            {
                removeSTree_ = ToRemoveS.GetRTrieTreeFromFile(conf_);
            }
            if(conf_.GetConfiguration(Configuration.LVG_PROMPT).equals(
                "DEFAULT") == false)
            {
                promptStr_ = conf_.GetConfiguration(Configuration.LVG_PROMPT);
            }
            // read in input text and get the Norm for it
            if(runFlag_ == true)
            {
                luiNormObj.GetVariants(out);
            }
        }
    }
    // data member
    private final static String NO_OUTPUT_STR = "-No Output-";
    private boolean detailsFlag_ = false;    // flag for details print
    private boolean runFlag_ = true;         // flag for running Norm
    private boolean noOutputFlag_ = false;   // flag for return no output
    private boolean promptFlag_ = false;     // flag for display prompt
    private boolean filterInputFlag_ = false;  // flag for keep org
    private boolean fileOutput_ = false;        // flag for file output
    private int maxTerm_ = -1;
    private int termFieldNum_ = 1;
    private String noOutputStr_ = NO_OUTPUT_STR;
    private String separator_ = GlobalBehavior.FS_STR;
    private String configFile_ = null;
    private String promptStr_ = null;
    private Vector<String> stopWords_ = null;
    private Hashtable<Character, String> symbolMap_ = null;
    private Hashtable<Character, String> unicodeMap_ = null;
    private Hashtable<Character, Character> diacriticMap_ = null; 
        // diacritic for q
    private Hashtable<Character, String> ligatureMap_ = null;   
        // ligature for q2
    private Hashtable<Character, String> nonStripMap_ = null;
    private RTrieTree removeSTree_ = null;  // removeS tree, rs
    private Connection conn_ = null;             // database connection
    private RamTrie ramTrie_ = null;         // persistent Trie
    private Configuration conf_ = null;
    private BufferedReader inReader_ = null;         // infile buffer
    private BufferedWriter outWriter_ = null;        // outfile buffer
}
