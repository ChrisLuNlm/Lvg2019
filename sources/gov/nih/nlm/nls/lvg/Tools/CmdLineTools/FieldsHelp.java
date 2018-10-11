package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.io.*;
import gov.nih.nlm.nls.lvg.Util.*;
/*****************************************************************************
* This class is to print out help menu for Fields.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class FieldsHelp
{
    // protected method
    protected static void FieldsHelp(BufferedWriter bw, boolean fileOutput,
        Out out)
    {
        HelpMenu helpMenu = new HelpMenu(bw, fileOutput, out);
        // print out the usage
        helpMenu.Println("");
        helpMenu.Println("Synopsis:");
        helpMenu.Println("  fields [options]");
        helpMenu.Println("");
        helpMenu.Println("Description:");
        helpMenu.Println("  Cut out and/or rearrange fields");
        helpMenu.Println("");
        helpMenu.Println("Options:");
        helpMenu.Println("  -F:INT  Specify field(s) to be copied to outputs.");
        helpMenu.Println("  -h      Print program help information (this is it).");
        helpMenu.Println("  -hs     Print option's hierarchy structure."); 
        helpMenu.Println("  -i:STR  Specify input file name.  The default is screen input.");
        helpMenu.Println("  -o:STR  Specify output file name.  The default is screen output.");
        helpMenu.Println("  -p      Show the prompt. The default is no prompt.");
        helpMenu.Println("  -s:STR  Specify a field separator.  The default is \"|\".");
        helpMenu.Println("  -v      Return the current version identification of Fields.");
    }
}
