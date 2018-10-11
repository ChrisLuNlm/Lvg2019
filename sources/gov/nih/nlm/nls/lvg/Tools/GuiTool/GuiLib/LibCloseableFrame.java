package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib;
import java.awt.event.*;
import javax.swing.*;
/*****************************************************************************
* This class provides closeable frame class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LibCloseableFrame extends JFrame
{
    public LibCloseableFrame()
    {
        // make the window closable, using inner class
        addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    System.exit(0);
                }
            });
    }
    
    public LibCloseableFrame(String title)
    {
        this();
        setTitle(title);
    }
    private final static long serialVersionUID = 5L;
}
