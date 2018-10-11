package gov.nih.nlm.nls.lvg.Tools.CmdLineTools;
import java.io.*;
import gov.nih.nlm.nls.lvg.Util.*;
/*****************************************************************************
* This is the class of help menu for command line tools.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class HelpMenu
{
    // public constructor
    public HelpMenu(BufferedWriter bw, boolean fileOutput, Out out)
    {
        bw_ = bw;
        fileOutput_ = fileOutput;
        out_ = out;
    }
    // public methods
    public void Println(String text)
    {
        try
        {
            out_.Println(bw_, text, fileOutput_, false);
        }
        catch (IOException e)
        {
            System.err.println("**ERR@HelpMenu.MenuPrint(" + text + ")");
        }
    }
    // data member
    private BufferedWriter bw_ = null;
    private boolean fileOutput_ = false;
    private Out out_ = null;
}
