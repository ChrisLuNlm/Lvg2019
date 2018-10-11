package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
*  This class is to show an output fields specifier dialog. 
*  The constructor is private since no instance object should be generated.
*  ShowDialog( ) should be called when use this dialog
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class FieldSpecifierDialog extends JDialog
{
    private FieldSpecifierDialog(JFrame owner)
    {
        super(owner, "Specify fields", true);
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(400, 300);
        // GUI components: Fields
        JScrollPane availableSp = new JScrollPane(availableList_);
        Box available = Box.createVerticalBox();
        available.add(new JLabel("Available Fields:"));
        available.add(Box.createVerticalStrut(5));
        available.add(availableSp);
        // buttons
        Box button = Box.createVerticalBox();
        JButton addB = new JButton(" Add  ");
        JButton insertB = new JButton("Insert");
        JButton deleteB = new JButton("Delete");
        button.add(Box.createVerticalStrut(10));
        button.add(addB);
        button.add(Box.createVerticalStrut(10));
        button.add(insertB);
        button.add(Box.createVerticalStrut(10));
        button.add(deleteB);
        // Selected List
        selectedList_.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane selectedSp = new JScrollPane(selectedList_);
        Box selected = Box.createVerticalBox();
        selected.add(new JLabel("Selected Fields:"));
        selected.add(Box.createVerticalStrut(5));
        selected.add(selectedSp);
        // center Panel
        JPanel centerP = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        centerP.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 100;    // resize weight: X size not changed
        gbc.weighty = 100;  // resize weight: y size chnage 100%
        gbc.insets = new Insets(10, 5, 20, 5);   //Pad: Top, Left, Bottom, Right
        GridBag.SetPosSize(gbc, 0, 0, 1, 3);
        centerP.add(available, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        GridBag.SetPosSize(gbc, 1, 0, 1, 3);
        centerP.add(button, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        GridBag.SetPosSize(gbc, 2, 0, 1, 3);
        centerP.add(selected, gbc);
        // botton panel 
        OkCancelResetButtonPanel buttonP = new OkCancelResetButtonPanel();
        JButton okB = buttonP.GetOkButton();
        JButton cancelB = buttonP.GetCancelButton();
        JButton resetB = buttonP.GetResetButton();
                        
        // Top level Panel
        getContentPane().add(centerP, "Center");
        getContentPane().add(buttonP, "South");
        // inner class for action listener
        addB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = availableList_.getSelectedIndex();
                selectedModel_.addElement(fieldName_[index]);
            }
        });
        // insert the selected flow component
        insertB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int srcIndex = availableList_.getSelectedIndex();
                int tarIndex = selectedList_.getSelectedIndex();
                tarIndex = (tarIndex > 0 ? tarIndex : 0);
                selectedModel_.insertElementAt(fieldName_[srcIndex], tarIndex);
            }
        });
        // delete the selected flow component
        deleteB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = selectedList_.getSelectedIndex();
                if(index >= 0)
                {
                    selectedModel_.removeElementAt(index);
                }
            }
        });
        // add the flow into cmd
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                fieldStr_ = new String();
                fieldList_.removeAllElements();
                for(int i = 0; i < selectedModel_.size(); i++)
                {
                    String value = (selectedModel_.elementAt(i)).substring(6);
                    fieldStr_ += ":"  + value;
                    Integer fieldNum = new Integer(value);
                    fieldList_.addElement(fieldNum);
                }
                if(fieldStr_.length() > 0)
                {
                    fieldStr_ = fieldStr_.substring(1);
                }
                setVisible(false);
            }
        });
        // cancel this dialog
        cancelB.addActionListener(new ActionListener()    
        {
            public void actionPerformed(ActionEvent evt)
            {
                setVisible(false);
            }
        });
        // clear all added flow compoments
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                selectedModel_.removeAllElements();
            }
        });
    }
    public static void ShowDialog(JFrame owner, JTextField valueT)
    {
        // display the Dialog
        if(fieldD_ == null)
        {
            fieldD_ = new FieldSpecifierDialog(owner);
        }
        FieldSpecifierDialog.SetFieldStr(valueT.getText());
        fieldD_.setVisible(true);
        // update field String to valueT 
        if(fieldD_.GetFieldStr() != null)
        {
            valueT.setText(fieldD_.GetFieldStr());
        }
    }
    public static void SetFieldStr(String fieldStr)
    {
        fieldStr_ = fieldStr;
        // sync fieldList_, selectedList_ to fieldStr_ 
        fieldList_.removeAllElements();
        selectedModel_.removeAllElements();
        StringTokenizer buf = new StringTokenizer(fieldStr, ":");
        while(buf.hasMoreTokens() == true)
        {
            String curField = buf.nextToken();
            selectedModel_.addElement("Field " + curField);
            Integer fieldNum = new Integer(curField);
            fieldList_.addElement(fieldNum);
        }
    }
    public String GetFieldStr()
    {
        return fieldStr_;
    }
    public static Vector<Integer> GetFieldList()
    {
        return fieldList_;
    }
    // private method
    // private data
    private final static int MAX_FIELD = 20;
    private static String[] fieldName_ = new String[MAX_FIELD];
    private static JList<String> availableList_ = null;
    private static DefaultListModel<String> selectedModel_ 
        = new DefaultListModel<String>();
    private static JList<String> selectedList_ 
        = new JList<String>(selectedModel_); 
    private static String fieldStr_ = null;
    private static Vector<Integer> fieldList_ = new Vector<Integer>();
    private static FieldSpecifierDialog fieldD_ = null;
    static
    {
        for(int i = 0; i < MAX_FIELD; i++)
        {
            fieldName_[i] = "Field " + (i+1);
        }
        availableList_ = new JList<String>(fieldName_);
    }
    private final static long serialVersionUID = 5L;
}
