package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib;
import javax.swing.*;
/*****************************************************************************
* This class provides menu class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LibMenu
{
    private LibMenu()
    {
    }
    // public methods
    public static JMenu MakeMenu(Object parent, Object[] items, Object target)
    {
        JMenu m = null;
        if(parent instanceof JMenu)
        {
            m = (JMenu) parent;
        }
        else if(parent instanceof String)
        {
            m = new JMenu((String) parent);
        }
        else
        {
            return null;
        }
        for(int i = 0; i < items.length; i++)
        {
            if(items[i] == null)        // add a separator if input is null
            {
                m.addSeparator();
            }
            else
            {
                m.add(LibPopupMenu.MakeMenuItem(items[i], target));
            }
        }
        return m;
    }
}
