package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.util.*;
import java.sql.*;
import java.io.*;
import gov.nih.nlm.nls.lvg.CmdLineSyntax.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Util.*;
import gov.nih.nlm.nls.lvg.Flows.*;
/*****************************************************************************
* This class is the command line program for WordInd.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class wordInd extends SystemOption
{
    // public constructor
    public wordInd()
    {
        super();
        Init();
    }
    // public method
    /**
    * The flow chart for user-interface are:
    * main() -> WordInd.ExecuteCommands: to setup flags
    * main() -> WordInd.GetVariants() -> LineHandler()
    */
    public static void main(String[] args)
    {
        // form option String if there are arguments
        Option io = Option.GetOptonByArgs(args);
        // define the system option flag & argument
        wordInd wordIndObj = new wordInd();
        Out out = new Out();
        // execute command according to option & argument
        if(SystemOption.CheckSyntax(io, wordIndObj.GetOption(), false, true) 
            == true)
        {
            // decode input option to form options
            wordIndObj.ExecuteCommands(io, wordIndObj.GetOption(), out);
            // read in input text and get the Words for it
            if(wordIndObj.GetRunFlag() == true)
            {
                wordIndObj.GetVariants(out);
            }
        }
        else
        {
            WordIndHelp.WordIndHelp(wordIndObj.GetOutWriter(), 
                wordIndObj.GetFileOutput(), out);
        }
        // close files & database
        wordIndObj.Close();
    }
    /**
    * read data from user's line inputs
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
        // perform WordInd operation
        try
        {
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
        if(CheckOption(nameItem, "-c") == true)
        {
            reserveCaseFlag_ = true;
        }
        else if(CheckOption(nameItem, "-F:INT") == true)
        {
            Integer fieldNum = new Integer(nameItem.GetOptionArgument());
            outputFieldList_.addElement(fieldNum);
        }
        else if(CheckOption(nameItem, "-h") == true)
        {
            WordIndHelp.WordIndHelp(outWriter_, fileOutput_, out);
            runFlag_ = false;
        }
        else if(CheckOption(nameItem, "-hs") == true)
        {
            systemOption.PrintOptionHierachy();        // not UTF-8
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
                    outWriter_ = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(outFile), "UTF-8"));
                    fileOutput_ = true;
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
        else if(CheckOption(nameItem, "-v") == true)
        {
            try
            {
                String releaseStr = "wordInd." + GlobalBehavior.YEAR;
                out.Println(outWriter_, releaseStr, fileOutput_, false);
            }
            catch (IOException e) { }
            runFlag_ = false;
        }
    }
    protected void DefineFlag()
    {
        // define all option flags & arguments by giving a option string
        String flagStr = 
            "-c -F:INT -h -hs -i:STR -n -o:STR -p -s:STR -t:INT -v";
        
        // init the system option
        systemOption_ = new Option(flagStr);
        // Add the full name for flags
        systemOption_.SetFlagFullName("-c", "Reserve_Cases");
        systemOption_.SetFlagFullName("-F", "Copy_Field_To_Outputs");
        systemOption_.SetFlagFullName("-h", "Help");
        systemOption_.SetFlagFullName("-hs", "Hierarchy_Struture");
        systemOption_.SetFlagFullName("-i", "Input_File");
        systemOption_.SetFlagFullName("-n", "No_Output");
        systemOption_.SetFlagFullName("-o", "Output_File");
        systemOption_.SetFlagFullName("-p", "Show_Prompt");
        systemOption_.SetFlagFullName("-s", "Field_Separator");
        systemOption_.SetFlagFullName("-t", "Term_Field");
        systemOption_.SetFlagFullName("-v", "Version");
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
        }
        catch (Exception e)
        {
            System.err.println("Err@wordInd.Close(): " + e.toString());
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
        // Get WordInd result by going through flow:c
        // get the term from appropriate field
        String inTerm = In.GetField(line, separator_, termFieldNum_);
        outputTerm_ = In.GetOutTerm(line, separator_, outputFieldList_);
        if(reserveCaseFlag_ == false)
        {
            inTerm = inTerm.toLowerCase();
        }
        LexItem in = new LexItem(inTerm);
        Vector<LexItem> result = ToTokenize.Mutate(in, false, false);
        
        // print results
        if(result.size() == 0)
        {
            if(noOutputFlag_ == true)
            {
                out.Println(outWriter_, 
                    (outputTerm_ + NO_OUTPUT_STR), fileOutput_, false);
            }
        }
        else
        {
            for(int i = 0; i < result.size(); i++)
            {
                LexItem temp = result.elementAt(i);
                out.Println(outWriter_, 
                    (outputTerm_ + temp.GetTargetTerm()), fileOutput_, false);
            }
        }
        return true;
    }
    private boolean GetRunFlag()
    {
        return runFlag_;
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
    // data member
    private final static String NO_OUTPUT_STR = "-No Output-";
    private boolean runFlag_ = true;         // flag for running WordInd
    private boolean noOutputFlag_ = false;   // flag for return no output
    private boolean promptFlag_ = false;     // flag for display prompt
    private boolean reserveCaseFlag_ = false; // flag for reserve case
    private boolean fileOutput_ = false;        // flag for file output
    private int termFieldNum_ = 1;
    private String separator_ = GlobalBehavior.FS_STR;
    private String outputTerm_ = new String();
    private String promptStr_ = null;
    private Vector<Integer> outputFieldList_ = new Vector<Integer>();
    private BufferedReader inReader_ = null;            // infile buffer
    private BufferedWriter outWriter_ = null;        // outfile buffer
}
