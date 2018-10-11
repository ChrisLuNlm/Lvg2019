package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*************************************************************************
* This class show the mutate panel for lexcial tool.
* The constructor is private since no instance object should be generated.
* GetPanel( ) should be called when use this panel.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class MutatePanel extends JPanel
{
    // private constructor
    private MutatePanel(JFrame owner)
    {
        owner_ = owner;
        // Layout
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Flow Mutate"));
        // init gui components
        JButton runB = new JButton("Run");
        JButton resetB = new JButton("Reset");
        JButton optionB = new JButton("Options");
        cmdField_ = new JTextField(35);
        cmdField_.setFont(new Font("Dialog", 0, 14));
        cmdField_.setEditable(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 2, 5, 2);
        GridBag.SetWeight(gbc, 100, 100);
        // layout
        // radio buttons
        for(int i = 0; i < TOTAL_NUM; i++)
        {
            GridBag.SetPosSize(gbc, i, 1, 1, 1);
            add(rb_[i], gbc);
        }
        // buttons
        GridBag.SetPosSize(gbc, 0, 2, 2, 1);
        gbc.anchor = GridBagConstraints.EAST;
        add(flowB_, gbc);
        GridBag.SetPosSize(gbc, 2, 2, 1, 1);
        gbc.anchor = GridBagConstraints.CENTER;
        add(runB, gbc);
        GridBag.SetPosSize(gbc, 3, 2, 1, 1);
        gbc.anchor = GridBagConstraints.CENTER;
        add(optionB, gbc);
        GridBag.SetPosSize(gbc, 4, 2, 1, 1);
        gbc.anchor = GridBagConstraints.WEST;
        add(resetB, gbc);
        // command line text
        cmd_ = Box.createHorizontalBox();
        cmd_.add(new JLabel("Lvg command: "));
        cmd_.add(cmdField_);
        cmd_.setVisible(LvgFrame.GetShowCmd());        // init condition
        GridBag.SetPosSize(gbc, 0, 3, 5, 1);
        add(cmd_, gbc);
        // inner class for action listener
        rb_[LVG_NO].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                curProgram_ = LVG_NO;
                // enable flow button
                flowB_.setEnabled(true);
                // Reset Command Line
                LvgGlobal.cmd_.Reset();
                LvgGlobal.UpdateCmdStr();
                OutputOptionDialog.SetOutputFiledsOption("");
            }
        });
        rb_[NORM_NO].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                curProgram_ = NORM_NO;
                // disable flow button
                flowB_.setEnabled(false);
                // Norm: Update LvgCommand & Output option dialog
                SetNotLvg(InputPanel.GetInLine());
            }
        });
        rb_[LUINORM_NO].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                curProgram_ = LUINORM_NO;
                // disable flow button
                flowB_.setEnabled(false);
                // LuiNorm
                SetNotLvg(InputPanel.GetInLine());
            }
        });
        rb_[WORDIND_NO].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                curProgram_ = WORDIND_NO;
                // disable flow button
                flowB_.setEnabled(false);
                // WordInd
                SetNotLvg(InputPanel.GetInLine());
            }
        });
        rb_[TO_ASCII_NO].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                curProgram_ = TO_ASCII_NO;
                // disable flow button
                flowB_.setEnabled(false);
                // TBD: disable input Option panel
                // ToAscii
                SetNotLvg(InputPanel.GetInLine());
            }
        });
        // run button
        runB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // mutate and output the results 
                LvgGlobal.LvgMutate(curProgram_);
            }
        });
        flowB_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                FlowSetupDialog.ShowDialog(owner_);
            }
        });
        optionB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                MutateOptionDialog.ShowDialog(owner_);
            }
        });
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // set Lvg setelected
                rb_[LVG_NO].setSelected(true);
                flowB_.setEnabled(true);
                // reset the lvg commands
                LvgGlobal.cmd_.Reset();
                LvgGlobal.cmd_.UpdateCmdStr();
                cmdField_.setText(LvgGlobal.cmd_.GetCmdStr());
            }
        });
    }
    // return this mutate panel
    public static JPanel GetPanel(JFrame owner)
    {
        if(mutateP_ == null)
        {
            mutateP_ = new MutatePanel(owner);
        }
        return mutateP_;
    }
    // show command box or not
    public static void SetShowCommand(boolean showCmd)
    {
        cmd_.setVisible(showCmd);
    }
    // set command box editable or not
    public static void SetCommandEditable(boolean setCmdEditable)
    {
        cmdField_.setEditable(setCmdEditable);
    }
    // update lvg command
    public static void SetCommand(String cmd)
    {
        cmdField_.setText(cmd);
    }
    // get command text field
    public static JTextField GetCmdField()
    {
        return cmdField_;
    }
    public static void SetNotLvg(String inLine)
    {
        if(rb_[NORM_NO].isSelected() == true) // Norm
        {
            LvgGlobal.cmd_.Reset();
            LvgGlobal.cmd_.AddFlowComponent("-f:N");
            // automatic detect output fields
            String fieldStr = GetFieldStr(inLine);
            LvgGlobal.cmd_.AddOutputOption("-F:" + fieldStr);
            LvgGlobal.UpdateCmdStr();
            // update output options in outputOptionD and LvgGlobal.ouputOption
            OutputOptionDialog.SetOutputFiledsOption(fieldStr);
        }
        else if(rb_[LUINORM_NO].isSelected() == true) // LuiNorm
        {
            LvgGlobal.cmd_.Reset();
            LvgGlobal.cmd_.AddFlowComponent("-f:N3");
            // automatic detect output fields
            String fieldStr = GetFieldStr(inLine);
            LvgGlobal.cmd_.AddOutputOption("-F:" + fieldStr);
            LvgGlobal.UpdateCmdStr();
            // update output options in outputOptionD and LvgGlobal.ouputOption
            OutputOptionDialog.SetOutputFiledsOption(fieldStr);
        }
        else if(rb_[WORDIND_NO].isSelected() == true)    // wordInd
        {
            LvgGlobal.cmd_.Reset();
            LvgGlobal.cmd_.AddFlowComponent("-f:l:c");
            // automatic detect output fields
            String fieldStr = GetLastFieldStr(inLine);
            LvgGlobal.cmd_.AddOutputOption("-F:" + fieldStr);
            LvgGlobal.UpdateCmdStr();
            // update output options in outputOptionD and LvgGlobal.ouputOption
            OutputOptionDialog.SetOutputFiledsOption(fieldStr);
        }
        else if(rb_[TO_ASCII_NO].isSelected() == true)    // ToAscii
        {
            LvgGlobal.cmd_.Reset();
            LvgGlobal.cmd_.AddFlowComponent("-f:q7:q8");
            // automatic detect output fields
            //String fieldStr = GetLastFieldStr(inLine);
            //LvgGlobal.cmd_.AddOutputOption("-F:" + fieldStr);
            LvgGlobal.UpdateCmdStr();
            // update output options in outputOptionD and LvgGlobal.ouputOption
            //OutputOptionDialog.SetOutputFiledsOption(fieldStr);
        }
    }
    public static int GetCurProgram()
    {
        return curProgram_;
    }
    // private method
    private static String GetLastFieldStr(String inLine)
    {
        int curField = 1;
        int fromIndex = -1;
        curField++;
        while((fromIndex = inLine.indexOf(LvgGlobal.separator_, fromIndex+1)) 
            > 0)
        {
            curField++;
        }
        return Integer.toString(curField);
    }
    private static String GetFieldStr(String inLine)
    {
        // check to see the number of separator
        int curField = 1;
        String fieldStr = curField + ":";
        curField++;
        int fromIndex = -1;
        while((fromIndex = inLine.indexOf(LvgGlobal.separator_, fromIndex+1)) 
            > 0)
        {
            fieldStr += curField + ":";
            curField++;
        }
        fieldStr += curField;
        return fieldStr;
    }
    // private data
    private static JTextField cmdField_ = null;        // lvg command
    // make lvg command line triggerable
    private static Box cmd_ = null;        // lvg command layout box
  
    final public static int LVG_NO = 0;
    final public static int NORM_NO = 1;
    final public static int LUINORM_NO = 2;
    final public static int WORDIND_NO = 3;
    final public static int TO_ASCII_NO = 4;
      private static int curProgram_ = LVG_NO;
    final private static int TOTAL_NUM = 5;
    // tool selection: lvg, norm, luinorm, wordInd
    private static JRadioButton[] rb_ = new JRadioButton[TOTAL_NUM];
    private static ButtonGroup rbGroup_ = new ButtonGroup();
    private static JFrame owner_ = null;
    private static MutatePanel mutateP_ = null;
    private static JButton flowB_ = new JButton("Flow Setup");
    static
    {
        rb_[LVG_NO] = new JRadioButton("Lvg");
        rb_[NORM_NO] = new JRadioButton("Norm");
        rb_[LUINORM_NO] = new JRadioButton("LuiNorm");
        rb_[WORDIND_NO] = new JRadioButton("WordInd");
        rb_[TO_ASCII_NO] = new JRadioButton("ToAscii");
        rb_[LVG_NO].setSelected(true);
        rbGroup_.add(rb_[LVG_NO]);
        rbGroup_.add(rb_[NORM_NO]);
        rbGroup_.add(rb_[LUINORM_NO]);
        rbGroup_.add(rb_[WORDIND_NO]);
        rbGroup_.add(rb_[TO_ASCII_NO]);
    }
    private final static long serialVersionUID = 5L;
}
