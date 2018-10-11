package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
/*****************************************************************************
* This class provides Int text document class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class IntTextDocument extends PlainDocument
{
    public void insertString(int offs, String str, AttributeSet a)
        throws BadLocationException
    {
        if(str == null)
        {
            return;
        }
        String oldString = getText(0, getLength());
        String newString = oldString.substring(0, offs) + str
            + oldString.substring(offs);
        try
        {
            Integer.parseInt(newString + "0");
            super.insertString(offs, str, a);
        }
        catch(NumberFormatException e)
        {
        }
    }
    private final static long serialVersionUID = 5L;
}
