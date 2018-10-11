package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/*****************************************************************************
* This class provides popup menu class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LibPopupMenu 
{
    // make it private so that no one will instantiate it
    private LibPopupMenu()
    {
    }
    public static JMenuItem MakeMenuItem(Object item, Object target)
    {
        JMenuItem mi = null;
        if(item instanceof String)
        {
            mi = new JMenuItem((String)item);
        }
        else if(item instanceof JMenuItem)
        {
            mi = (JMenuItem)item;
        }
        else
        {
            return null;
        }
        if(target instanceof ActionListener)
        {
            mi.addActionListener((ActionListener) target);
        }
        return mi;
    }
    public static JPopupMenu MakePopupMenu(Object[] items, Object target)
    {
        JPopupMenu pm = new JPopupMenu();
        for(int i = 0; i < items.length; i++)
        {
            if(items[i] == null)
            {
                pm.addSeparator();
            }
            else
            {
                pm.add(MakeMenuItem(items[i], target));
            }
        }
        return pm;
    }
}
