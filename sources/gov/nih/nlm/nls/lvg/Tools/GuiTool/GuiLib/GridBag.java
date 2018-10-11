package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/*****************************************************************************
* This class provides gridBag class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class GridBag extends JPanel
{
    public static void SetWeight(GridBagConstraints gbc, int weightx, 
        int weighty) 
    {
        gbc.weightx = weightx;
        gbc.weighty = weighty;
    }
    public static void SetPosSize(GridBagConstraints gbc, int x, int y, 
        int width, int height)
    {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
    }
    private final static long serialVersionUID = 5L;
}
