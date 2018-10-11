package gov.nih.nlm.nls.lvg.Tools.GuiTool.Global;
import java.io.*;
import java.util.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Api.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*****************************************************************************
* This class provides LVG Gui Tool global variables.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LvgGlobal
{
    // make it private so that no one can instantiate it
    private LvgGlobal()
    {
    }
    // public methods
    public static void SetConfig(String config)
    {
        config_ = new Configuration(config, false);    // configuration
        lvg_ = new LvgLexItemApi("", config);        // lvg api
    }
    public static Vector<LexItem> GetOutputLexItems()
    {
        return outputLexItems_;
    }
    public static Vector<String> GetOutputStrings()
    {
        return outputStrs_;
    }
    // IO manager: Lvg mutate and send output to file or screen
    public static void LvgMutate(int program)
    {
        if(program == MutatePanel.TO_ASCII_NO)    // toAscii
        {
            if(inputFromScreen_ == true)    // screen input, screen & file out
            {
                ToAscii(InputPanel.GetLexItem());
            }
            else if(outputToScreen_ == true)    // file input, screen output
            {
                ToAscii(inFile_);
            }
            else if(outputToScreen_ == false)    // file input and output
            {
                ToAscii(inFile_, outFile_);
            }
        }
        else    // lvg, norm. luiNorm, wordInd
        {
            if(inputFromScreen_ == true)    // screen input, screen & file out
            {
                LvgMutate(InputPanel.GetLexItem(), InputPanel.GetInLine());
            }
            else if(outputToScreen_ == true)    // file input, screen output
            {
                LvgMutate(inFile_);
            }
            else if(outputToScreen_ == false)    // file input and output
            {
                LvgMutate(inFile_, outFile_);
            }
        }
    }
    // toAscii: screen input and output
    private static void ToAscii(LexItem inLexItem)
    {
        // set toAscii to output Str
        LexItem outLexItem = toAscii_.Mutate(inLexItem, detailsFlag_);
        String asciiStr = outLexItem.GetTargetTerm();
        String ls = System.getProperty("line.separator").toString();
        if(detailsFlag_ == true)
        {
            asciiStr += (ls + outLexItem.GetDetailInformation());
        }
        ResetOutputStrings();
        // reset and update the output LexItems
        outputLexItems_.removeAllElements();
        outputLexItems_.add(outLexItem);
        // send outputs to screen or a file 
        if(LvgGlobal.outputToScreen_ == true)
        {
            // separate line by line and put into outputStrings Vector
            StringTokenizer buf = new StringTokenizer(asciiStr, ls);
            while(buf.hasMoreTokens() == true)
            {
                outputStrs_.addElement(buf.nextToken());
            }
            OutputPanel.UpdateResult();
        }
        else     // output to a file
        {
            outputStr_ = asciiStr;
            try
            {
                BufferedWriter out = new BufferedWriter(
                    new FileWriter(outFile_.getPath(), outAppendFlag_));
                out.write(outputStr_);
                out.close();
            }
            catch(Exception e) { }
            OutputPanel.SetOutputMessage();
        }
    }
    private static void ToAscii(File inFile)    // file in, screen out
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            String line = null;
            // read in line by line from a file
            boolean firstTimeRead = true;
            ResetOutputStrings();
            while((line = in.readLine()) != null)
            {
                // change output field for Norm, LuiNorm, and WordInd
                if(firstTimeRead == true)
                {
                    MutatePanel.SetNotLvg(line);
                    firstTimeRead = false;
                }
                LexItem inLexItem = new LexItem(line);
                LexItem outLexItem = toAscii_.Mutate(inLexItem, detailsFlag_);
                String asciiStr = outLexItem.GetTargetTerm();
                String ls = System.getProperty("line.separator").toString();
                if(detailsFlag_ == true)
                {
                    asciiStr += (ls + outLexItem.GetDetailInformation());
                }
                
                // separate line by line and put into outputStrings Vector
                StringTokenizer buf = new StringTokenizer(asciiStr, ls);
                while(buf.hasMoreTokens() == true)
                {
                    outputStrs_.addElement(buf.nextToken());
                }
                // send output to screen
                OutputPanel.UpdateResult();
            }
            in.close();
        }
        catch (Exception e)
        {
            System.err.println("** Error: incorrect input file, " +
                e.getMessage());
        }
    }
    private static void ToAscii(File inFile, File outFile)
    {
        try
        {
            String line = null;
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            BufferedWriter out = new BufferedWriter(
                new FileWriter(outFile_.getPath(), outAppendFlag_));
            // read in line by line from a file
            boolean firstTimeRead = true;
            ResetOutputStrings();
            while((line = in.readLine()) != null)
            {
                // change output field for Norm, LuiNorm, and WordInd
                if(firstTimeRead == true)
                {
                    MutatePanel.SetNotLvg(line);
                    firstTimeRead = false;
                }
                LexItem inLexItem = new LexItem(line);
                LexItem outLexItem = toAscii_.Mutate(inLexItem, detailsFlag_);
                String asciiStr = outLexItem.GetTargetTerm();
                String ls = System.getProperty("line.separator").toString();
                if(detailsFlag_ == true)
                {
                    asciiStr += (ls + outLexItem.GetDetailInformation());
                }
                else
                {
                    asciiStr += ls;
                }
                outputStr_ = asciiStr;
                out.write(outputStr_);
            }
            // close in & out files and send message
            in.close();
            out.close();
            OutputPanel.SetOutputMessage();
        }
        catch (Exception e)
        {
            System.err.println("** Error: incorrect input file, " +
                e.getMessage());
        }
    }
    // Lvg mutate for input from screen (single input)
    private static void LvgMutate(LexItem input, String inLine)
    {
        // reset and update the output LexItems 
        outputLexItems_.removeAllElements();
        outputLexItems_ = Process(input);
        // output filter & String
        UpdateOutputStrings(outputLexItems_, inLine, false);
        // send outputs to screen or a file 
        if(LvgGlobal.outputToScreen_ == true)
        {
            OutputPanel.UpdateResult();
        }
        else     // output to a file
        {
            try
            {
                BufferedWriter out = new BufferedWriter(
                    new FileWriter(outFile_.getPath(), outAppendFlag_));
                out.write(outputStr_);
                out.close();
            }
            catch(Exception e)
            {
            }
            OutputPanel.SetOutputMessage();
        }
    }
    // Lvg mutate for input from a file (multiple input) to the screen
    private static void LvgMutate(File inFile)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            String line = null;
            // read in line by line from a file
            boolean firstTimeRead = true;
            outputLexItems_.removeAllElements();
            ResetOutputStrings();
            while((line = in.readLine()) != null)
            {
                // change output field for Norm, LuiNorm, and WordInd
                if(firstTimeRead == true)
                {
                    MutatePanel.SetNotLvg(line);
                    firstTimeRead = false;
                }
                // set the input LexItem
                LexItem inLexItem = SetLexItem(line);
                // add result to output LexItems Vector
                Vector<LexItem> result = Process(inLexItem);
                UpdateOutputStrings(result, line, true);
                outputLexItems_.addAll(result);
                // send output to screen
                OutputPanel.UpdateResult();
            }
            in.close();
        }
        catch (Exception e)
        {
            System.err.println("** Error: incorrect input file, " + 
                e.getMessage());
        }
    }
    // Lvg mutate for input from a file (multiple input) to a file
    private static void LvgMutate(File inFile, File outFile)
    {
        try
        {
            String line = null;
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            BufferedWriter out = new BufferedWriter(
                new FileWriter(outFile_.getPath(), outAppendFlag_));
            // read in line by line from a file
            boolean firstTimeRead = true;
            outputLexItems_.removeAllElements();
            while((line = in.readLine()) != null)
            {
                // change output field for Norm, LuiNorm, and WordInd
                if(firstTimeRead == true)
                {
                    MutatePanel.SetNotLvg(line);
                    firstTimeRead = false;
                }
                LexItem inLexItem = SetLexItem(line);
                // add result to output LexItems Vector
                Vector<LexItem> result = Process(inLexItem);
                UpdateOutputStrings(result, line, true);
                //outputLexItems_.addAll(result);
                // send output to a file
                out.write(outputStr_);
                ResetOutputStrings();
            }
            // close in & out files and send message
            in.close();
            out.close();
            OutputPanel.SetOutputMessage();
        }
        catch (Exception e)
        {
            System.err.println("** Error: incorrect input file, " + 
                e.getMessage());
        }
    }
    // reset output for LexItems, Strings, and a String
    private static void ResetOutputs()
    {
        outputLexItems_.removeAllElements();
        outputStrs_.removeAllElements();
        outputStr_ = new String();
    }
    // Input filter otpion to setup an input LexItem
    private static LexItem SetLexItem(String inLine)
    {
        String separator = LvgGlobal.separator_;
        int termFieldNum = InputOptionDialog.GetTermFieldNum();
        String term =
            InputFilter.GetInputTerm(inLine, separator, termFieldNum);
        LexItem lexItem = new LexItem(term);
        // input filter for cat & infl
        int categoryFieldNum = InputOptionDialog.GetCategoryFieldNum();
        if(categoryFieldNum > 0)
        {
            long inCat = InputFilter.GetInputCategory(inLine, separator,
                categoryFieldNum);
            lexItem.SetSourceCategory(inCat);
        }
        else
        {
            lexItem.SetSourceCategory(Category.ALL_BIT_VALUE);
        }
        int inflectionFieldNum = InputOptionDialog.GetInflectionFieldNum();
        if(inflectionFieldNum > 0)
        {
            long inInfl = InputFilter.GetInputInflection(inLine, separator,
                inflectionFieldNum);
            lexItem.SetSourceInflection(inInfl);
        }
        else
        {
            lexItem.SetSourceInflection(Inflection.ALL_BIT_VALUE);
        }
        return lexItem;
    }
    // update cmd string based on the flowList_ and optionList_
    public static void UpdateCmdStr()
    {
        // clean up and set cmd str
        cmd_.UpdateCmdStr();
        MutatePanel.SetCommand(cmd_.GetCmdStr());
    }
    // process the input LexItem, one at a time
    private static Vector<LexItem> Process(LexItem inLexItem)
    {
        Vector<LexItem> outputs = new Vector<LexItem>();
        // setup lvg
        lvg_.SetOption(cmd_.GetCmdStr());
        // process Mutate
        try
        {
            outputs = lvg_.ProcessLexItem(inLexItem);
        }
        catch (Exception e) 
        {
            System.err.println("** Error: " + e.toString());
        }
        return outputs;
    }
    // based on a mutate for one input LexItem
    private static void ResetOutputStrings()
    {
        outputStr_ = new String();
        outputStrs_.removeAllElements();
    }
    public static void UpdateOutputStrings(Vector<LexItem> output, 
        String inLine, boolean append)
    {
        if(append == false)
        {
            ResetOutputStrings();
        }
        String curOutputStr = OutputFilter.ExecuteOutputFilter(output,
            mutateFlag_, detailsFlag_, separator_, inLine, outputOption_);
        outputStr_ += curOutputStr;
        // separate line by line and put into outputStrings Vector
        String ls = System.getProperty("line.separator").toString();
        StringTokenizer buf = new StringTokenizer(curOutputStr, ls);
        while(buf.hasMoreTokens() == true)
        {
            outputStrs_.addElement(buf.nextToken());
        }
    }
    // public define varaiable
    // Lvg command
    public static LvgCommand cmd_ = new LvgCommand();      // cmd to CmdStr
    // Lvg results
    private static Vector<LexItem> outputLexItems_ = new Vector<LexItem>();
    private static Vector<String> outputStrs_ = new Vector<String>();    // screen output 
    private static String outputStr_ = new String(); // file output in String
    // Configuration
    public static Configuration config_ = 
        new Configuration("data.config.lvg", true);    // conf
    public static LvgLexItemApi lvg_ = new LvgLexItemApi();    // lvg api
    public static ToAsciiApi toAscii_ = new ToAsciiApi(config_); // toAScii Api
    // flag for input and output
    public static boolean inputFromScreen_ = true;
    public static boolean outputToScreen_ = true;
    // default file location
    private static String curDir_ = System.getProperty("user.dir");
    public static File inFile_ = new File(curDir_ + "/in.data");
    public static File outFile_ = new File(curDir_ + "/out.data");
    public static File confFile_ = new File(curDir_);
    public static boolean outAppendFlag_ = false;
    // lvg options
    // the api use Mutate method, which does not take care of output options
    public static LvgOutputOption outputOption_ = new LvgOutputOption();
    // mutate options
    public static boolean mutateFlag_ = false;
    public static boolean detailsFlag_ = false;
    public static String separator_ = GlobalBehavior.FS_STR;
    // input option & flow specific options are local vars.
}
