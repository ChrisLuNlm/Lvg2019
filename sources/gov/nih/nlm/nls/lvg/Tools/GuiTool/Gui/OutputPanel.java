package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*************************************************************************
* This class show the output panel for lexcial tool.
* The constructor is private since no instance object should be generated.
* GetPanel( ) should be called when use this panel.
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class OutputPanel extends JPanel
{
    private OutputPanel(JFrame owner)
    {
        owner_ = owner;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Outputs"));
        // GUI components
        JScrollPane scrollPane = new JScrollPane(lvgOutputs_); // scrollable
        JButton optionB = new JButton("Options");
        JButton resetB = new JButton("Reset");
        JButton detailB = new JButton("Details");
        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 3, 5, 3);
        GridBag.SetWeight(gbc, 100, 100);
        GridBag.SetPosSize(gbc, 1, 1, 5, 1);
        add(scrollPane, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        GridBag.SetPosSize(gbc, 2, 2, 1, 1);
        add(optionB, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        GridBag.SetPosSize(gbc, 3, 2, 1, 1);
        add(resetB, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        GridBag.SetPosSize(gbc, 4, 2, 1, 1);
        add(detailB, gbc);
        // inner class: buttons
        optionB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                OutputOptionDialog.ShowDialog(owner_);
            }
        });
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                lvgOutputs_.setListData(banner_);
            }
        });
        detailB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = lvgOutputs_.getSelectedIndex();
                Vector<LexItem> outputs = LvgGlobal.GetOutputLexItems();
                ViewLexItemsDialog lexItemD = 
                    new ViewLexItemsDialog(owner_, outputs, 
                    "LVG Output - LexItems Details",
                    "Output LexItems Deatils", index);
                lexItemD.setVisible(true);
            }
        });
    }
    public static JPanel GetPanel(JFrame owner)
    {
        if(outputP_ == null)
        {
            outputP_ = new OutputPanel(owner);
        }
        return outputP_;
    }
    // This is used only for output to the screen
    public static void UpdateResult()
    {
        Vector<String> outStrs = LvgGlobal.GetOutputStrings();
        if(outStrs.size() == 0)
        {
            lvgOutputs_.setListData(banner_);
        }
        else
        {
            // get string result & select select list
            lvgOutputs_.setListData(outStrs);
            lvgOutputs_.setSelectedIndex(0);
        }
    }
    public static void SetSelectedIndex(int index)
    {
        lvgOutputs_.setSelectedIndex(index);
    }
    public static JList<String> GetOutputList()
    {
        return lvgOutputs_;
    }
    public static void SetOutputMessage()
    {
        String msg = "*** Results are sent to the file below ***";
        Vector<String> msgList_ = new Vector<String>();
        msgList_.addElement(msg);
        msgList_.addElement("    '" + LvgGlobal.outFile_.getPath() + "'");
        GregorianCalendar cal = new GregorianCalendar();
        msgList_.addElement("    @ " + cal.getTime().toString());
        lvgOutputs_.setListData(msgList_);
    }
    // private data
    private final static String LVG_BANNER = "*** Welcome to Lexical Tool ***";
    private static Vector<String> banner_ = new Vector<String>();
    private static JList<String> lvgOutputs_ = null;
    private static JFrame owner_ = null;
    private static OutputPanel outputP_ = null;
    static
    {
        banner_.addElement(LVG_BANNER);
        lvgOutputs_ = new JList<String>(banner_);
        lvgOutputs_.setFont(new Font("Dialog", 0, 14));
    }
    private final static long serialVersionUID = 5L;
}
