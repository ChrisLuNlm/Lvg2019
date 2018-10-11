package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
* This class show the dialog for modifying categories and inflections.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class CatInflDialog extends JDialog implements ActionListener
{
    public CatInflDialog(JFrame owner, String labelCat, String labelInfl,
        String title)
    {
        super(owner, title, true);
        // Geometry Setting
        setLocation(new Point(100, 200));
        setSize(880, 500);
        // text Field for the value of category
        JPanel textP1 = new JPanel();
        textP1.setLayout(new BoxLayout(textP1, BoxLayout.X_AXIS));
        catField_ = new IntTextField(0, 4);
        catField_.setEditable(false);
        textP1.add(new JLabel(labelCat));
        textP1.add(catField_);
        // text Field for the value of category
        JPanel textP2 = new JPanel();
        textP2.setLayout(new BoxLayout(textP2, BoxLayout.X_AXIS));
        inflField_ = new IntTextField(0, 8);
        inflField_.setEditable(false);
        textP2.add(new JLabel(labelInfl));
        textP2.add(inflField_);
        // Panel for check boxes of Categories
        CategoryPanel catP = new CategoryPanel(this, 1);
        catCb_ = catP.GetCheckBox();
        // Panel for check boxes of Categories
        inflP_ = new InflectionPanel(this, 2);
        inflP_.UpdateCheckBox(categoryValue_);
        inflCb_ = inflP_.GetCheckBox();
        // botton panel 
        JPanel buttonP = new JPanel();
        JButton okB = new JButton("Ok");
        JButton cancelB = new JButton("Cancel");
        JButton selectCatB = new JButton("Select All Categories");
        JButton selectInflB = new JButton("Select All Inflections");
        JButton resetCatB = new JButton("Reset Category");
        JButton resetInflB = new JButton("Reset Inflection");
        
        buttonP.add(okB);
        buttonP.add(cancelB);
        buttonP.add(selectCatB);
        buttonP.add(selectInflB);
        buttonP.add(resetCatB);
        buttonP.add(resetInflB);
        // Center Panel
        JPanel centerP = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        centerP.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 5, 5);   //Pad: Top, Left, Bottom, Right
        GridBag.SetWeight(gbc, 100, 0);
        GridBag.SetPosSize(gbc, 0, 0, 1, 1);
        centerP.add(textP1, gbc);
        GridBag.SetWeight(gbc, 100, 0);
        GridBag.SetPosSize(gbc, 1, 0, 1, 1);
        centerP.add(textP2, gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        GridBag.SetWeight(gbc, 100, 100);
        GridBag.SetPosSize(gbc, 0, 1, 1, 6);
        centerP.add(catP, gbc);
        GridBag.SetWeight(gbc, 100, 100);
        GridBag.SetPosSize(gbc, 1, 1, 5, 7);
        centerP.add(inflP_, gbc);
        // Top level Panel
        getContentPane().add(centerP, "Center");
        getContentPane().add(buttonP, "South");
        // inner class for action listener
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                category_ = Long.toString(categoryValue_);
                inflection_ = Long.toString(inflectionValue_);
                setVisible(false);
            }
        });
        cancelB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                category_ = null;
                inflection_ = null;
                setVisible(false);
            }
        });
        selectCatB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // select all category
                for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
                {
                    catCb_[i].setSelected(true);
                }
                categoryValue_ = LvgDef.ALL_CATEGORY_VALUE;
                catField_.setText(Long.toString(categoryValue_));
                // set checkbox of inflections to noneditable based on cat value
                inflP_.UpdateCheckBox(categoryValue_);
            }
        });
        selectInflB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // select all inflections
                inflectionValue_ = 0;
                for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
                {
                    inflCb_[i].setSelected(inflCb_[i].isEnabled());
                    // calculate value
                    if(inflCb_[i].isSelected() == true)
                    {
                        inflectionValue_ = 
                            inflectionValue_ | LvgDef.INFLECTION_VALUE[i];
                    }
                }
                inflField_.setText(Long.toString(inflectionValue_));
            }
        });
        resetCatB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // reset category
                for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
                {
                    catCb_[i].setSelected(false);
                }
                categoryValue_ = 0;
                catField_.setText(Long.toString(categoryValue_));
                // set checkbox of inflections to noneditable based on cat value
                inflP_.UpdateCheckBox(categoryValue_);
            }
        });
        resetInflB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // reset inflection
                for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
                {
                    inflCb_[i].setSelected(false);
                }
                inflectionValue_ = 0;
                inflField_.setText(Long.toString(inflectionValue_));
            }
        });
    }
    public void actionPerformed(ActionEvent evt)
    {
        // category value
        categoryValue_ = 0;
        for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
        {
            if(catCb_[i].isSelected() == true)
            {
                categoryValue_ = categoryValue_ | LvgDef.CATEGORY_VALUE[i];
            }
        }
        catField_.setText(Long.toString(categoryValue_));
        // disable checkbox of inflections based on category value
        inflP_.UpdateCheckBox(categoryValue_);
        // inflection value
        inflectionValue_ = 0;
        for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
        {
            if(inflCb_[i].isSelected() == true)
            {
                inflectionValue_ = 
                    inflectionValue_ | LvgDef.INFLECTION_VALUE[i];
            }
        }
        inflField_.setText(Long.toString(inflectionValue_));
    }
    public String GetCategoryStr()
    {
        return category_;
    }
    public String GetInflectionStr()
    {
        return inflection_;
    }
    public long GetCategoryValue()
    {
        return categoryValue_;
    }
    public long GetInflectionValue()
    {
        return inflectionValue_;
    }
    // private data
    private JCheckBox[] catCb_ = new JCheckBox[LvgDef.CATEGORY_NUM];
    private static IntTextField catField_ = null;
    private static long categoryValue_ = 0;
    private static String category_ = null;
    private JCheckBox[] inflCb_ = new JCheckBox[LvgDef.INFLECTION_NUM];
    private static IntTextField inflField_ = null;
    private static long inflectionValue_ = 0;
    private static String inflection_ = null;
    private InflectionPanel inflP_ = null;    // infl panel based on Category 
    private final static long serialVersionUID = 5L;
}
