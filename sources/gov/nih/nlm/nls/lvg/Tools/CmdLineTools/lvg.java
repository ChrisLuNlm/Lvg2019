package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.util.*;
import java.io.*;
import gov.nih.nlm.nls.lvg.Api.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.CmdLineSyntax.*;
import gov.nih.nlm.nls.lvg.Util.Out;
/*****************************************************************************
* This class is the sample program for testing Lvg command line API.
* This program use LvgCmdApi to perform a command line interface
* 
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class lvg
{
    /**
    * Test Driver: Command line interface 
    */
    public static void main(String[] args)
    {
        // PreProscess: get option form input args
        String optionStr = GetOptionStr(args);
        // define the system option flag & argument
        LvgCmdApi lvgCmdApi = new LvgCmdApi(optionStr);
        // Process: check the input option, if legal, process  the input term
        if(lvgCmdApi.IsLegalOption() == true)
        {
            ProcessTerm(lvgCmdApi);
        }
        else
        {
            lvgCmdApi.PrintLvgHelp();
        }
        // Post Process: CleanUp
        lvgCmdApi.CleanUp();
    }
    // private methods
    // read data from user's line inputs
    private static void ProcessTerm(LvgCmdApi lvg)
    {
        try
        {
            while(true) // Loop forever
            {
                // execute command according to option & argument
                if(lvg.ProcessLine() == false)
                {
                    break;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private static String GetOptionStr(String[] args)
    {
        // define the default option for showing help menu
        String optionStr = "-h";
        // capture option form args
        if(args.length > 0)
        {
            optionStr = "";
            for(int i = 0; i < args.length; i++)
            {
                if(i == 0)
                {
                    optionStr = args[i];
                }
                else
                {
                    optionStr += (" " + args[i]);
                }
            }
        }
        return optionStr;
    }
}
