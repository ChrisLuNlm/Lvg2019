package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.util.*;
import java.io.*;
import gov.nih.nlm.nls.lvg.CmdLineSyntax.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Util.*;
import gov.nih.nlm.nls.lvg.Flows.*;
import gov.nih.nlm.nls.lvg.Api.*;
/*****************************************************************************
* This class is the command line program for ToAscii.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class toAscii extends SystemOption
{
    // public constructor
    public toAscii()
    {
        super();
        Init();
    }
    // public method
    // The flow chart for user-interface are:
    // main() -> ToAscii.ExecuteCommands: to setup flags
    // main() -> ToAscii.GetVariants() -> LineHandler()
    public static void main(String[] args)
    {
        // form optin String if there are arguments
        Option io = Option.GetOptonByArgs(args);
        // define the system option flag & argument
        toAscii toAsciiObj = new toAscii();
        Out out = new Out();
        // execute command according to option & argument
        if(SystemOption.CheckSyntax(io, toAsciiObj.GetOption(), false, true) 
            == true)
        {
            // decode input option to form options
            toAsciiObj.ExecuteCommands(io, toAsciiObj.GetOption(), out);
            // get config file from environment variable
            toAsciiObj.GetConfig();
            // Init vars by config
            toAsciiObj.InitByConfig(toAsciiObj, out);
        }
        else
        {
            ToAsciiHelp.ToAsciiHelp(toAsciiObj.GetOutWriter(), 
                toAsciiObj.GetFileOutput(), out);
        }
        // close files
        toAsciiObj.Close();
    }
    // read data from user's line inputs
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
        // perform ToAscii operation
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
            ToAsciiHelp.ToAsciiHelp(outWriter_, fileOutput_, out);
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
        else if(CheckOption(nameItem, "-pio") == true)
        {
            preserveIoFlag_ = true;
        }
        else if(CheckOption(nameItem, "-v") == true)
        {
            try
            {
                String releaseStr = "toAscii." + GlobalBehavior.YEAR;
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
        String flagStr = "-ci -d -h -hs -i:STR -n -o:STR -p -pio -v -x:STR";
        
        // init the system option
        systemOption_ = new Option(flagStr);
        // Add the full name for flags
        systemOption_.SetFlagFullName("-ci", "Print_Config_Info");
        systemOption_.SetFlagFullName("-d", "Print_Operation_Details");
        systemOption_.SetFlagFullName("-h", "Help");
        systemOption_.SetFlagFullName("-hs", "Hierarchy_Struture");
        systemOption_.SetFlagFullName("-i", "Input_File");
        systemOption_.SetFlagFullName("-n", "No_Output");
        systemOption_.SetFlagFullName("-o", "Output_File");
        systemOption_.SetFlagFullName("-p", "Show_Prompt");
        systemOption_.SetFlagFullName("-pio", "Preserve_IO");
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
        }
        catch (Exception e)
        {
            System.err.println("Err@toAscii.Close(): " + e.toString());
        }
    }
    private void GetPrompt(Out out) throws IOException
    {
        out.Println(outWriter_, promptStr_, fileOutput_, false);
    }
    private boolean LineHandler(String line, Out out) throws IOException
    {
        // check input for quitiing
        if(line == null)    
        {
            return false;
        }
        // Get ToAscii result by going through flow -f:q7:q8
        // get the term from appropriate field
        LexItem inLexItem = new LexItem(line);
        LexItem outLexItem = toAsciiApi_.Mutate(inLexItem, detailsFlag_);
        
        // print results
        // check no output flag
        if((noOutputFlag_ == true)    // no output flag
        && (outLexItem.GetTargetTerm().length() == 0))    // empty string
        {
            if(preserveIoFlag_ == true)
            {
                out.Println(outWriter_, "Input: " + line, fileOutput_, false);
                out.Println(outWriter_, "Ascii: " + noOutputStr_, fileOutput_, 
                    false);
            }
            else
            {
                out.Println(outWriter_, noOutputStr_, fileOutput_, false);
            }
        }
        else
        {
            if(preserveIoFlag_ == true)
            {
                out.Println(outWriter_, "Input: " + line, fileOutput_, false);
                out.Println(outWriter_, "Ascii: " + outLexItem.GetTargetTerm(), 
                    fileOutput_, false);
            }
            else
            {
                out.Println(outWriter_, outLexItem.GetTargetTerm(), fileOutput_,
                    false);
            }
            // print details
            if(detailsFlag_ == true)
            {
                out.Println(outWriter_, outLexItem.GetDetailInformation(), 
                    fileOutput_, false);
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
    private void InitByConfig(toAscii toAsciiObj, Out out)
    {
        // check config size
        if(conf_.GetSize() <= 0)
        {
            runFlag_ = false;
        }
        else
        {
            // init toASciiApi object
            toAsciiApi_ = new ToAsciiApi(conf_);
            if(conf_.GetConfiguration(Configuration.NO_OUTPUT) != null)
            {
                noOutputStr_ = conf_.GetConfiguration(Configuration.NO_OUTPUT);
            }
            if(conf_.GetConfiguration(Configuration.LVG_PROMPT).equals(
                "DEFAULT") == false)
            {
                 promptStr_ = conf_.GetConfiguration(Configuration.LVG_PROMPT);
            }
            // read in input text and get the ToAscii for it
            if(runFlag_ == true)
            {
                toAsciiObj.GetVariants(out);
            }
        }
    }
    // data member
    private final static String NO_OUTPUT_STR = "-No Output-";
    private boolean detailsFlag_ = false;    // flag for details print
    private boolean runFlag_ = true;         // flag for running ToAscii
    private boolean noOutputFlag_ = false;   // flag for return no output
    private boolean promptFlag_ = false;     // flag for display prompt
    private boolean preserveIoFlag_ = false;  // flag for preserveIo
    private boolean fileOutput_ = false;        // flag for file output
    private String noOutputStr_ = NO_OUTPUT_STR;
    private String configFile_ = null;
    private String promptStr_ = null;
    private Configuration conf_ = null;
    private ToAsciiApi toAsciiApi_ = null;
    private BufferedReader inReader_ = null;         // infile buffer
    private BufferedWriter outWriter_ = null;        // outfile buffer
}
