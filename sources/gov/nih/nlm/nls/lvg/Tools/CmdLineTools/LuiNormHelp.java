package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.io.*;
import gov.nih.nlm.nls.lvg.Util.*;
/*****************************************************************************
* This class prints out help menu of LuiNorm.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LuiNormHelp
{
    // public constructor
    // protected method
    protected static void LuiNormHelp(BufferedWriter bw, boolean fileOutput,
        Out out)
    {
        HelpMenu helpMenu = new HelpMenu(bw, fileOutput, out);
        // print out the usage
        helpMenu.Println("");
        helpMenu.Println("Synopsis:");
        helpMenu.Println("  LuiNorm [options]");
        helpMenu.Println("");
        helpMenu.Println("Description:");
        helpMenu.Println("  Normalize and canonicalize text.  That is the lvg flow options -f:q7:g:rs:o:t:l:B:C:q8:w.");
        helpMenu.Println("");
        helpMenu.Println("Options:");
        helpMenu.Println("  -ci     Print configuration information.");
        helpMenu.Println("  -d      Print details of LuiNorm operations.");
        helpMenu.Println("  -h      Print program help information (this is it).");
        helpMenu.Println("  -hs     Print option's hierarchy structure."); 
        helpMenu.Println("  -i:STR  Specify input file name.  The default is screen input.");
        helpMenu.Println("  -n      Return a \"-No Output-\" message when an input produces no output.");
        helpMenu.Println("  -o:STR  Specify output file name.  The default is screen output.");
        helpMenu.Println("  -p      Show the prompt. The default is no prompt.");
        helpMenu.Println("  -s:STR  Specify a field separator.  The default is \"|\".");
        helpMenu.Println("  -t:INT  Specify the field to use as the term field.  The default is 1.");
        helpMenu.Println("  -ti     Display the filtered input term in the output.");
        helpMenu.Println("  -v      Return the current version identification of LuiNorm.");
        helpMenu.Println("  -x:STR  Loading an alternative configuration file.");
    }
}
