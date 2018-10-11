package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*************************************************************************
* This class show the input panel for lexcial tool.
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
public class InputPanel extends JPanel
{
    private InputPanel(JFrame owner)
    {
        owner_ = owner;
        // Layout
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Inputs"));
        // Components
        termField_ = new JTextField(40);
        termField_.setFont(new Font("Dialog", 0, 14));
        fileField_ = new JTextField(35);
        fileField_.setFont(new Font("Dialog", 0, 14));
        fileField_.setEditable(false);
        JButton optionB = new JButton("Options");
        JButton resetB = new JButton("Reset");
        JButton detailB = new JButton("Details");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 3, 5, 3);
        GridBag.SetWeight(gbc, 100, 100);
        // input term
        term_ = Box.createHorizontalBox();
        term_.add(new JLabel("Term: "));
        term_.add(termField_);
        term_.setVisible(LvgGlobal.inputFromScreen_);
        // input file
        file_ = Box.createHorizontalBox();
        file_.add(new JLabel("File: "));
        file_.add(fileField_);
        file_.setVisible(!LvgGlobal.inputFromScreen_);
        // layout
        GridBag.SetPosSize(gbc, 1, 1, 5, 1);
        add(file_, gbc);
        GridBag.SetPosSize(gbc, 2, 1, 5, 1);
        add(term_, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        GridBag.SetPosSize(gbc, 3, 2, 1, 1);
        add(optionB, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        GridBag.SetPosSize(gbc, 4, 2, 1, 1);
        add(resetB, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        GridBag.SetPosSize(gbc, 5, 2, 1, 1);
        add(detailB, gbc);
        // inner class: buttons
        optionB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                InputOptionDialog.ShowDialog(owner_);
            }
        });
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                // set Input LexItem, CheckBox, input source
                termField_.setText("");
                InputOptionDialog.SetCategory(Category.ALL_BIT_VALUE);
                InputOptionDialog.SetInflection(Inflection.ALL_BIT_VALUE);
                InputOptionDialog.ResetCheckBox();
                InputOptionDialog.SetSourceToScreen();
            }
        });
        detailB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                if(LvgGlobal.inputFromScreen_ == true)
                {
                    // set LexItem
                    SetLexItem();
                    // set Dialog
                    if(lexItemD_ == null)
                    {
                        lexItemD_ = new ViewLexItemDialog(owner_, lexItem_,
                            "LVG Input - LexItem Details",
                            "Input LexItem Details");
                    }
                    else
                    {
                        lexItemD_.SetLexItem(lexItem_);
                    }
                    lexItemD_.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(owner_,
                        "No detail information is available for file input!",
                        "Input LexItem Details", 
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    public static JPanel GetPanel(JFrame owner)
    {
        if(inputP_ == null)
        {
            inputP_ = new InputPanel(owner);
        }
        return inputP_;
    }
    // return the latest term as the input LexItem
    public static void SetLexItem()
    {
        String separator = LvgGlobal.separator_;
        String inLine = termField_.getText();
        int termFieldNum = InputOptionDialog.GetTermFieldNum();
        String term = 
            InputFilter.GetInputTerm(inLine, separator, termFieldNum);
        if(MutatePanel.GetCurProgram() == MutatePanel.TO_ASCII_NO)
        {
            term = inLine;
        }
        lexItem_.SetOriginalTerm(term);
        lexItem_.SetSourceTerm(term);
        // input filter for cat & infl
        int categoryFieldNum = InputOptionDialog.GetCategoryFieldNum();
        if(categoryFieldNum > 0)
        {
            long inCat = InputFilter.GetInputCategory(inLine, separator, 
                categoryFieldNum);
            lexItem_.SetSourceCategory(inCat);
        }
        else
        {
            long inCat = InputOptionDialog.GetCategory();
            lexItem_.SetSourceCategory(inCat);
        }
        int inflectionFieldNum = InputOptionDialog.GetInflectionFieldNum();
        if(inflectionFieldNum > 0)
        {
            long inInfl = InputFilter.GetInputInflection(inLine, separator, 
                inflectionFieldNum);
            lexItem_.SetSourceInflection(inInfl);
        }
        else
        {
            long inInfl = InputOptionDialog.GetInflection();
            lexItem_.SetSourceInflection(inInfl);
        }
    }
    public static LexItem GetLexItem()
    {
        SetLexItem();
        return lexItem_;
    }
    public static String GetInLine()
    {
        return termField_.getText();
    }
    public static JTextField GetTermField()
    {
        return termField_;
    }
    public static void SetFile(String filePath)
    {
        fileField_.setText(filePath);
    }
    // show input file box or not (term box)
    public static void SetShowFile(boolean showTerm)
    {
        term_.setVisible(showTerm);
        file_.setVisible(!showTerm);
    }
    // private data
    private static JTextField termField_ = null;        // text field for term
    private static JTextField fileField_ = null;        // text field for file
    private static LexItem lexItem_ = null;                // input lexItem
    private static ViewLexItemDialog lexItemD_ = null;    // dialog for LexItem
    private static JFrame owner_ = null;                // parents owner
    private static InputPanel inputP_ = null;
    // make lvg input term triggerable
    private static Box term_ = null;
    private static Box file_ = null;
    private final static long serialVersionUID = 5L;
    static 
    {
        lexItem_ = new LexItem("", Category.ALL_BIT_VALUE, 
            Inflection.ALL_BIT_VALUE);
    }
}
