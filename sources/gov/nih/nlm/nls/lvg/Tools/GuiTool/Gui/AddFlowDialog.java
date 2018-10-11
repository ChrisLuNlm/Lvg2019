package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
* This class show the dialog for adding a new flow into flows.
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
public class AddFlowDialog extends JDialog
{
    private AddFlowDialog(JFrame owner)
    {
        super(owner, "Add a new flow", true);
        owner_ = owner;
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(500, 400);
        // GUI components: Lvg Flow
        FlowListPanel flowPanel = new FlowListPanel();
        availableList_ = flowPanel.GetList();
        // buttons
        Box button = Box.createVerticalBox();
        JButton addB = new JButton(" Add  ");
        JButton insertB = new JButton("Insert");
        JButton deleteB = new JButton("Delete");
        JButton helpB = new JButton(" Help ");
        button.add(Box.createVerticalStrut(10));
        button.add(addB);
        button.add(Box.createVerticalStrut(10));
        button.add(insertB);
        button.add(Box.createVerticalStrut(10));
        button.add(deleteB);
        button.add(Box.createVerticalStrut(10));
        button.add(helpB);
        // Selected List
        selectedList_.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane selectedSp = new JScrollPane(selectedList_);
        Box selected = Box.createVerticalBox();
        selected.add(new JLabel("Current flow:"));
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
        centerP.add(flowPanel, gbc);
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
                if(index == LvgDef.F_ws)    // input word size
                {
                    WordSizeDialog.AddWordSizeDialog(getContentPane(),
                        selectedModel_);
                }
                else if(index == LvgDef.F_dc)    // derivation with category
                {
                    DerivationCatDialog.AddDerivationCatDialog(owner_,
                        selectedModel_);
                }
                else if(index == LvgDef.F_ici)    //inflection with cat & infl
                {
                    InflectionCatInflDialog.AddInflectionCatInflDialog(
                        owner_, selectedModel_);
                }
                else
                {
                    selectedModel_.addElement(LvgDef.FLOW_FLAG[index]);
                }
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
                if(srcIndex == LvgDef.F_ws)
                {
                    WordSizeDialog.InsertWordSizeDialog(getContentPane(), 
                        selectedModel_, tarIndex);
                }
                else if(srcIndex == LvgDef.F_dc)    // derivation with category
                {
                    DerivationCatDialog.InsertDerivationCatDialog(owner_,
                        selectedModel_, tarIndex);
                }
                else if(srcIndex == LvgDef.F_ici)    //inflection with cat & infl
                {
                    InflectionCatInflDialog.InsertInflectionCatInflDialog(
                        owner_, selectedModel_, tarIndex);
                }
                else
                {
                    selectedModel_.insertElementAt(
                        LvgDef.FLOW_FLAG[srcIndex], tarIndex);
                }
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
        // show the help information for the selected flow component
        helpB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = availableList_.getSelectedIndex();
                String lvgDir = LvgGlobal.config_.GetConfiguration(
                    Configuration.LVG_DIR);
                String flowUrl = "file:" + lvgDir 
                    + "docs/designDoc/UDF/flow/"
                    + LvgDef.FLOW_DOC[index];
                if(helpDoc_ == null)
                {
                    String title = "Lvg Flow Components Documents";
                    String homeUrl = "file:" + lvgDir + 
                        "docs/designDoc/UDF/flow/index.html";
                    helpDoc_ = new LvgHtmlBrowser(owner_, title,
                        600, 800, homeUrl, flowUrl);
                }
                else
                {
                    helpDoc_.SetPage(flowUrl);
                }
                helpDoc_.setVisible(true);
            }
        });
        // add the flow into cmd
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                if(selectedModel_.size() > 0)
                {
                    String cmd = "-f";
                    for(int i = 0; i < selectedModel_.size(); i++)
                    {
                        cmd += ":" + selectedModel_.elementAt(i);
                    }
                    LvgGlobal.cmd_.AddFlowComponent(cmd); // flow cmd list
                }
                setVisible(false);
            }
        });
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
    public static void ShowDialog(JFrame owner)
    {
        if(addFlowD_ == null)
        {
            addFlowD_ = new AddFlowDialog(owner);
        }
        // Clean up the selected list, the show
        selectedModel_.removeAllElements();
        addFlowD_.setVisible(true);
    }
    // private method
    // private data
    private static String[] item_ = new String[LvgDef.FLOW_NUM]; 
    private static JList<String> availableList_ = null;
    private static DefaultListModel<String> selectedModel_ 
        = new DefaultListModel<String>();
    private static JList<String> selectedList_ 
        = new JList<String>(selectedModel_); 
    private static JFrame owner_ = null;
    private static AddFlowDialog addFlowD_ = null;
    private static LvgHtmlBrowser helpDoc_ = null;
    private final static long serialVersionUID = 5L;
}
