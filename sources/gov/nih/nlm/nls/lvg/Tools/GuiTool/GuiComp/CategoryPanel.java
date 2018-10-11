package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*****************************************************************************
* This class provides category panel.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class CategoryPanel extends JPanel
{
    public CategoryPanel(ActionListener target, int col)
    {
        // Grid Layout
        int row = (int) Math.ceil((double) LvgDef.CATEGORY_NUM/col);
        setLayout(new GridLayout(row, col, 5, 3));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Categories"));
        // init check box
        for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
        {
            cb_[i] = new JCheckBox(LvgDef.CATEGORY[i] + " ("
                + LvgDef.CATEGORY_VALUE[i] + ")");
            cb_[i].addActionListener(target);
            add(cb_[i]);
        }
    }
    public JCheckBox[] GetCheckBox()
    {
        return cb_;
    }
    // Test driver
    public static void main(String[] args)
    {
        JFrame frame = new LibCloseableFrame("Category Panel");
        frame.setContentPane(new CategoryPanel(new Handler(), 2));    // 2 col
        frame.setSize(400, 150);
        frame.setVisible(true);
    }
    // data members
    private JCheckBox[] cb_ = new JCheckBox[LvgDef.CATEGORY_NUM];
    private final static long serialVersionUID = 5L;
}
