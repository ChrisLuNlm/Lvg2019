package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
*  This class is to show an inflection selection dialog.
*  The constructor is private since no instance object should be generated.
*  ShowDialog() should be called when using this dialog
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class InflectionDialog extends JDialog implements ActionListener
{
    private InflectionDialog(JFrame owner, String label, String title)
    {
        super(owner, title, true);
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(590, 480);
        // text Field for the value of inflection
        JPanel textP = new JPanel();
        textP.setLayout(new BoxLayout(textP, BoxLayout.X_AXIS));
        inflField_ = new IntTextField(0, 8);
        inflField_.setEditable(false);
        textP.add(new JLabel(label));
        textP.add(inflField_);
        // Panel for check boxes of Categories
        inflP_ = new InflectionPanel(this, 2);
        cb_ = inflP_.GetCheckBox();
        // botton panel 
        OkCancelResetAllButtonPanel buttonP = new OkCancelResetAllButtonPanel();
        JButton okB = buttonP.GetOkButton();
        JButton cancelB = buttonP.GetCancelButton();
        JButton resetB = buttonP.GetResetButton();
        JButton allB = buttonP.GetAllButton();
        // Top level Panel
        JPanel centerP = new JPanel();
        centerP.add(textP);
        centerP.add(inflP_);
        getContentPane().add(centerP, "Center");
        getContentPane().add(buttonP, "South");
        // inner class for action listener
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                inflection_ = Long.toString(inflectionValue_);
                setVisible(false);
            }
        });
        cancelB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                inflection_ = null;
                setVisible(false);
            }
        });
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
                {
                    cb_[i].setSelected(false);
                }
                inflectionValue_ = 0;
                inflField_.setText(Long.toString(inflectionValue_));
            }
        });
        allB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                inflectionValue_ = 0;
                for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
                {
                    cb_[i].setSelected(cb_[i].isEnabled());
                    if(cb_[i].isSelected() == true)
                    {
                        inflectionValue_ = 
                            inflectionValue_ | LvgDef.INFLECTION_VALUE[i];
                    }
                }
                inflField_.setText(Long.toString(inflectionValue_));
            }
        });
    }
    public void actionPerformed(ActionEvent evt)
    {
        inflectionValue_ = 0;
        for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
        {
            if(cb_[i].isSelected() == true)
            {
                inflectionValue_ = 
                    inflectionValue_ | LvgDef.INFLECTION_VALUE[i];
            }
        }
        inflField_.setText(Long.toString(inflectionValue_));
    }
    public String GetValueStr()
    {
        return inflection_;
    }
    public long GetValue()
    {
        return inflectionValue_;
    }
    public void SetValueStr(String valueStr)
    {
        if(valueStr != null)
        {
            try
            {
                inflection_ = valueStr;
                inflectionValue_ = Long.parseLong(inflection_);
                inflField_.setText(inflection_);
                for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
                {
                    boolean selected = 
                        (((inflectionValue_ & LvgDef.INFLECTION_VALUE[i]) > 0)
                        ?true:false);
                    cb_[i].setSelected(selected);
                }
            }
            catch (Exception e)
            {
                inflection_ = "";
                inflField_.setText(inflection_);
                for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
                {
                    cb_[i].setSelected(false);
                }
            }
        }
        else
        {
            inflection_ = "";
            inflField_.setText(inflection_);
            for(int i = 0; i < LvgDef.INFLECTION_NUM; i++)
            {
                cb_[i].setSelected(false);
            }
        }
    }
    public void SetCategoryValue(String valueStr)
    {
        if(valueStr != null)
        {
            try
            {
                long catValue = Long.parseLong(valueStr);
                inflP_.UpdateCheckBox(catValue);
            }
            catch (Exception e)
            {
            }
        }
    }
    public static void ShowDialog(JFrame owner, JTextField valueT)
    {
        // display the category panel
        if(inflD_ == null)
        {
            inflD_ = new InflectionDialog(owner,
                "Value of selected inflection: ", "Inflection Selection");
        }
        inflP_.ResetCheckBox();
        inflD_.SetValueStr(valueT.getText());
        inflD_.setVisible(true);
        // check the input category and update
        if(inflD_.GetValueStr() != null)
        {
            valueT.setText(inflD_.GetValueStr());
        }
    }
    // set check box for inflections none-editable based on category value
    public static void ShowDialog(JFrame owner, JTextField valueT,
        JTextField catValueT)
    {
        // display the category panel
        if(inflD_ == null)
        {
            inflD_ = new InflectionDialog(owner,
                "Value of selected inflection: ", "Inflection Selection");
        }
        inflD_.SetCategoryValue(catValueT.getText());
        inflD_.SetValueStr(valueT.getText());
        inflD_.setVisible(true);
        // check the input category and update
        if(inflD_.GetValueStr() != null)
        {
            valueT.setText(inflD_.GetValueStr());
        }
    }
    // private data
    private static JCheckBox[] cb_ = new JCheckBox[LvgDef.INFLECTION_NUM];
    private static InflectionPanel inflP_ = null;
    private static IntTextField inflField_ = null;
    private static long inflectionValue_ = 0;
    private static String inflection_ = null;
    private static InflectionDialog inflD_ = null;
    private final static long serialVersionUID = 5L;
}
