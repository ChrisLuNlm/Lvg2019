package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/*****************************************************************************
* This class provides handler class.
* it is a public class Handler extends JPanel implements ActionListener.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class Handler implements ActionListener
{
    public void actionPerformed(ActionEvent evt)
    {
        String cmd = evt.getActionCommand();
        System.out.println("-- " + cmd);
    }
}
