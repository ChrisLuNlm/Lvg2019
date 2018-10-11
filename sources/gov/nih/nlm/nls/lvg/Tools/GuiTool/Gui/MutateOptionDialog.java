package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
* This class show the mutate option for lexcial tool.
* The constructor is private since no instance object should be generated.
* ShowDialog( ) should be called when use this dialog.
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class MutateOptionDialog extends JDialog
{
    private MutateOptionDialog(JFrame owner)
    {
        super(owner, "Lvg Mutate Options", true);
        owner_ = owner;
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(800, 550);
        // top panel
        JPanel topP = new JPanel();
        topP.add(new JLabel("Please select mutate options:"));
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);
        GridBag.SetWeight(gbc, 100, 100);
        // flow option panel
        JPanel flowP = new JPanel();
        flowP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Flow Specific Options"));
        flowP.setLayout(gbl);
        for(int i = 0; i < FO_SIZE; i++)
        {
            GridBag.SetPosSize(gbc, 0, i, 1, 1);
            flowP.add(cb_[i], gbc);
            if(valueTFlag_[i] == true)    // value button
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                flowP.add(valueT_[i], gbc);
            }
            if(setBFlag_[i] == true)    // setting button
            {
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                if(i == LvgDef.FO_kd)
                {
                    flowP.add(kdCombo_, gbc);
                }
                else if(i == LvgDef.FO_kdn)
                {
                    flowP.add(kdnCombo_, gbc);
                }
                else if(i == LvgDef.FO_kdt)
                {
                    flowP.add(kdtCombo_, gbc);
                }
                else if (i == LvgDef.FO_ki)
                {
                    flowP.add(kiCombo_, gbc);
                }
                else if (i == LvgDef.FO_ks)
                {
                    flowP.add(ksCombo_, gbc);
                }
                else
                {
                    flowP.add(setB_[i], gbc);
                }
            }
            if(helpBFlag_[i] == true)    // help button
            {
                GridBag.SetPosSize(gbc, 3, i, 1, 1);
                flowP.add(helpB_[i], gbc);
            }
        }
        // global behavior option panel
        JPanel globalP = new JPanel();
        globalP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Global Behavior Options"));
        globalP.setLayout(gbl);
        for(int i = 0; i < GB_SIZE; i++)
        {
            int ii = i + FO_SIZE;
            GridBag.SetPosSize(gbc, 0, i, 1, 1);
            globalP.add(cb_[ii], gbc);
            if(valueTFlag_[ii] == true)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                globalP.add(valueT_[ii], gbc);
            }
            if(setBFlag_[ii] == true)
            {
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                globalP.add(setB_[ii], gbc);
            }
            if(helpBFlag_[ii] == true)
            {
                GridBag.SetPosSize(gbc, 3, i, 1, 1);
                globalP.add(helpB_[ii], gbc);
            }
        }
        // center: 2 sub panles
        JPanel centerP = new JPanel();
        centerP.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 12, 6, 12);  //Pad: Top, Left, Bottom, Right
        GridBag.SetPosSize(gbc, 1, 1, 1, 1);
        centerP.add(flowP, gbc);
        GridBag.SetPosSize(gbc, 1, 2, 1, 1);
        centerP.add(globalP, gbc);
        // bottom panel
        OkCancelResetButtonPanel bottomP = new OkCancelResetButtonPanel();
        JButton okB = bottomP.GetOkButton();
        JButton cancelB = bottomP.GetCancelButton();
        JButton resetB = bottomP.GetResetButton();
        // top level
        getContentPane().add(topP, "North");
        getContentPane().add(centerP, "Center");
        getContentPane().add(bottomP, "South");
        // inner class for action listener
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // flow specific options
                for(int i = 0; i < FO_SIZE; i++)
                {
                    boolean flag = cb_[i].isSelected();
                    String valueStr = new String();
                    if(valueTFlag_[i] == true)
                    {
                        valueStr = valueT_[i].getText();
                    }
                    switch(i)
                    {
                        case LvgDef.FO_MIN_TERM_LENGTH:
                            int minTermLength = Integer.parseInt(valueStr);
                            LvgGlobal.lvg_.SetMinTermLength(minTermLength);
                            break;
                        case LvgDef.FO_MAX_PERMUTE_TERM:
                            int maxPermuteTerm = Integer.parseInt(valueStr);
                            LvgGlobal.lvg_.GetFlowSpecificOptions()
                                .SetMaxPermuteTermNum(maxPermuteTerm);
                            break;
                        case LvgDef.FO_MAX_METAPHONE:
                            int maxCodeLength = Integer.parseInt(valueStr);
                            LvgGlobal.lvg_.GetFlowSpecificOptions()
                                .SetMaxMetaphoneCodeLength(maxCodeLength);
                            break;
                        case LvgDef.FO_kd:
                            // defualt value for checkbox is not checked
                            LvgGlobal.lvg_.GetFlowSpecificOptions()
                                .SetDerivationFilter(OutputFilter.LVG_ONLY);
                            break;
                        case LvgDef.FO_kdn:
                            // defualt value for checkbox is not checked
                            LvgGlobal.lvg_.GetFlowSpecificOptions()
                                .SetDerivationNegation(
                                LvgFlowSpecificOption.KDN_O);
                            break;
                        case LvgDef.FO_kdt:
                            // defualt value for checkbox is not checked
                            LvgGlobal.lvg_.GetFlowSpecificOptions()
                                .SetDerivationType(
                                LvgFlowSpecificOption.KDT_ZSP);
                            break;
                        case LvgDef.FO_ki:
                            // defualt value for checkbox is not checked
                            LvgGlobal.lvg_.GetFlowSpecificOptions()
                                .SetInflectionFilter(OutputFilter.LVG_OR_ALL);
                            break;
                        case LvgDef.FO_ks:
                            // defualt value for checkbox is not checked
                            LvgGlobal.lvg_.GetFlowSpecificOptions()
                                .SetSynonymFilter(
                                LvgFlowSpecificOption.KS_CEN);
                            break;
                    }
                }
                // global behavior options
                for(int i = 0; i < GB_SIZE; i++)
                {
                    int ii = i + FO_SIZE;
                    boolean flag = cb_[ii].isSelected();
                    String valueStr = new String();
                    if(valueTFlag_[ii] == true)
                    {
                        valueStr = valueT_[ii].getText();
                    }
                    switch(i)
                    {
                        case LvgDef.GB_m:
                            LvgGlobal.mutateFlag_ = flag;
                            break;
                        case LvgDef.GB_d:
                            LvgGlobal.detailsFlag_ = flag;
                            break;
                        case LvgDef.GB_s:
                            if(flag == true)
                            {
                                LvgGlobal.separator_ = valueStr;
                            }
                            else
                            {
                                LvgGlobal.separator_ = defaults_[ii];
                            }
                            break;
                    }
                }
                // command line syntax
                LvgGlobal.cmd_.ResetGlobalOptions();    // reset
                // update value when checkbox is checked
                for(int i = LvgDef.FO_kd; i < FO_SIZE; i++)
                {
                    if(cb_[i].isSelected() == true)
                    {
                        LvgGlobal.cmd_.AddGlobalOption("-" 
                            + LvgDef.FLOW_SPECIFIC_PURE_OPT_FLAG[i]
                            + ":" + valueT_[i].getText());
                    }
                }
                for(int i = FO_SIZE; i < SIZE; i++)
                {
                    int ii = i - FO_SIZE;
                    if(cb_[i].isSelected() == true)
                    {
                        if(valueTFlag_[i] == true)
                        {
                            LvgGlobal.cmd_.AddGlobalOption("-" 
                                + LvgDef.GLOBAL_BEHAVIOR_PURE_OPT_FLAG[ii]
                                + ":" + valueT_[i].getText());
                        }
                        else
                        {
                            LvgGlobal.cmd_.AddGlobalOption("-" 
                                + LvgDef.GLOBAL_BEHAVIOR_PURE_OPT_FLAG[ii]);
                        }
                    }
                }
                LvgGlobal.UpdateCmdStr();
                
                setVisible(false);
            }
        });
        cancelB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                RetrieveStatus();
                setVisible(false);
            }
        });
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                for(int i = 0; i < SIZE; i++)
                {
                    cb_[i].setSelected(false);
                    valueT_[i].setText(defaults_[i]);
                }
                cb_[LvgDef.FO_MIN_TERM_LENGTH].setSelected(true);
                cb_[LvgDef.FO_MAX_PERMUTE_TERM].setSelected(true);
                cb_[LvgDef.FO_MAX_METAPHONE].setSelected(true);
                kdCombo_.setSelectedIndex(0);
                kdnCombo_.setSelectedIndex(0);
                kdtCombo_.setSelectedIndex(6);
                kiCombo_.setSelectedIndex(1);
                ksCombo_.setSelectedIndex(6);
            }
        });
        cb_[LvgDef.FO_MIN_TERM_LENGTH].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                cb_[LvgDef.FO_MIN_TERM_LENGTH].setSelected(true);
            }
        });
        cb_[LvgDef.FO_MAX_PERMUTE_TERM].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                cb_[LvgDef.FO_MAX_PERMUTE_TERM].setSelected(true);
            }
        });
        cb_[LvgDef.FO_MAX_METAPHONE].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                cb_[LvgDef.FO_MAX_METAPHONE].setSelected(true);
            }
        });
        kdCombo_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = kdCombo_.getSelectedIndex()+1;
                valueT_[LvgDef.FO_kd].setText(Integer.toString(index));
            }
        });
        kdnCombo_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = kdnCombo_.getSelectedIndex();
                valueT_[LvgDef.FO_kdn].setText(kdnValue_[index]);
            }
        });
        kdtCombo_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = kdtCombo_.getSelectedIndex();
                valueT_[LvgDef.FO_kdt].setText(kdtValue_[index]);
            }
        });
        kiCombo_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = kiCombo_.getSelectedIndex()+1;
                valueT_[LvgDef.FO_ki].setText(Integer.toString(index));
            }
        });
        ksCombo_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = ksCombo_.getSelectedIndex();
                valueT_[LvgDef.FO_ks].setText(ksValue_[index]);
            }
        });
        // set value buttons
        helpB_[LvgDef.FO_kd].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowFsHelp(LvgDef.FO_kd);
            }
        });
        helpB_[LvgDef.FO_kdn].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowFsHelp(LvgDef.FO_kdn);
            }
        });
        helpB_[LvgDef.FO_kdt].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowFsHelp(LvgDef.FO_kdt);
            }
        });
        helpB_[LvgDef.FO_ki].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowFsHelp(LvgDef.FO_ki);
            }
        });
        helpB_[LvgDef.FO_ks].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowFsHelp(LvgDef.FO_ks);
            }
        });
        helpB_[FO_SIZE + LvgDef.GB_m].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowGbHelp(LvgDef.GB_m);
            }
        });
        helpB_[FO_SIZE + LvgDef.GB_d].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowGbHelp(LvgDef.GB_d);
            }
        });
        helpB_[FO_SIZE + LvgDef.GB_s].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowGbHelp(LvgDef.GB_s);
            }
        });
    }
    // public methods
    public static void ShowDialog(JFrame owner)
    {
        if(dialog_ == null)
        {
            dialog_ = new MutateOptionDialog(owner);
        }
        StoreStatus();
        dialog_.setVisible(true);
    }
    // private methods
    private static void StoreStatus()
    {
        for(int i = 0; i < SIZE; i++)
        {
            cancelCb_[i] = cb_[i].isSelected();
            cancelText_[i] = valueT_[i].getText();
        }
    }
    private static void RetrieveStatus()
    {
        for(int i = 0; i < SIZE; i++)
        {
            cb_[i].setSelected(cancelCb_[i]);
            valueT_[i].setText(cancelText_[i]);
        }
        kdCombo_.setSelectedIndex(
            Integer.parseInt(valueT_[LvgDef.FO_kd].getText())-1);
        kdnCombo_.setSelectedIndex(
            LvgFlowSpecificOption.GetDerivationNegationInt(
            valueT_[LvgDef.FO_kdn].getText()));
        kdtCombo_.setSelectedIndex(LvgFlowSpecificOption.GetDerivationTypeInt(
            valueT_[LvgDef.FO_kdt].getText()));
        kiCombo_.setSelectedIndex(
            Integer.parseInt(valueT_[LvgDef.FO_ki].getText())-1);
        ksCombo_.setSelectedIndex(LvgFlowSpecificOption.GetSynonymFilterInt(
            valueT_[LvgDef.FO_ks].getText()));
    }
    private void ShowFsHelp(int index)
    {
        String lvgDir = LvgGlobal.config_.GetConfiguration(
            Configuration.LVG_DIR);
        String optionUrl = "file:" + lvgDir + 
            "docs/designDoc/LifeCycle/requirement/lvgOptions/"
            + LvgDef.FLOW_SPECIFIC_OPT_DOC[index];
        String homeUrl = "file:" + lvgDir +
            "docs/designDoc/LifeCycle/requirement/lvgFlowOption.html";
        String title = "Flow Specific Option Documents";
        // display help document
        if(helpDoc_ == null)
        {
            helpDoc_ = new LvgHtmlBrowser(owner_, title,
                600, 800, homeUrl, optionUrl);
        }
        else
        {
            helpDoc_.setTitle(title);
            helpDoc_.SetHome(homeUrl);
            helpDoc_.SetPage(optionUrl);
        }
        helpDoc_.setVisible(true);
    }
    private void ShowGbHelp(int index)
    {
        String lvgDir = LvgGlobal.config_.GetConfiguration(
            Configuration.LVG_DIR);
        String optionUrl = "file:" + lvgDir + 
            "docs/designDoc/LifeCycle/requirement/lvgOptions/"
            + LvgDef.GLOBAL_BEHAVIOR_OPT_DOC[index];
        String homeUrl = "file:" + lvgDir +
            "docs/designDoc/LifeCycle/requirement/lvgGlobalBehavior.html";
        String title = "Global Behavior Option Documents";
        // display help document
        if(helpDoc_ == null)
        {
            helpDoc_ = new LvgHtmlBrowser(owner_, title,
                600, 800, homeUrl, optionUrl);
        }
        else
        {
            helpDoc_.setTitle(title);
            helpDoc_.SetHome(homeUrl);
            helpDoc_.SetPage(optionUrl);
        }
        helpDoc_.setVisible(true);
    }
    // private data
    private static int FO_SIZE = LvgDef.FLOW_SPECIFIC_OPT_NUM;    // flow option
    private static int GB_SIZE = LvgDef.GLOBAL_BEHAVIOR_OPT_NUM;// Global
    private static int SIZE = FO_SIZE + GB_SIZE;
    private static JCheckBox[] cb_ = new JCheckBox[SIZE];
    private static JButton[] setB_ = new JButton[SIZE];
    private static JButton[] helpB_ = new JButton[SIZE];
    private static JComboBox<String> kdCombo_ = new JComboBox<String>();
    private static JComboBox<String> kdnCombo_ = new JComboBox<String>();
    private static JComboBox<String> kdtCombo_ = new JComboBox<String>();
    private static JComboBox<String> kiCombo_ = new JComboBox<String>();
    private static JComboBox<String> ksCombo_ = new JComboBox<String>();
    private static JTextField[] valueT_ = new JTextField[SIZE];
    private static String[] defaults_ = new String[SIZE];
    private static String[] cancelText_ = new String[SIZE];
    private static boolean[] cancelCb_ = new boolean[SIZE];
    private static int[] size_ = new int[SIZE];
    private static boolean[] valueTFlag_ =     //value text field button
        {true, true, true, true, true, true, true, true,
         false, false, true}; 
    private static boolean[] setBFlag_ =         //Set value button
        {false, false, false, true, true, true, true, true,
         false, false, false}; 
    private static boolean[] helpBFlag_ =         //Help value button
        {false, false, false, true, true, true, true, true,
         true, true, true}; 
    private static JFrame owner_ = null;
    private static MutateOptionDialog dialog_ = null;
    private static LvgHtmlBrowser helpDoc_ = null;
    private static String[] kdnValue_ =     // value of kdn
        {LvgFlowSpecificOption.KDN_O, LvgFlowSpecificOption.KDN_N,
         LvgFlowSpecificOption.KDN_B};
    private static String[] kdtValue_ =     // value of kdt
        {LvgFlowSpecificOption.KDT_Z, LvgFlowSpecificOption.KDT_S,
         LvgFlowSpecificOption.KDT_P, LvgFlowSpecificOption.KDT_ZS,
         LvgFlowSpecificOption.KDT_ZP, LvgFlowSpecificOption.KDT_SP,
         LvgFlowSpecificOption.KDT_ZSP};
    private static String[] ksValue_ =     // value of ks
        {LvgFlowSpecificOption.KS_C, LvgFlowSpecificOption.KS_E,
         LvgFlowSpecificOption.KS_N, LvgFlowSpecificOption.KS_CE,
         LvgFlowSpecificOption.KS_CN, LvgFlowSpecificOption.KS_EN,
         LvgFlowSpecificOption.KS_CEN};
    static
    {
        defaults_[LvgDef.FO_MIN_TERM_LENGTH] = 
            LvgGlobal.config_.GetConfiguration(Configuration.MIN_TERM_LENGTH);
        defaults_[LvgDef.FO_MAX_PERMUTE_TERM] = 
            LvgGlobal.config_.GetConfiguration(Configuration.MAX_UNINFLS);
        defaults_[LvgDef.FO_MAX_METAPHONE] = 
            LvgGlobal.config_.GetConfiguration(Configuration.MAX_METAPHONE);
        defaults_[LvgDef.FO_kd] = Integer.toString(OutputFilter.LVG_ONLY);
        defaults_[LvgDef.FO_kdn] = LvgFlowSpecificOption.KDN_O;
        defaults_[LvgDef.FO_kdt] = LvgFlowSpecificOption.KDT_ZSP;
        defaults_[LvgDef.FO_ki] = Integer.toString(OutputFilter.LVG_OR_ALL);
        defaults_[LvgDef.FO_ks] = LvgFlowSpecificOption.KS_CEN;
        defaults_[FO_SIZE + LvgDef.GB_s] = GlobalBehavior.FS_STR;
        size_[LvgDef.FO_MIN_TERM_LENGTH] = 3;
        size_[LvgDef.FO_MAX_PERMUTE_TERM] = 3;
        size_[LvgDef.FO_MAX_METAPHONE] = 3;
        size_[LvgDef.FO_kd] = 3;
        size_[LvgDef.FO_kdn] = 3;
        size_[LvgDef.FO_kdt] = 3;
        size_[LvgDef.FO_ki] = 3;
        size_[LvgDef.FO_ks] = 3;
        size_[FO_SIZE + LvgDef.GB_s] = 3;
        for(int i = 0; i < SIZE; i++)
        {
            if(i <= LvgDef.FO_MAX_METAPHONE)    // first 3
            {
                cb_[i] = new JCheckBox(LvgDef.FLOW_SPECIFIC_OPT[i], true);
            }
            else if(i < FO_SIZE)    // 4 to 7
            {
                cb_[i] = new JCheckBox(LvgDef.FLOW_SPECIFIC_OPT[i] + " (-" 
                    + LvgDef.FLOW_SPECIFIC_OPT_FLAG[i] + ")");
            }
            else    // global Behavior
            {
                int ii = i - FO_SIZE;
                cb_[i] = new JCheckBox(LvgDef.GLOBAL_BEHAVIOR_OPT[ii] + " (-" 
                    + LvgDef.GLOBAL_BEHAVIOR_OPT_FLAG[ii] + ")");
            }
            setB_[i] = new JButton("Setting");
            //setB_[i].setPreferredSize(new Dimension(100, 21));
            helpB_[i] = new JButton("Help");    // help botton
            //helpB_[i].setPreferredSize(new Dimension(50, 21));
            valueT_[i] = new JTextField(defaults_[i], size_[i]);
        }
        valueT_[LvgDef.FO_kd].setEditable(false);
        valueT_[LvgDef.FO_kdn].setEditable(false);
        valueT_[LvgDef.FO_kdt].setEditable(false);
        valueT_[LvgDef.FO_ki].setEditable(false);
        valueT_[LvgDef.FO_ks].setEditable(false);
        kdCombo_.addItem("Lexicon only");
        kdCombo_.addItem("Lexicon, then rules");
        kdCombo_.addItem("Lexicon and rules");
        kdCombo_.setSelectedIndex(0);
        kdnCombo_.addItem("Otherwise");
        kdnCombo_.addItem("Negation");
        kdnCombo_.addItem("Both");
        kdnCombo_.setSelectedIndex(0);
        kdtCombo_.addItem("ZeroD");
        kdtCombo_.addItem("SuffixD");
        kdtCombo_.addItem("PrefixD");
        kdtCombo_.addItem("ZeroD & SuffixD");
        kdtCombo_.addItem("ZeroD & PrefixD");
        kdtCombo_.addItem("SuffixD & PrefixD");
        kdtCombo_.addItem("ZeroD, SuffixD, & PrefixD");
        kdtCombo_.setSelectedIndex(6);
        kiCombo_.addItem("Lexicon only");
        kiCombo_.addItem("Lexicon, then rules");
        kiCombo_.addItem("Lexicon and rules");
        kiCombo_.setSelectedIndex(1);
        ksCombo_.addItem("CUI");
        ksCombo_.addItem("EUI");
        ksCombo_.addItem("NLP");
        ksCombo_.addItem("CUI & EUI");
        ksCombo_.addItem("CUI & NLP");
        ksCombo_.addItem("EUI & NLP");
        ksCombo_.addItem("CUI, EUI, & NLP");
        ksCombo_.setSelectedIndex(6);
    }
    private final static long serialVersionUID = 5L;
}
