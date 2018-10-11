package gov.nih.nlm.nls.lvg.Tools.GuiTool.Global;
import java.util.*;
import javax.swing.*;
/*************************************************************************
* This is the GUI component for Lvg command objects.
* 
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LvgCommand
{
    public LvgCommand()
    {
    }
    /**
    public void SetCmdStr(String cmdStr)
    {
        lvgCmdStr_ = cmdStr;
    }
    **/
    public String GetCmdStr()
    {
        return lvgCmdStr_;
    }
    public void AddFlowComponent(String flow)
    {
        flowList_.addElement(flow);
    }
    public void SetFlowComponent(String flow, int index)
    {
        flowList_.setElementAt(flow, index);
    }
    public String GetFlowComponent(int index)
    {
        return flowList_.elementAt(index);
    }
    public void RemoveFlowComponent(int index)
    {
        flowList_.removeElementAt(index);
    }
    public void ResetFlow()
    {
        flowList_.removeAllElements();
    }
    public DefaultListModel<String> GetFlowList()
    {
        return flowList_;
    }
    public void AddInputOption(String option)
    {
        inputOptions_.addElement(option);
    }
    public void ResetInputOptions()
    {
        inputOptions_.removeAllElements();
    }
    public void AddGlobalOption(String option)
    {
        globalOptions_.addElement(option);
    }
    public void ResetGlobalOptions()
    {
        globalOptions_.removeAllElements();
    }
    public void AddOutputOption(String option)
    {
        outputOptions_.addElement(option);
    }
    public void ResetOutputOptions()
    {
        outputOptions_.removeAllElements();
    }
    public void UpdateCmdStr()
    {
        lvgCmdStr_ = new String();
        // flowList_
        for(int i = 0; i < flowList_.size(); i++)
        {
            lvgCmdStr_ += flowList_.elementAt(i) + " ";
        }
        // input optionList_
        for(int i = 0; i < inputOptions_.size(); i++)
        {
            lvgCmdStr_ += inputOptions_.elementAt(i) + " ";
        }
        // global optionList_
        for(int i = 0; i < globalOptions_.size(); i++)
        {
            lvgCmdStr_ += globalOptions_.elementAt(i) + " ";
        }
        // output optionList_
        for(int i = 0; i < outputOptions_.size(); i++)
        {
            lvgCmdStr_ += outputOptions_.elementAt(i) + " ";
        }
        lvgCmdStr_.trim();
    }
    public void Reset()
    {
        ResetFlow();
        ResetInputOptions();
        ResetGlobalOptions();
        ResetOutputOptions();
    }
    private String lvgCmdStr_ = new String();        // command in String
    private DefaultListModel<String> flowList_ = new DefaultListModel<String>();    // flows
    private Vector<String> inputOptions_ = new Vector<String>();//input options
    private Vector<String> globalOptions_ = new Vector<String>();//global options
    private Vector<String> outputOptions_ = new Vector<String>();//output options
}
