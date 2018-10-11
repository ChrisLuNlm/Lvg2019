package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp; 
import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*****************************************************************************
* This is the inflection panel.
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class InflectionPanel extends JPanel
{
    public InflectionPanel(ActionListener target, int col)
    {
        // Grid Layout
        int row = (int) Math.ceil((double) LvgDef.INFLECTION_NUM/col);
        setLayout(new GridLayout(row, col, 5, 3));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Inflections"));
        
        // init check box
        for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
        {
            cb_[i] = new JCheckBox(LvgDef.INFLECTION[i] + " ("
                + LvgDef.INFLECTION_VALUE[i] + ")");
            cb_[i].addActionListener(target);
            add(cb_[i]);
        }
    }
    public void UpdateCheckBox(long cat)
    {
        // update check box
        for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
        {
            Vector<Long> catValues = Category.ToValues(cat);
            boolean flag = false;
            for(int j = 0; j < catValues.size(); j++)
            {
                long curCat = (catValues.elementAt(j)).longValue();
                // disable check box if it is not a legal inflection
                flag = flag || 
                    CatInfl.IsRelated(curCat, Inflection.GetBitValue(i));
            }
            cb_[i].setEnabled(flag);
        }
    }
    public void ResetCheckBox()
    {
        for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
        {
            cb_[i].setEnabled(true);
        }
    }
    public JCheckBox[] GetCheckBox()
    {
        return cb_;
    }
    // Test driver
    public static void main(String[] args)
    {
        JFrame frame = new LibCloseableFrame("Inflection Panel");
        frame.setContentPane(new InflectionPanel(new Handler(), 3));    // 3 col
        frame.setSize(900, 250);
        frame.setVisible(true);
    }
    // data members
    private JCheckBox[] cb_ = new JCheckBox[LvgDef.INFLECTION_NUM];
    private final static long serialVersionUID = 5L;
}
