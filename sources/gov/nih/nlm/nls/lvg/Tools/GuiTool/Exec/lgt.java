package gov.nih.nlm.nls.lvg.Tools.GuiTool.Exec;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui.*;
/*****************************************************************************
* This class provides LVG Gui Tool executable class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class lgt 
{
    public static void main(String[] args) 
    {
        // read in configuration
        if(args.length > 1)
        {
            System.out.println("Usage: java Lgt <Config file>");
            System.exit(0);
        }
        else if(args.length == 1)
        {
            String config = args[0];
            LvgGlobal.SetConfig(config);
        }
        if(LvgGlobal.lvg_.GetRunFlag() == true)
        {
            LvgFrame lvgFrame = new LvgFrame();
            lvgFrame.setVisible(true);
        }
    }
}
