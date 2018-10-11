package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp; 
import java.awt.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*****************************************************************************
* This class provides Ok, Cancel, Reset button panel.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class OkCancelResetButtonPanel extends JPanel
{
    public OkCancelResetButtonPanel()
    {
        okB_ = new JButton("Ok");
        cancelB_ = new JButton("Cancel");
        resetB_ = new JButton("Reset");
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);  //Pad: Top, Left, Bottom, Right
        GridBag.SetWeight(gbc, 0, 100);
        GridBag.SetPosSize(gbc, 0, 0, 1, 1);
        add(okB_, gbc);
        GridBag.SetPosSize(gbc, 1, 0, 1, 1);
        add(cancelB_, gbc);
        GridBag.SetPosSize(gbc, 2, 0, 1, 1);
        add(resetB_, gbc);
    }
    public JButton GetOkButton()
    {
        return okB_;
    }
    public JButton GetCancelButton()
    {
        return cancelB_;
    }
    public JButton GetResetButton()
    {
        return resetB_;
    }
    public static void main(String[] args)
    {
        JFrame frame = new LibCloseableFrame("OkCancelReset Panel");
        frame.getContentPane().add(new OkCancelResetButtonPanel(), "South");
        
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
    // data members
    private JButton okB_ = null;
    private JButton cancelB_ = null;
    private JButton resetB_ = null;
    private final static long serialVersionUID = 5L;
}
