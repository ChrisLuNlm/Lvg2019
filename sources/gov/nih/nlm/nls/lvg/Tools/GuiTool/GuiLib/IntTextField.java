package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
/*****************************************************************************
* This class provides Int text field class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class IntTextField extends JTextField
{
    public IntTextField(int defval, int size)
    {
        super("" + defval, size);
    }
    protected Document createDefaultModel()
    {
        return new IntTextDocument();
    }
    public boolean isValid()
    {
        try
        {
            Integer.parseInt(getText());
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
    public int getValue()
    {
        try
        {
            return Integer.parseInt(getText());
        }
        catch(NumberFormatException e)
        {
            return 0;
        }
    }
    private final static long serialVersionUID = 5L;
}
