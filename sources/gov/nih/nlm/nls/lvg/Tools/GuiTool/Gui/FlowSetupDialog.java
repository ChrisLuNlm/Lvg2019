package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
* This class show the dialog for setting up flows.
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
public class FlowSetupDialog extends JDialog
{
    private FlowSetupDialog(JFrame owner)
    {
        super(owner, "Flow(s) Setup", true);
        owner_ = owner;
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(500, 300);
        // Selected List
        selectedList_.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane selectedSp = new JScrollPane(selectedList_);
        // center Panel
        JPanel centerP = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        centerP.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);   //Pad: Top, Left, Bottom, Right
        GridBag.SetWeight(gbc, 100, 100);
        GridBag.SetPosSize(gbc, 0, 0, 1, 1);
        centerP.add(new JLabel("Construct LVG Flow(s):"), gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 5, 5);   //Pad: Top, Left, Bottom, Right
        GridBag.SetPosSize(gbc, 0, 1, 1, 10);
        centerP.add(selectedSp, gbc);
        // botton panel 
        JPanel buttonP = new JPanel();
        JButton okB = new JButton("OK");
        JButton cancelB = new JButton("Cancel");
        JButton addB = new JButton("Add");
        JButton deleteB = new JButton("Delete");
        JButton modifyB = new JButton("Modify");
        JButton resetB = new JButton("Reset");
        buttonP.add(okB);
        buttonP.add(cancelB);
        buttonP.add(addB);
        buttonP.add(deleteB);
        buttonP.add(modifyB);
        buttonP.add(resetB);
                        
        // Top level Panel
        getContentPane().add(centerP, "Center");
        getContentPane().add(buttonP, "South");
        // inner class for action listener
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                LvgGlobal.UpdateCmdStr();
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
        addB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                AddFlowDialog.ShowDialog(owner_);
            }
        });
        modifyB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = selectedList_.getSelectedIndex();
                if(selectedList_.getModel().getSize() <= 0)
                {
                    JOptionPane.showMessageDialog(owner_,
                        "Current flow is empty!",
                        "Setup Flow", JOptionPane.WARNING_MESSAGE);
                }
                else if(index < 0)
                {
                    JOptionPane.showMessageDialog(owner_,
                        "Please select a flow for modifying",
                        "Setup Flow", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    ModifyFlowDialog.ShowDialog(owner_, index);
                }
            }
        });
        deleteB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                int index = selectedList_.getSelectedIndex();
                if(index < 0)
                {
                    JOptionPane.showMessageDialog(owner_,
                        "Please select a flow to delete",
                        "Setup Flow", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    LvgGlobal.cmd_.RemoveFlowComponent(index);
                }
            }
        });
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                LvgGlobal.cmd_.ResetFlow();
            }
        });
    }
    public static void ShowDialog(JFrame owner)
    {
        if(dialog_ == null)
        {
            dialog_ = new FlowSetupDialog(owner);
        }
        
        dialog_.setVisible(true);
    }
    // private data
    private static JList<String> selectedList_ = 
        new JList<String>(LvgGlobal.cmd_.GetFlowList()); 
    private static FlowSetupDialog dialog_ = null;
    private static JFrame owner_ = null;
    private final static long serialVersionUID = 5L;
}
