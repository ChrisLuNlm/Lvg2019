package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
* This class show the detail information for a LexItem in a Vector.
* The constructor is public and a Vector of lexItems object are passed in.
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class ViewLexItemsDialog extends JDialog
{
    public ViewLexItemsDialog(JFrame owner, Vector<LexItem> lexItems, 
        String title, String label, int index)
    {
        super(owner, title, false);
        owner_ = owner;
        index_ = index;
        if(index_ < 0)
        {
            index_ = 0;
        }
        else if(index_ > lexItems.size()-1)
        {
            index_ = lexItems.size()-1;
        }
        lexItems_ = lexItems;
        Init();
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(600, 530);
        // top panel
        JPanel topP = new JPanel();
        topP.add(new JLabel(label));
        // center
        JPanel centerP = new JPanel();
        centerP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Output LexItem"));
        GridBagLayout gbl = new GridBagLayout();
        centerP.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);
        GridBag.SetWeight(gbc, 100, 100);
        
        JButton srcCatB = new JButton("Details");
        JButton tarCatB = new JButton("Details");
        JButton srcInflB = new JButton("Details");
        JButton tarInflB = new JButton("Details");
        for(int i = 0; i < FIELD_NUM; i++)
        {
            GridBag.SetPosSize(gbc, 0, i, 1, 1);
            centerP.add(cb_[i], gbc);
            if(i == DETAIL_INFO)
            {
                GridBag.SetPosSize(gbc, 1, i, 2, 1);
                JScrollPane scrollPane = new JScrollPane(detailArea_);
                centerP.add(scrollPane, gbc);
            }
            else if(i == SRC_CAT)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                centerP.add(valueT_[i], gbc);
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                centerP.add(srcCatB, gbc);
            }
            else if(i == TAR_CAT)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                centerP.add(valueT_[i], gbc);
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                centerP.add(tarCatB, gbc);
            }
            else if(i == SRC_INFL)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                centerP.add(valueT_[i], gbc);
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                centerP.add(srcInflB, gbc);
            }
            else if(i == TAR_INFL)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                centerP.add(valueT_[i], gbc);
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                centerP.add(tarInflB, gbc);
            }
            else
            {
                GridBag.SetPosSize(gbc, 1, i, 2, 1);
                centerP.add(valueT_[i], gbc);
            }
        }
        // bottom panel
        ListButtonPanel bottomP = new ListButtonPanel();
        JButton firstB = bottomP.GetFirstButton();
        JButton prevB = bottomP.GetPreviousButton();
        JButton nextB = bottomP.GetNextButton();
        JButton lastB = bottomP.GetLastButton();
        JButton goB = bottomP.GetGoButton();
        JButton closeB = bottomP.GetCloseButton();
        indexT_ = bottomP.GetIndexTextField();
        indexT_.setText(Integer.toString(index_ + 1));
        // top level
        getContentPane().add(topP, "North");
        getContentPane().add(centerP, "Center");
        getContentPane().add(bottomP, "South");
        // inner class for action listener
        firstB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                index_ = 0;
                UpdatePage();
            }
        });
        prevB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                index_--;
                if(index_ < 0)
                {
                    index_ = 0;
                    Toolkit.getDefaultToolkit().beep();
                }
                UpdatePage();
            }
        });
        nextB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                index_++;
                int max = lexItems_.size()-1;
                if(index_ > max)
                {
                    index_ = max;
                    Toolkit.getDefaultToolkit().beep();
                }
                UpdatePage();
            }
        });
        lastB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                index_ = lexItems_.size()-1;
                UpdatePage();
            }
        });
        goB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                try
                {
                    index_ = Integer.parseInt(indexT_.getText()) - 1;
                }
                catch(Exception e)
                {
                    Toolkit.getDefaultToolkit().beep();
                }
                int max = lexItems_.size()-1;
                if(index_ > max)
                {
                    index_ = max;
                    Toolkit.getDefaultToolkit().beep();
                }
                else if(index_ < 0)
                {
                    index_ = 0;
                    Toolkit.getDefaultToolkit().beep();
                }
                UpdatePage();
            }
        });
        closeB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                setVisible(false);
            }
        });
        srcCatB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String name = GetCategoryName(valueT_[SRC_CAT].getText());
                JOptionPane.showMessageDialog(owner_, name,
                    "Category Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        tarCatB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String name = GetCategoryName(valueT_[TAR_CAT].getText());
                JOptionPane.showMessageDialog(owner_, name,
                    "Category Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        srcInflB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String name = GetInflectionName(valueT_[SRC_INFL].getText());
                JOptionPane.showMessageDialog(owner_, name,
                    "Inflection Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        tarInflB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String name = GetInflectionName(valueT_[TAR_INFL].getText());
                JOptionPane.showMessageDialog(owner_, name,
                    "Inflection Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    //protected
    protected static String GetCategoryName(String in)
    {
        String out = new String();
        try
        {
            long value = Long.parseLong(in);
            out = "Value: " + in 
                + "\nName: " + Category.ToName(value);
        }
        catch(Exception e)
        {
            out = "No detail information available!";
        }
        return out;
    }
    protected static String GetInflectionName(String in)
    {
        String out = new String();
        try
        {
            long value = Long.parseLong(in);
            out = "Value: " + in
                + "\nName: " + Inflection.ToName(value);
        }
        catch(Exception e)
        {
            out = "No detail information available!";
        }
        return out;
    }
    private void UpdatePage()
    {
        if((lexItems_ != null) && (index_ >= 0) && (index_ < lexItems_.size()))
        {
            LexItem lexItem = lexItems_.elementAt(index_);
            defaults_[ORG_TERM] = lexItem.GetOriginalTerm();
            defaults_[FLOW_NUM] = Integer.toString(lexItem.GetFlowNumber());
            defaults_[FLOW_HIS] = lexItem.GetFlowHistory();
            defaults_[SRC_TERM] = lexItem.GetSourceTerm();
            defaults_[TAR_TERM] = lexItem.GetTargetTerm();
            defaults_[SRC_CAT] = 
                Long.toString(lexItem.GetSourceCategory().GetValue());
            defaults_[TAR_CAT] = 
                Long.toString(lexItem.GetTargetCategory().GetValue());
            defaults_[SRC_INFL] = 
                Long.toString(lexItem.GetSourceInflection().GetValue());
            defaults_[TAR_INFL] = 
                Long.toString(lexItem.GetTargetInflection().GetValue());
            defaults_[MUTATE_INFO] = lexItem.GetMutateInformation();
            defaults_[DETAIL_INFO] = lexItem.GetDetailInformation();
        }
        for(int i = 0; i < FIELD_NUM; i++)
        {
            valueT_[i].setText(defaults_[i]);
        }
        detailArea_.setText(defaults_[DETAIL_INFO]);
        indexT_.setText(Integer.toString(index_ + 1));
        OutputPanel.SetSelectedIndex(index_);
    }
    // private methods
    public void Init()
    {
        if((lexItems_ != null) && (index_ >= 0) && (index_ < lexItems_.size()))
        {
            LexItem lexItem = lexItems_.elementAt(index_);
            defaults_[ORG_TERM] = lexItem.GetOriginalTerm();
            defaults_[FLOW_NUM] = Integer.toString(lexItem.GetFlowNumber());
            defaults_[FLOW_HIS] = lexItem.GetFlowHistory();
            defaults_[SRC_TERM] = lexItem.GetSourceTerm();
            defaults_[TAR_TERM] = lexItem.GetTargetTerm();
            defaults_[SRC_CAT] = 
                Long.toString(lexItem.GetSourceCategory().GetValue());
            defaults_[TAR_CAT] = 
                Long.toString(lexItem.GetTargetCategory().GetValue());
            defaults_[SRC_INFL] = 
                Long.toString(lexItem.GetSourceInflection().GetValue());
            defaults_[TAR_INFL] = 
                Long.toString(lexItem.GetTargetInflection().GetValue());
            defaults_[MUTATE_INFO] = lexItem.GetMutateInformation();
            defaults_[DETAIL_INFO] = lexItem.GetDetailInformation();
        }
        size_[ORG_TERM] = 15;
        size_[FLOW_NUM] = 2;
        size_[FLOW_HIS] = 15;
        size_[SRC_TERM] = 15;
        size_[TAR_TERM] = 15;
        size_[SRC_CAT] = 4;
        size_[TAR_CAT] = 4;
        size_[SRC_INFL] = 8;
        size_[TAR_INFL] = 8;
        size_[MUTATE_INFO] = 35;
        size_[DETAIL_INFO] = 0;
        if(detailArea_ == null)
        {
            detailArea_ = new JTextArea(5, 35);
            detailArea_.setLineWrap(true);
            detailArea_.setWrapStyleWord(true);
            detailArea_.setEditable(false);
            detailArea_.setText(defaults_[DETAIL_INFO]);
        }
        for(int i = 0; i < FIELD_NUM; i++)
        {
            cb_[i] = new JLabel(FIELD_STR[i] + ": ");
            valueT_[i] = new JTextField(defaults_[i], size_[i]);
            valueT_[i].setEditable(false);
        }
    }
    // private data
    private static final int FIELD_NUM = 11;
    private static final String[] FIELD_STR = 
        {"Original term", 
         "Flow number", "Flow history",
         "Source term", "Target term",
         "Source category", "Target category",
         "Source inflection", "Target inflection",
         "Mutate information", "Detail information"
         };
    private static final int ORG_TERM = 0;
    private static final int FLOW_NUM = 1;
    private static final int FLOW_HIS = 2;
    private static final int SRC_TERM = 3;
    private static final int TAR_TERM = 4;
    private static final int SRC_CAT = 5;
    private static final int TAR_CAT = 6;
    private static final int SRC_INFL = 7;
    private static final int TAR_INFL = 8;
    private static final int MUTATE_INFO = 9;
    private static final int DETAIL_INFO = 10;
    private JLabel[] cb_ = new JLabel[FIELD_NUM];
    private JTextField[] valueT_ = new JTextField[FIELD_NUM];
    private int[] size_ = new int[FIELD_NUM];
    private String[] defaults_ = new String[FIELD_NUM];
    private JTextField indexT_ = null;
    private JTextArea detailArea_ = null;
    private JFrame owner_ = null;
    private int index_ = 0;
    private Vector<LexItem> lexItems_ = new Vector<LexItem>();
    private final static long serialVersionUID = 5L;
}
