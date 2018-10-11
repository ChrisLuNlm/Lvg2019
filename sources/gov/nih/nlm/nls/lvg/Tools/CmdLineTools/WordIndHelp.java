package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.io.*;
import gov.nih.nlm.nls.lvg.Util.*;
/*****************************************************************************
* This class is to print out help menu for WordInd.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class WordIndHelp
{
    // protected method
    protected static void WordIndHelp(BufferedWriter bw, boolean fileOutput,
        Out out)
    {
        HelpMenu helpMenu = new HelpMenu(bw, fileOutput, out);
        // print out the usage
        helpMenu.Println("");
        helpMenu.Println("Synopsis:");
        helpMenu.Println("  WordInd [options]");
        helpMenu.Println("");
        helpMenu.Println("Description:");
        helpMenu.Println("  Tokenize a term into words.  The \"words\" are output one per line. That is the lvg flow options -f:l:c.");
        helpMenu.Println("");
        helpMenu.Println("Options:");
        helpMenu.Println("  -c      Reserve case (default is to lowercase words).");
        helpMenu.Println("  -F:INT  Specify field to be copied to outputs.");
        helpMenu.Println("  -h      Print program help information (this is it).");
        helpMenu.Println("  -hs     Print option's hierarchy structure."); 
        helpMenu.Println("  -i:STR  Specify input file name.  The default is screen input.");
        helpMenu.Println("  -n      Return a \"-No Output-\" message when an input produces no output.");
        helpMenu.Println("  -o:STR  Specify output file name.  The default is screen output.");
        helpMenu.Println("  -p      Show the prompt. The default is no prompt.");
        helpMenu.Println("  -s:STR  Specify a field separator.  The default is \"|\".");
        helpMenu.Println("  -t:INT  Specify the field to use as the term field.  The default is 1.");
        helpMenu.Println("  -v      Return the current version identification of WordInd.");
    }
}
