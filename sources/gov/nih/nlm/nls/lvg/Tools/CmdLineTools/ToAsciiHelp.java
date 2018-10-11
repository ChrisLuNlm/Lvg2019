package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.io.*;
import gov.nih.nlm.nls.lvg.Util.*;
/*****************************************************************************
* This class is to print out help menu of ToAscii.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class ToAsciiHelp
{
    // protected method
    protected static void ToAsciiHelp(BufferedWriter bw, boolean fileOutput,
        Out out)
    {
        HelpMenu helpMenu = new HelpMenu(bw, fileOutput, out);
        // print out the usage
        helpMenu.Println("");
        helpMenu.Println("Synopsis:");
        helpMenu.Println("  ToAscii [options]");
        helpMenu.Println("");
        helpMenu.Println("Description:");
        helpMenu.Println("  To ASCII.  Convert UTF-8 to ASCII by utilizing lvg flows -f:q7:q8");
        helpMenu.Println("");
        helpMenu.Println("Options:");
        helpMenu.Println("  -ci     Print configuration information.");
        helpMenu.Println("  -d      Print details of Ascii convertion operations.");
        helpMenu.Println("  -h      Print program help information (this is it).");
        helpMenu.Println("  -hs     Print option's hierarchy structure."); 
        helpMenu.Println("  -i:STR  Specify input file name.  The default is screen input.");
        helpMenu.Println("  -n      Return a \"-No Output-\" message when an input produces no output.");
        helpMenu.Println("  -o:STR  Specify output file name.  The default is screen output.");
        helpMenu.Println("  -p      Show the prompt. The default is no prompt.");
        helpMenu.Println("  -pio    Preserve and display input and output term.");
        helpMenu.Println("  -v      Return the current version identification of ToAscii.");
        helpMenu.Println("  -x:STR  Loading an alternative configuration file.");
    }
}
