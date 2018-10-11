package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
*  This class is to show a category selection dialog.
*  The constructor is private since no instance object should be generated.
*  ShowDialog() should be called when using this dialog
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class CategoryDialog extends JDialog implements ActionListener
{
    private CategoryDialog(JFrame owner, String label, String title)
    {
        super(owner, title, true);
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(350, 450);
        // text Field for the value of category
        JPanel textP = new JPanel();
        textP.setLayout(new BoxLayout(textP, BoxLayout.X_AXIS));
        catField_ = new IntTextField(0, 4);
        catField_.setEditable(false);
        textP.add(new JLabel(label));
        textP.add(catField_);
        // Panel for check boxes of Categories
        CategoryPanel catP = new CategoryPanel(this, 1);
        cb_ = catP.GetCheckBox();
        // botton panel 
        OkCancelResetAllButtonPanel buttonP = new OkCancelResetAllButtonPanel();
        JButton okB = buttonP.GetOkButton();
        JButton cancelB = buttonP.GetCancelButton();
        JButton resetB = buttonP.GetResetButton();
        JButton allB = buttonP.GetAllButton();
        // Top level Panel
        JPanel centerP = new JPanel();
        centerP.add(textP);
        centerP.add(catP);
        getContentPane().add(centerP, "Center");
        getContentPane().add(buttonP, "South");
        // inner class for action listener
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                category_ = Long.toString(categoryValue_);
                setVisible(false);
            }
        });
        cancelB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                category_ = null;
                setVisible(false);
            }
        });
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
                {
                    cb_[i].setSelected(false);
                }
                categoryValue_ = 0;
                catField_.setText(Long.toString(categoryValue_));
            }
        });
        allB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
                {
                    cb_[i].setSelected(true);
                }
                categoryValue_ = LvgDef.ALL_CATEGORY_VALUE;
                catField_.setText(Long.toString(categoryValue_));
            }
        });
    }
    public void actionPerformed(ActionEvent evt)
    {
        categoryValue_ = 0;
        for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
        {
            if(cb_[i].isSelected() == true)
            {
                categoryValue_ = categoryValue_ | LvgDef.CATEGORY_VALUE[i];
            }
        }
        catField_.setText(Long.toString(categoryValue_));
    }
    public String GetValueStr()
    {
        return category_;
    }
    public long GetValue()
    {
        return categoryValue_;
    }
    public void SetValueStr(String valueStr)
    {
        if(valueStr != null)
        {
            try
            {
                category_ = valueStr;
                categoryValue_ = Long.parseLong(category_);
                catField_.setText(category_);
                for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
                {
                    boolean selected = 
                        (((categoryValue_ & LvgDef.CATEGORY_VALUE[i]) > 0)
                        ?true:false);
                    cb_[i].setSelected(selected);
                }
            }
            catch (Exception e)
            {
                category_ = "";
                catField_.setText(category_);
                for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
                {
                    cb_[i].setSelected(false);
                }
            }
        }
        else
        {
            category_ = "";
            catField_.setText(category_);
            for(int i = 0; i < LvgDef.CATEGORY_NUM; i++)
            {
                cb_[i].setSelected(false);
            }
        }
    }
    public static String ShowDialog(JFrame owner, long category)
    {
        // display the category panel
        if(catD_ == null)
        {
            catD_ = new CategoryDialog(owner,
                "Value of selected category: ", "Category Selection");
        }
        catD_.SetValueStr(Long.toString(category));
        catD_.setVisible(true);
        return catD_.GetValueStr();
    }
    public static void ShowDialog(JFrame owner, JTextField valueT)
    {
        // display the category panel
        if(catD_ == null)
        {
            catD_ = new CategoryDialog(owner,
                "Value of selected category: ", "Category Selection");
        }
        catD_.SetValueStr(valueT.getText());
        catD_.setVisible(true);
        // check & update the input category
        if(catD_.GetValueStr() != null)
        {
            valueT.setText(catD_.GetValueStr());
        }
    }
    public static void ShowDialog(JFrame owner, JTextField valueT,
        JTextField inflValueT)
    {
        // display the category panel
        if(catD_ == null)
        {
            catD_ = new CategoryDialog(owner,
                "Value of selected category: ", "Category Selection");
        }
        catD_.SetValueStr(valueT.getText());
        catD_.setVisible(true);
        // check the input category and update
        if(catD_.GetValueStr() != null)
        {
            valueT.setText(catD_.GetValueStr());
            long inflValue = CalInflectionValue(catD_.GetValue());
            inflValueT.setText(Long.toString(inflValue));
        }
    }
    // private method
    private static long CalInflectionValue(long cat)
    {
        long inflValue = 0;
        Vector<Long> catValues = Category.ToValues(cat);
        for(int i = 0; i < catValues.size(); i++)
        {
            long curCat = (catValues.elementAt(i)).longValue();
            for(int j = 0; j < LvgDef.INFLECTION_NUM; j++)
            {
                if(CatInfl.IsRelated(curCat, Inflection.GetBitValue(j)) == true)
                {
                    inflValue = inflValue | LvgDef.INFLECTION_VALUE[j];
                }
            }
        }
        return inflValue;
    }
    // private data
    private static JCheckBox[] cb_ = new JCheckBox[LvgDef.CATEGORY_NUM];
    private static IntTextField catField_ = null;
    private static long categoryValue_ = 0;
    private static String category_ = null;
    private static CategoryDialog catD_ = null;
    private final static long serialVersionUID = 5L;
}
