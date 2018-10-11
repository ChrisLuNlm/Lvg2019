package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
* This class show the output option for lexcial tool.
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
public class OutputOptionDialog extends JDialog
{
    private OutputOptionDialog(JFrame owner)
    {
        super(owner, "Lvg Output Options", true);
        owner_ = owner;
        // Geometry Setting
        setLocation(new Point(100, 200));
        setSize(1000, 700);
        // top panel
        JPanel topP = new JPanel();
        topP.add(new JLabel("Please select output options:"));
        // option
        JPanel optionP = new JPanel();
        optionP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Output Options"));
        GridBagLayout gbl = new GridBagLayout();
        optionP.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);
        GridBag.SetWeight(gbc, 100, 100);
        
        for(int i = 0; i < SIZE-1; i++)
        {
            GridBag.SetPosSize(gbc, 0, i, 1, 1);
            optionP.add(cb_[i], gbc);
            if(valueTFlag_[i] == true)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                optionP.add(valueT_[i], gbc);
            }
            
            // add setting button
            if(setBFlag_[i] == true)
            {
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                switch(i)
                {
                    case LvgDef.OUT_St:
                        optionP.add(sortCombo_, gbc);
                        break;
                    case LvgDef.OUT_C:
                        optionP.add(caseCombo_, gbc);
                        break;
                    case LvgDef.OUT_CR:
                        optionP.add(combineCombo_, gbc);
                        break;
                    default:
                        optionP.add(setB_[i], gbc);
                        break;
                }
            }
            GridBag.SetPosSize(gbc, 3, i, 1, 1);
            optionP.add(helpB_[i], gbc);
        }
        // target panel
        GridBag.SetWeight(gbc, 100, 100);
        JPanel targetP = new JPanel();
        targetP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Output Target Options"));
        targetP.setLayout(gbl);
        for(int i = 0; i < OUTPUT_NUM; i++)
        {
            GridBag.SetPosSize(gbc, i, 0, 1, 1);
            targetP.add(rb_[i], gbc);
        }
        GridBag.SetPosSize(gbc, OUTPUT_NUM, 0, 1, 1);
        targetP.add(valueT_[LvgDef.OUT_o], gbc);
        GridBag.SetPosSize(gbc, OUTPUT_NUM+1, 0, 1, 1);
        targetP.add(appendCb_, gbc);
        GridBag.SetPosSize(gbc, OUTPUT_NUM+2, 0, 1, 1);
        targetP.add(setB_[LvgDef.OUT_o], gbc);
        GridBag.SetPosSize(gbc, OUTPUT_NUM+3, 0, 1, 1);
        targetP.add(helpB_[LvgDef.OUT_o], gbc);
        // center: 2 sub panels
        JPanel centerP = new JPanel();
        centerP.setLayout(new GridBagLayout());
        gbc.insets = new Insets(6, 12, 6, 12);  //Pad: Top, Left, Bottom, Right
        GridBag.SetPosSize(gbc, 1, 1, 1, 1);
        centerP.add(optionP, gbc);
        GridBag.SetPosSize(gbc, 1, 2, 1, 1);
        centerP.add(targetP, gbc);
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
        rb_[SCREEN_OUTPUT].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                valueT_[LvgDef.OUT_o].setEditable(false);
            }
        });
        rb_[FILE_OUTPUT].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                valueT_[LvgDef.OUT_o].setEditable(true);
            }
        });
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // go through all check boxes
                for(int i = 0; i < SIZE; i++)
                {
                    boolean flag = cb_[i].isSelected();
                    String valueStr = new String();
                    if(valueTFlag_[i] == true)
                    {
                        valueStr = valueT_[i].getText();
                    }
                    switch(i)
                    {
                        case LvgDef.OUT_SC:
                            LvgGlobal.outputOption_.SetShowCategoryStrFlag(flag);
                            break;
                        case LvgDef.OUT_SI:
                            LvgGlobal.outputOption_.SetShowInflectionStrFlag(flag);
                            break;
                        case LvgDef.OUT_ti:
                            LvgGlobal.outputOption_.SetFilterInputFlag(flag);
                            break;
                        case LvgDef.OUT_ccgi:
                            LvgGlobal.outputOption_.SetMarkEndFlag(flag);
                            LvgGlobal.outputOption_.SetMarkEndStr(valueStr);
                            break;
                        case LvgDef.OUT_n:
                            LvgGlobal.outputOption_.SetNoOutputFlag(flag);
                            LvgGlobal.outputOption_.SetNoOutputStr(valueStr);
                            break;
                        case LvgDef.OUT_R:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetOutRecordNum(
                                    Integer.parseInt(valueStr));
                            }
                            else    // default
                            {
                                LvgGlobal.outputOption_.SetOutRecordNum(-1);
                            }
                            break;
                        case LvgDef.OUT_St:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetSortFlag(
                                    sortCombo_.getSelectedIndex()+1);
                            }
                            else
                            {
                                LvgGlobal.outputOption_.SetSortFlag(
                                    LexItemComparator.NONE);
                            }
                            break;
                        case LvgDef.OUT_C:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetCaseFlag(
                                    Integer.parseInt(valueStr));
                            }
                            else        // default
                            {
                                LvgGlobal.outputOption_.SetCaseFlag(
                                    OutputFilter.PRESERVED_CASE);
                            }
                            break;
                        case LvgDef.OUT_CR:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetCombineRule(
                                    combineCombo_.getSelectedIndex()+1);
                            }
                            else
                            {
                                LvgGlobal.outputOption_.SetCombineRule(
                                    CombineRecords.BY_NONE);
                            }
                            break;
                        case LvgDef.OUT_DC:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetOutCategory(
                                    Long.parseLong(valueStr));
                            }
                            else
                            {
                                LvgGlobal.outputOption_.SetOutCategory(
                                    Category.ALL_BIT_VALUE);
                            }
                            break;
                        case LvgDef.OUT_DI:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetOutInflection(
                                    Long.parseLong(valueStr));
                            }
                            else
                            {
                                LvgGlobal.outputOption_.SetOutInflection(
                                    Inflection.ALL_BIT_VALUE);
                            }
                            break;
                        case LvgDef.OUT_EC:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetExcludeCategory(
                                    Long.parseLong(valueStr));
                            }
                            else
                            {
                                LvgGlobal.outputOption_.SetExcludeCategory(
                                    Category.NO_BIT_VALUE);
                            }
                            break;
                        case LvgDef.OUT_EI:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetExcludeInflection(
                                    Long.parseLong(valueStr));
                            }
                            else
                            {
                                LvgGlobal.outputOption_.SetExcludeInflection(
                                    Inflection.NO_BIT_VALUE);
                            }
                            break;
                        case LvgDef.OUT_F:
                            if(flag == true)
                            {
                                LvgGlobal.outputOption_.SetOutputFieldList(
                                    FieldSpecifierDialog.GetFieldList());
                            }
                            else
                            {
                                LvgGlobal.outputOption_.SetOutputFieldList(
                                    new Vector<Integer>());
                            }
                            break;
                    }
                }
                // Command line syntax:
                LvgGlobal.cmd_.ResetOutputOptions();
                for(int i = 0; i < SIZE; i++)
                {
                    if(cb_[i].isSelected() == true)
                    {
                        if(valueTFlag_[i] == true)
                        {
                            if((i == LvgDef.OUT_ccgi) 
                            || (i == LvgDef.OUT_n))
                            {
                                LvgGlobal.cmd_.AddOutputOption("-" 
                                    + LvgDef.OUT_PURE_OPT_FLAG[i]); 
                            }
                            else
                            {
                                LvgGlobal.cmd_.AddOutputOption("-" 
                                    + LvgDef.OUT_PURE_OPT_FLAG[i] 
                                    + ":" + valueT_[i].getText());
                            }
                        }
                        else
                        {
                            LvgGlobal.cmd_.AddOutputOption("-" 
                                + LvgDef.OUT_PURE_OPT_FLAG[i]);
                        }
                    }
                }
                LvgGlobal.UpdateCmdStr();
                
                // change output in output panel
                LvgGlobal.outputToScreen_ = rb_[SCREEN_OUTPUT].isSelected();
                LvgGlobal.outAppendFlag_ = appendCb_.isSelected();
                // Apply output filter option if input from and output to screen
                if((LvgGlobal.inputFromScreen_ == true)
                && (LvgGlobal.outputToScreen_ == true))
                {
                    LvgGlobal.UpdateOutputStrings(LvgGlobal.GetOutputLexItems(),
                        InputPanel.GetInLine(), false);
                    // update result in output panel
                    OutputPanel.UpdateResult();
                }
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
                // options
                for(int i = 0; i < SIZE; i++)
                {
                    valueT_[i].setText(defaults_[i]);
                    cb_[i].setSelected(false);
                }
                sortCombo_.setSelectedIndex(0);
                caseCombo_.setSelectedIndex(0);
                combineCombo_.setSelectedIndex(0);
                // target
                rb_[SCREEN_OUTPUT].setSelected(true);
                appendCb_.setSelected(false);
            }
        });
        // set value buttons
        sortCombo_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = sortCombo_.getSelectedIndex();
                String sortStr = "o";
                switch(index)
                {
                    case 0:
                        sortStr = "o";
                        break;
                    case 1:
                        sortStr = "oc";
                        break;
                    case 2:
                        sortStr = "oci";
                        break;
                }
                valueT_[LvgDef.OUT_St].setText(sortStr);
            }
        });
        caseCombo_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = caseCombo_.getSelectedIndex() + 1;
                valueT_[LvgDef.OUT_C].setText(Integer.toString(index));
            }
        });
        combineCombo_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = combineCombo_.getSelectedIndex();
                String crStr = "o";
                switch(index)
                {
                    case 0:
                        crStr = "o";
                        break;
                    case 1:
                        crStr = "oc";
                        break;
                    case 2:
                        crStr = "oi";
                        break;
                    case 3:
                        crStr = "oe";
                        break;
                }
                valueT_[LvgDef.OUT_CR].setText(crStr);
            }
        });
        setB_[LvgDef.OUT_F].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display category selections
                FieldSpecifierDialog.ShowDialog(owner_, valueT_[LvgDef.OUT_F]);
            }
        });
        setB_[LvgDef.OUT_DC].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display category selections
                CategoryDialog.ShowDialog(owner_, valueT_[LvgDef.OUT_DC]);
            }
        });
        setB_[LvgDef.OUT_DI].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display category selections
                InflectionDialog.ShowDialog(owner_, valueT_[LvgDef.OUT_DI]);
            }
        });
        setB_[LvgDef.OUT_EC].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display category selections
                CategoryDialog.ShowDialog(owner_, valueT_[LvgDef.OUT_EC]);
            }
        });
        setB_[LvgDef.OUT_EI].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display category selections
                InflectionDialog.ShowDialog(owner_, valueT_[LvgDef.OUT_EI]);
            }
        });
        setB_[LvgDef.OUT_o].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display output file selection dialog
                JFileChooser dialog = new JFileChooser();
                dialog.setSelectedFile(LvgGlobal.outFile_);
                dialog.setDialogTitle("Choose the output file");
                dialog.setMultiSelectionEnabled(false);
                if(dialog.showDialog(owner_, "Select")
                    == JFileChooser.APPROVE_OPTION)
                {
                    LvgGlobal.outFile_ = dialog.getSelectedFile();
                    SetOutputFile(LvgGlobal.outFile_);
                }
            }
        });
        helpB_[LvgDef.OUT_SC].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_SC);
            }
        });
        helpB_[LvgDef.OUT_SI].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_SI);
            }
        });
        helpB_[LvgDef.OUT_ti].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_ti);
            }
        });
        helpB_[LvgDef.OUT_ccgi].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_ccgi);
            }
        });
        helpB_[LvgDef.OUT_n].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_n);
            }
        });
        helpB_[LvgDef.OUT_R].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_R);
            }
        });
        helpB_[LvgDef.OUT_St].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_St);
            }
        });
        helpB_[LvgDef.OUT_C].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_C);
            }
        });
        helpB_[LvgDef.OUT_CR].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_CR);
            }
        });
        helpB_[LvgDef.OUT_DC].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_DC);
            }
        });
        helpB_[LvgDef.OUT_DI].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_DI);
            }
        });
        helpB_[LvgDef.OUT_EC].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_EC);
            }
        });
        helpB_[LvgDef.OUT_EI].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_EI);
            }
        });
        helpB_[LvgDef.OUT_F].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_F);
            }
        });
        helpB_[LvgDef.OUT_o].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.OUT_o);
            }
        });
    }
    // public methods
    public static void SetOutputFiledsOption(String option)
    {
        if(option.length() <= 0)
        {
            cb_[LvgDef.OUT_F].setSelected(false);
        }
        else
        {
            cb_[LvgDef.OUT_F].setSelected(true);
        }
        valueT_[LvgDef.OUT_F].setText(option);
        FieldSpecifierDialog.SetFieldStr(option);
        LvgGlobal.outputOption_.SetOutputFieldList(
            FieldSpecifierDialog.GetFieldList());
    }
    public static void ShowDialog(JFrame owner)
    {
        if(outOptionD_ == null)
        {
            outOptionD_ = new OutputOptionDialog(owner);
        }
        StoreStatus();
        outOptionD_.setVisible(true);
    }
    public static void SetOutputFile(File file)
    {
        valueT_[LvgDef.OUT_o].setText(LvgGlobal.outFile_.getPath());
    }
    // private method
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
        // combo box
        sortCombo_.setSelectedIndex(
            GetSortComboIndex(valueT_[LvgDef.OUT_St].getText()));
        caseCombo_.setSelectedIndex(
            Integer.parseInt(valueT_[LvgDef.OUT_C].getText())-1);
        combineCombo_.setSelectedIndex(
            GetCombineComboIndex(valueT_[LvgDef.OUT_CR].getText()));
        // output target
        rb_[SCREEN_OUTPUT].setSelected(LvgGlobal.outputToScreen_);
        rb_[FILE_OUTPUT].setSelected(!LvgGlobal.outputToScreen_);
        appendCb_.setSelected(LvgGlobal.outAppendFlag_);
    }
    private static int GetSortComboIndex(String value)
    {
        int index = 0;
        if(value.equals("o"))
        {
            index = 0;
        }
        else if(value.equals("oc"))
        {
            index = 1;
        }
        else if(value.equals("oci"))
        {
            index = 2;
        }
        return index;
    }
    private static int GetCombineComboIndex(String value)
    {
        int index = 0;
        if(value.equals("o"))
        {
            index = 0;
        }
        else if(value.equals("oc"))
        {
            index = 1;
        }
        else if(value.equals("oi"))
        {
            index = 2;
        }
        else if(value.equals("oe"))
        {
            index = 3;
        }
        return index;
    }
    private void ShowHelp(int index)
    {
        String lvgDir = LvgGlobal.config_.GetConfiguration(
            Configuration.LVG_DIR);
        String optionUrl = "file:" + lvgDir +
            "docs/designDoc/LifeCycle/requirement/lvgOptions/"
            + LvgDef.OUT_OPT_DOC[index];
        // display help document
        if(helpDoc_ == null)
        {
            String title = "Output Filter Option Documents";
            String homeUrl = "file:" + lvgDir +
                "docs/designDoc/LifeCycle/requirement/lvgOutputFilter.html";
            helpDoc_ = new LvgHtmlBrowser(owner_, title,
                600, 800, homeUrl, optionUrl);
        }
        else
        {
            helpDoc_.SetPage(optionUrl);
        }
        helpDoc_.setVisible(true);
    }
    // private data
    private static int SIZE = LvgDef.OUT_OPT_NUM;
    private static JCheckBox[] cb_ = new JCheckBox[SIZE];
    private static JButton[] setB_ = new JButton[SIZE];
    private static JButton[] helpB_ = new JButton[SIZE];
    private static JComboBox<String> sortCombo_ = new JComboBox<String>();
    private static JComboBox<String> caseCombo_ = new JComboBox<String>();
    private static JComboBox<String> combineCombo_ = new JComboBox<String>();
    private static JTextField[] valueT_ = new JTextField[SIZE];
    private static String[] defaults_ = new String[SIZE];
    private static String[] cancelText_ = new String[SIZE];
    private static boolean[] cancelCb_ = new boolean[SIZE];
    private static int[] size_ = new int[SIZE];
    private static boolean[] valueTFlag_ =     //value text field button
        {false, false, false, true, true, 
         true, true, true, true, true,
         true, true, true, true, false}; 
    private static boolean[] setBFlag_ =         //Set value button
        {false, false, false, false, false, 
         false, true, true, true, true,
         true, true, true, true, true}; 
    // target
    final private static int OUTPUT_NUM = 2;
    final private static int SCREEN_OUTPUT = 0;
    final private static int FILE_OUTPUT = 1;
    private static JRadioButton[] rb_ = new JRadioButton[OUTPUT_NUM];
    private static ButtonGroup rbGroup_ = null;     // Output target options
    private static JFrame owner_ = null;
    private static OutputOptionDialog outOptionD_ = null;
    private static JCheckBox appendCb_ = new JCheckBox("Append");
    private static LvgHtmlBrowser helpDoc_ = null;
    static
    {
        defaults_[LvgDef.OUT_ccgi] = 
            LvgGlobal.config_.GetConfiguration(Configuration.CCGI);
        defaults_[LvgDef.OUT_n] = 
            LvgGlobal.config_.GetConfiguration(Configuration.NO_OUTPUT);
        defaults_[LvgDef.OUT_R] = LvgGlobal.config_.GetConfiguration(
                Configuration.MAX_RESULT);
        defaults_[LvgDef.OUT_St] = "o";
        defaults_[LvgDef.OUT_C] = "1";
        defaults_[LvgDef.OUT_CR] = "o";
        defaults_[LvgDef.OUT_DC] = "0";
        defaults_[LvgDef.OUT_DI] = "0";
        defaults_[LvgDef.OUT_EC] = "0";
        defaults_[LvgDef.OUT_EI] = "0";
        defaults_[LvgDef.OUT_o] = LvgGlobal.outFile_.getPath();
        size_[LvgDef.OUT_ccgi] = 10;
        size_[LvgDef.OUT_n] = 10;
        size_[LvgDef.OUT_R] = 3;
        size_[LvgDef.OUT_St] = 3;
        size_[LvgDef.OUT_C] = 3;
        size_[LvgDef.OUT_CR] = 3;
        size_[LvgDef.OUT_DC] = 4;
        size_[LvgDef.OUT_DI] = 8;
        size_[LvgDef.OUT_EC] = 4;
        size_[LvgDef.OUT_EI] = 8;
        size_[LvgDef.OUT_F] = 10;
        size_[LvgDef.OUT_o] = 40;
        for(int i = 0; i < SIZE; i++)
        {
            cb_[i] = new JCheckBox(LvgDef.OUT_OPT[i] + " (-" 
                + LvgDef.OUT_OPT_FLAG[i] + ")");
            setB_[i] = new JButton("Setting");
            helpB_[i] = new JButton("Help");
            valueT_[i] = new JTextField(defaults_[i], size_[i]);
        }
        valueT_[LvgDef.OUT_St].setEditable(false);
        valueT_[LvgDef.OUT_C].setEditable(false);
        valueT_[LvgDef.OUT_CR].setEditable(false);
        valueT_[LvgDef.OUT_o].setEditable(false);
        // target: radio buttons
        rb_[SCREEN_OUTPUT] = new JRadioButton("Screen");
        rb_[FILE_OUTPUT] = new JRadioButton("File");
        rb_[SCREEN_OUTPUT].setSelected(true);
        rbGroup_ = new ButtonGroup();
        rbGroup_.add(rb_[SCREEN_OUTPUT]);
        rbGroup_.add(rb_[FILE_OUTPUT]);
        sortCombo_.addItem("By term");
        sortCombo_.addItem("By term & category");
        sortCombo_.addItem("By term, category, & inflection");
        sortCombo_.setSelectedIndex(0);
        caseCombo_.addItem("Preserve case");
        caseCombo_.addItem("Lower case");
        caseCombo_.addItem("Upper case");
        caseCombo_.setSelectedIndex(0);
        combineCombo_.addItem("By term");
        combineCombo_.addItem("By term & category");
        combineCombo_.addItem("By term & infelction");
        combineCombo_.addItem("By term & Eui");
        combineCombo_.setSelectedIndex(0);
    }
    private final static long serialVersionUID = 5L;
}
