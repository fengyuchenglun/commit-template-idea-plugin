package com.forever.fengyuchenglun.commit.ui;

import com.forever.fengyuchenglun.commit.CommitMessage;
import com.forever.fengyuchenglun.commit.CommitSetting;
import com.forever.fengyuchenglun.commit.model.ChangeScope;
import com.forever.fengyuchenglun.commit.model.ChangeType;
import com.forever.fengyuchenglun.commit.model.CommitChange;
import com.intellij.openapi.project.Project;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Damien Arrachequesne
 */
public class CommitPanel {
    private CommitSetting commitSetting = CommitSetting.getInstance();
    private JPanel mainPanel;
    private JComboBox changeType;
    private JComboBox changeScope;
    private JTextField shortDescription;
    private JTextArea longDescription;
    private JTextField closedIssues;
    private JTextArea breakingChanges;

    public CommitPanel(Project project, CommitChange commitChange) {
        for (ChangeType type : commitSetting.getChangeTypeList()) {
            changeType.addItem(type);
        }
        for (ChangeScope scope : commitSetting.getChangeScopeList()) {
            changeScope.addItem(scope);
        }
        if (null != commitChange) {
            // 下拉框选项不为空
            if (!CollectionUtils.isEmpty(commitSetting.getChangeTypeList())) {
                // 选中值
                if (commitChange.getChangeType() != null) {
                    changeType.setSelectedItem(commitChange.getChangeType());
                } else {
                    // 默认选择第一个
                    changeType.setSelectedIndex(0);
                }
            }

            if (!CollectionUtils.isEmpty(commitSetting.getChangeScopeList())) {
                // 选中值
                if (commitChange.getChangeScope() != null) {
                    changeScope.setSelectedItem(commitChange.getChangeScope());
                } else {
                    // 默认选择第一个
                    changeScope.setSelectedIndex(0);
                }
            }

            if(StringUtils.isNotBlank(commitChange.getShortDescription())){
                shortDescription.setText(commitChange.getShortDescription());
            }

            if(StringUtils.isNotBlank(commitChange.getLongDescription())){
                longDescription.setText(commitChange.getLongDescription());
            }

            if(StringUtils.isNotBlank(commitChange.getBreakingChanges())){
                breakingChanges.setText(commitChange.getBreakingChanges());
            }

            if(StringUtils.isNotBlank(commitChange.getClosedIssues())){
                closedIssues.setText(commitChange.getClosedIssues());
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    String getCommitMessage() {
        return CommitMessage.buildContent(
                (ChangeType) changeType.getSelectedItem(),
                (ChangeScope) changeScope.getSelectedItem(),
                shortDescription.getText().trim(),
                longDescription.getText().trim(),
                closedIssues.getText().trim(),
                breakingChanges.getText().trim()
        );
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Type of change");
        mainPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(94, 17), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Scope of this change");
        mainPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(94, 17), null, 0, false));
        changeScope = new JComboBox();
        changeScope.setEditable(true);
        mainPanel.add(changeScope, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setHorizontalTextPosition(2);
        label3.setText("Short description");
        mainPanel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(94, 17), null, 0, false));
        shortDescription = new JTextField();
        mainPanel.add(shortDescription, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Long description");
        mainPanel.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(94, 17), null, 0, false));
        longDescription = new JTextArea();
        longDescription.setLineWrap(true);
        longDescription.setToolTipText("Max Size is 72");
        mainPanel.add(longDescription, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Closed issues");
        mainPanel.add(label5, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(94, 17), null, 0, false));
        changeType = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        changeType.setModel(defaultComboBoxModel1);
        mainPanel.add(changeType, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        closedIssues = new JTextField();
        mainPanel.add(closedIssues, new GridConstraints(5, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Breaking changes");
        mainPanel.add(label6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(94, 17), null, 0, false));
        breakingChanges = new JTextArea();
        breakingChanges.setLineWrap(true);
        breakingChanges.setText("");
        breakingChanges.setToolTipText("Max Size is 72");
        mainPanel.add(breakingChanges, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
