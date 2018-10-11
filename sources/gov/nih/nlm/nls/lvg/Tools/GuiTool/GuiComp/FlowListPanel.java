package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*****************************************************************************
* This panel list all lvg flow components.
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class FlowListPanel extends Panel
{
    public FlowListPanel()
    {
        // init select list
        flow_ = new String[LvgDef.FLOW_NUM];
        for(int i = 0; i < LvgDef.FLOW_NUM; i++)
        {
            flow_[i] = LvgDef.FLOW_FLAG[i] + ": " + LvgDef.FLOW[i];
        }
        flowList_ = new JList<String>(flow_);
        // scroll pane
        flowList_.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flowSp_ = new JScrollPane(flowList_);
        // set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Lvg flow components:"));
        add(Box.createVerticalStrut(5));
        add(flowSp_);
    }
    public JScrollPane GetFlowList()
    {
        return flowSp_;
    }
    public JList<String> GetList()
    {
        return flowList_;
    }
    // Test driver
    public static void main(String[] args)
    {
        JFrame frame = new LibCloseableFrame("Flow List Panel");
        frame.setContentPane(new FlowListPanel());
        frame.setSize(400, 250);
        frame.setVisible(true);
    }
    // data members
    private JList<String> flowList_ = null;
    private JScrollPane flowSp_ = null;
    private String[] flow_ = new String[LvgDef.FLOW_NUM];
    private final static long serialVersionUID = 5L;
}
