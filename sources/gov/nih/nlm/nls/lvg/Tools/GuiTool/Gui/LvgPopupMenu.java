package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*************************************************************************
* This class takes care of popup menu executed by pressing right button
* on the mouse.
* The actions are performed in LvgFrame.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LvgPopupMenu
{
    // private constructor
    private LvgPopupMenu()
    {
    }
    
    // private methods
    public static void AddPopupMenu(JFrame target)
    {
        // popup menu
        Object[] popupItems = {"Flow Setup", null, "Input Options", 
            "Mutate Options", "Output Options", null, "Exit"};
        popup_ = LibPopupMenu.MakePopupMenu(popupItems, target);
        // isPopupTrigger() needs to checks both pressed and released
        // for different platform
        target.getContentPane().addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent evt)
            {
                if(evt.isPopupTrigger())
                {
                    popup_.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
            public void mouseReleased(MouseEvent evt)
            {
                if(evt.isPopupTrigger())
                {
                    popup_.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });
    }
    private static JPopupMenu popup_;
}
