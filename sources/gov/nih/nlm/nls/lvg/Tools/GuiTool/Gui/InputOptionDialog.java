package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
* This class show the input option for lexcial tool.
* The constructor is private since no instance object should be generated.
* ShowDialog( ) should be called when use this dialog.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class InputOptionDialog extends JDialog
{
    // private constructor
    private InputOptionDialog(JFrame owner)
    {
        super(owner);
        setTitle("Lvg Input Options");
        setModal(true);
        owner_ = owner;
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(420, 480);
        // top panel
        JPanel topP = new JPanel();
        topP.add(new JLabel("Please select input options:"));
        // category and inflection panel
        JPanel catInflP = new JPanel();
        catInflP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Categories and Inflections"));
        GridBagLayout gbl = new GridBagLayout();
        catInflP.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        GridBag.SetWeight(gbc, 100, 100);
        for(int i = 0; i < OFFSET; i++)
        {
            GridBag.SetPosSize(gbc, 0, i, 1, 1);
            catInflP.add(cb_[i], gbc);
            GridBag.SetPosSize(gbc, 1, i, 1, 1);
            catInflP.add(valueT_[i], gbc);
            if(setBFlag_[i] == true)
            {
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                catInflP.add(setB_[i], gbc);
            }
        }
        // input filter panel
        GridBag.SetWeight(gbc, 100, 100);
        JPanel filterP = new JPanel();
        filterP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Input Filter Options"));
        filterP.setLayout(gbl);
        for(int i = OFFSET; i < SIZE-1; i++)
        {
            GridBag.SetPosSize(gbc, 0, i, 1, 1);
            filterP.add(cb_[i], gbc);
            GridBag.SetPosSize(gbc, 1, i, 1, 1);
            filterP.add(valueT_[i], gbc);
            GridBag.SetPosSize(gbc, 2, i, 1, 1);
            filterP.add(helpB_[i], gbc);
        }
        // source panel
        // update input file
        valueT_[LvgDef.IN_i].setText(LvgGlobal.inFile_.getPath());
        GridBag.SetWeight(gbc, 100, 100);
        JPanel sourceP = new JPanel();
        sourceP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Input Source Options"));
        sourceP.setLayout(gbl);
        for(int i = 0; i < INPUT_NUM; i++)
        {
            GridBag.SetPosSize(gbc, i, 0, 1, 1);
            sourceP.add(rb_[i], gbc);
        }
        GridBag.SetPosSize(gbc, INPUT_NUM, 0, 1, 1);
        sourceP.add(setB_[LvgDef.IN_i], gbc);
        GridBag.SetPosSize(gbc, INPUT_NUM+1, 0, 1, 1);
        sourceP.add(helpB_[LvgDef.IN_i], gbc);
        GridBag.SetPosSize(gbc, 1, 1, 3, 1);
        sourceP.add(valueT_[LvgDef.IN_i], gbc);
        // center: 3 sub panels
        JPanel centerP = new JPanel();
        centerP.setLayout(new GridBagLayout());
        gbc.insets = new Insets(6, 12, 6, 12);  //Pad: Top, Left, Bottom, Right
        GridBag.SetPosSize(gbc, 1, 1, 1, 1);
        centerP.add(catInflP, gbc);
        GridBag.SetPosSize(gbc, 1, 2, 1, 1);
        centerP.add(filterP, gbc);
        GridBag.SetPosSize(gbc, 1, 3, 1, 1);
        centerP.add(sourceP, gbc);
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
        rb_[SCREEN_INPUT].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                valueT_[LvgDef.IN_i].setEditable(false);
            }
        });
        rb_[FILE_INPUT].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                valueT_[LvgDef.IN_i].setEditable(true);
            }
        });
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // add input category, inflection to lvg cmd
                LvgGlobal.cmd_.ResetInputOptions();
                // input term, category, and inflection
                for(int i = LvgDef.IN_t; i < SIZE; i++)
                {
                    if(cb_[i].isSelected() == true)
                    {
                        LvgGlobal.cmd_.AddInputOption("-"
                            + LvgDef.INPUT_PURE_OPT_FLAG[i]
                            + ":" + valueT_[i].getText());
                    }
                }
                // update command
                LvgGlobal.UpdateCmdStr();
                
                // update change in the input panel
                InputPanel.SetShowFile(LvgGlobal.inputFromScreen_);
                LvgGlobal.inFile_ = new File(valueT_[LvgDef.IN_i].getText());
                InputPanel.SetFile(LvgGlobal.inFile_.getPath());
                // change input setting
                LvgGlobal.inputFromScreen_ = rb_[SCREEN_INPUT].isSelected();
                setVisible(false);
                SwingUtilities.updateComponentTreeUI(owner_);
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
                // reset category, inflection, & filter options
                ResetCheckBox();
                for(int i = 0; i < SIZE; i++)
                {
                    valueT_[i].setText(defaults_[i]);
                }
                // reset source
                SetSourceToScreen();
            }
        });
        // set value buttons
        setB_[LvgDef.IN_CAT].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display category selections
                CategoryDialog.ShowDialog(owner_, valueT_[LvgDef.IN_CAT], 
                    valueT_[LvgDef.IN_INFL]);
            }
        });
        setB_[LvgDef.IN_INFL].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display inflection selections
                InflectionDialog.ShowDialog(owner_, valueT_[LvgDef.IN_INFL], 
                    valueT_[LvgDef.IN_CAT]);
            }
        });
        setB_[LvgDef.IN_i].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // display input file selection dialog
                JFileChooser dialog = new JFileChooser();
                //dialog.setCurrentDirectory(LvgGlobal.inFile_);
                dialog.setSelectedFile(
                    new File(valueT_[LvgDef.IN_i].getText()));
                dialog.setDialogTitle("Choose the input file");
                dialog.setMultiSelectionEnabled(false);
                if(dialog.showDialog(owner_, "Select")
                    == JFileChooser.APPROVE_OPTION)
                {
                    valueT_[LvgDef.IN_i].setText(
                        dialog.getSelectedFile().getPath());
                }
            }
        });
        helpB_[LvgDef.IN_t].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.IN_t);
            }
        });
        helpB_[LvgDef.IN_cf].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.IN_cf);
            }
        });
        helpB_[LvgDef.IN_if].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.IN_if);
            }
        });
        helpB_[LvgDef.IN_i].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                ShowHelp(LvgDef.IN_i);
            }
        });
    }
    // public methods
    public static void ShowDialog(JFrame owner)
    {
        if(dialog_ == null)
        {
            dialog_ = new InputOptionDialog(owner);
        }
        StoreStatus();
        dialog_.setVisible(true);
    }
    public static int GetTermFieldNum()
    {
        int termFieldNum = 1;
        if(cb_[LvgDef.IN_t].isSelected() == true)
        {
            termFieldNum = Integer.parseInt(valueT_[LvgDef.IN_t].getText());
        }
        return termFieldNum;
    }
    public static long GetCategory()
    {
        long cat = Long.parseLong(valueT_[LvgDef.IN_CAT].getText());
        return cat;
    }
    public static void SetCategory(long cat)
    {
        valueT_[LvgDef.IN_CAT].setText(Long.toString(cat));
    }
    public static long GetInflection()
    {
        long infl = Long.parseLong(valueT_[LvgDef.IN_INFL].getText());
        return infl;
    }
    public static void SetInflection(long infl)
    {
        valueT_[LvgDef.IN_INFL].setText(Long.toString(infl));
    }
    public static void ResetCheckBox()
    {
        for(int i = 0; i < SIZE; i++)
        {
            if(i < OFFSET)
            {
                cb_[i].setSelected(true);
            }
            else
            {
                cb_[i].setSelected(false);
            }
        }
    }
    public static void SetSourceToScreen()
    {
        rb_[SCREEN_INPUT].setSelected(true);
        LvgGlobal.inputFromScreen_ = true;
        valueT_[LvgDef.IN_i].setEditable(false);
    }
    public static int GetCategoryFieldNum()
    {
        int categoryFieldNum = -1; 
        if(cb_[LvgDef.IN_cf].isSelected() == true)
        {
            categoryFieldNum = 
                Integer.parseInt(valueT_[LvgDef.IN_cf].getText());
        }
        return categoryFieldNum;
    }
    public static int GetInflectionFieldNum()
    {
        int inflectionFieldNum = -1;
        if(cb_[LvgDef.IN_cf].isSelected() == true)
        {
            inflectionFieldNum =
                Integer.parseInt(valueT_[LvgDef.IN_if].getText());
        }
        return inflectionFieldNum;
    }
    // private methods
    // store current dialog status
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
        rb_[SCREEN_INPUT].setSelected(LvgGlobal.inputFromScreen_);
        rb_[FILE_INPUT].setSelected(!LvgGlobal.inputFromScreen_);
    }
    private void ShowHelp(int index)
    {
        String lvgDir = LvgGlobal.config_.GetConfiguration(
            Configuration.LVG_DIR);
        String optionUrl = "file:" + lvgDir +
            "docs/designDoc/LifeCycle/requirement/lvgOptions/"
            + LvgDef.INPUT_OPT_DOC[index];
        // display help document
        if(helpDoc_ == null)
        {
            String title = "Input Filter Option Documents";
            String homeUrl = "file:" + lvgDir +
                "docs/designDoc/LifeCycle/requirement/lvgInputFilter.html";
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
    private final static int SIZE = LvgDef.INPUT_OPT_NUM;
    private final static int OFFSET = 2;
    private static JCheckBox[] cb_ = new JCheckBox[SIZE];
    private static JTextField[] valueT_ = new JTextField[SIZE];
    private static JButton[] setB_ = new JButton[SIZE];
    private static JButton[] helpB_ = new JButton[SIZE];
    private static int[] size_ = new int[SIZE];
    private static boolean[] valueTFlag_ = {true, true, true, true, true};
    private static boolean[] setBFlag_ = {true, true, false, false, false};
    private static String[] defaults_ = new String[SIZE];
    private static String[] cancelText_ = new String[SIZE];
    private static boolean[] cancelCb_ = new boolean[SIZE];
    // source
    private final static int INPUT_NUM = 2;
    private final static int SCREEN_INPUT = 0;
    private final static int FILE_INPUT = 1;
    private static JRadioButton[] rb_ = new JRadioButton[INPUT_NUM];
    private static ButtonGroup rbGroup_ = null;        // Input source options
    private static ButtonGroup catGroup_ = null;
    private static ButtonGroup inflGroup_ = null;
    private static JFrame owner_ = null;
    private static InputOptionDialog dialog_ = null;
    private static LvgHtmlBrowser helpDoc_ = null;
    static
    {
        defaults_[LvgDef.IN_CAT] = Long.toString(Category.ALL_BIT_VALUE);
        defaults_[LvgDef.IN_INFL] = Long.toString(Inflection.ALL_BIT_VALUE);
        defaults_[LvgDef.IN_t] = "1";
        defaults_[LvgDef.IN_cf] = "0";
        defaults_[LvgDef.IN_if] = "0";
        defaults_[LvgDef.IN_i] = LvgGlobal.inFile_.getPath();
        size_[LvgDef.IN_CAT] = 4;
        size_[LvgDef.IN_INFL] = 8;
        size_[LvgDef.IN_cf] = 3;
        size_[LvgDef.IN_if] = 3;
        size_[LvgDef.IN_t] = 3;
        for(int i = 0; i < SIZE; i++)
        {
            if(i < OFFSET)
            {
                cb_[i] = new JCheckBox(LvgDef.INPUT_OPT[i]);
                cb_[i].setSelected(true);
            }
            else
            {
                cb_[i] = new JCheckBox(LvgDef.INPUT_OPT[i] + " (-" 
                    + LvgDef.INPUT_OPT_FLAG[i] + ")");
            }
            setB_[i] = new JButton("Setting");
            setB_[i].setPreferredSize(new Dimension(100, 21));
            helpB_[i] = new JButton("Help");
            helpB_[i].setPreferredSize(new Dimension(50, 21));
            valueT_[i] = new JTextField(defaults_[i], size_[i]);
        }
        valueT_[LvgDef.IN_i].setEditable(false);
        // source: radio buttons
        rb_[SCREEN_INPUT] = new JRadioButton("Screen");
        rb_[FILE_INPUT] = new JRadioButton("File");
        rb_[SCREEN_INPUT].setSelected(true);
        rbGroup_ = new ButtonGroup();
        rbGroup_.add(rb_[SCREEN_INPUT]);
        rbGroup_.add(rb_[FILE_INPUT]);
        // category buttons
        catGroup_ = new ButtonGroup();
        catGroup_.add(cb_[LvgDef.IN_CAT]);
        catGroup_.add(cb_[LvgDef.IN_cf]);
        // inflection buttons
        inflGroup_ = new ButtonGroup();
        inflGroup_.add(cb_[LvgDef.IN_INFL]);
        inflGroup_.add(cb_[LvgDef.IN_if]);
    }
    private final static long serialVersionUID = 5L;
}
