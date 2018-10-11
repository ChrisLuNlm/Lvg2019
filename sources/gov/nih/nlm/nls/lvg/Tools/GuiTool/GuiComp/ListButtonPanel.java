package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp; 
import java.awt.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*****************************************************************************
* This class provides list button panel.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class ListButtonPanel extends JPanel
{
    public ListButtonPanel()
    {
        firstB_ = new JButton("<<");
        prevB_ = new JButton("<");
        nextB_ = new JButton(">");
        lastB_ = new JButton(">>");
        goB_ = new JButton("Go");
        closeB_ = new JButton("Close");
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);  //Pad: Top, Left, Bottom, Right
        GridBag.SetWeight(gbc, 0, 100);
        GridBag.SetPosSize(gbc, 0, 0, 1, 1);
        add(firstB_, gbc);
        GridBag.SetPosSize(gbc, 1, 0, 1, 1);
        add(prevB_, gbc);
        GridBag.SetPosSize(gbc, 2, 0, 1, 1);
        add(nextB_, gbc);
        GridBag.SetPosSize(gbc, 3, 0, 1, 1);
        add(lastB_, gbc);
        GridBag.SetPosSize(gbc, 4, 0, 1, 1);
        add(indexT_, gbc);
        GridBag.SetPosSize(gbc, 5, 0, 1, 1);
        add(goB_, gbc);
        GridBag.SetPosSize(gbc, 6, 0, 1, 1);
        add(closeB_, gbc);
    }
    public JButton GetFirstButton()
    {
        return firstB_;
    }
    public JButton GetPreviousButton()
    {
        return prevB_;
    }
    public JButton GetNextButton()
    {
        return nextB_;
    }
    public JButton GetLastButton()
    {
        return lastB_;
    }
    public JButton GetGoButton()
    {
        return goB_;
    }
    public JButton GetCloseButton()
    {
        return closeB_;
    }
    public JTextField GetIndexTextField()
    {
        return indexT_;
    }
    public static void main(String[] args)
    {
        JFrame frame = new LibCloseableFrame("List Button Panel");
        frame.getContentPane().add(new ListButtonPanel(), "South");
        
        frame.setSize(600, 200);
        frame.setVisible(true);
    }
    // data members
    private JButton firstB_ = null;
    private JButton prevB_ = null;
    private JButton nextB_ = null;
    private JButton lastB_ = null;
    private JButton goB_ = null;
    private JButton closeB_ = null;
    private JTextField indexT_ = new JTextField(3);
    private final static long serialVersionUID = 5L;
}
