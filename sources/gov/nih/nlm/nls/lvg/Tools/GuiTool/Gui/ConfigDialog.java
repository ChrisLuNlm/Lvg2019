package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*****************************************************************************
* This is the configuration dialog class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class ConfigDialog extends JDialog
{
    public ConfigDialog(JFrame owner)
    {
        super(owner, "Configuration Setup", true);
        Init();
        owner_ = owner;
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(360, 340);
        // top panel
        JPanel topP = new JPanel();
        topP.add(new JLabel("configuration variables:"));
        // center
        JPanel centerP = new JPanel();
        centerP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Configurations:"));
        GridBagLayout gbl = new GridBagLayout();
        centerP.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);
        GridBag.SetWeight(gbc, 100, 100);
        
        for(int i = 0; i < LvgDef.CONFIG_VARS_NUM; i++)
        {
            GridBag.SetPosSize(gbc, 0, i, 1, 1);
            centerP.add(cb_[i], gbc);
            GridBag.SetPosSize(gbc, 1, i, 1, 1);
            centerP.add(valueT_[i], gbc);
        }
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
                SetValues();
                setVisible(false);
            }
        });
        cancelB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                GetValues();
                setVisible(false);
            }
        });
        resetB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                GetValues();
            }
        });
    }
    // private methods
    private void Init()
    {
        size_[LvgDef.CONF_MIN_TERM_LENGTH] = 3;
        size_[LvgDef.CONF_MAX_PERMUTE_TERM] = 3;
        size_[LvgDef.CONF_MAX_METAPHONE] = 3;
        size_[LvgDef.CONF_CGI_EOP] = 12;
        size_[LvgDef.CONF_NO_OUTPUT] = 12;
        size_[LvgDef.CONF_TRUNCATED_NUM] = 3;
        for(int i = 0; i < LvgDef.CONFIG_VARS_NUM; i++)
        {
            cb_[i] = new JLabel(LvgDef.CONFIG_VARS[i]);
            valueT_[i] = new JTextField(defaults_[i], size_[i]);
        }
        GetValues();
    }
    private void SetValues()
    {
        for(int i = 0; i < LvgDef.CONFIG_VARS_NUM; i++)
        {
            /**
            LvgGlobal.config_.SetConfiguration(
                LvgDef.CONFIG_VARS[i], valueT_[i].getText());
                **/
        }
    }
    private void GetValues()
    {
        for(int i = 0; i < LvgDef.CONFIG_VARS_NUM; i++)
        {
            defaults_[i] = LvgGlobal.config_.GetConfiguration(
                LvgDef.CONFIG_VARS[i]);
            valueT_[i].setText(defaults_[i]);
        }
    }
    // private data
    private JLabel[] cb_ = new JLabel[LvgDef.CONFIG_VARS_NUM];
    private JTextField[] valueT_ = new JTextField[LvgDef.CONFIG_VARS_NUM];
    private String[] defaults_ = new String[LvgDef.CONFIG_VARS_NUM];
    private int[] size_ = new int[LvgDef.CONFIG_VARS_NUM];
    private JFrame owner_ = null;
    private final static long serialVersionUID = 5L;
}
