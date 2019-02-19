package com.forever.fengyuchenglun.commit.ui;

import com.forever.fengyuchenglun.commit.CommitSetting;
import com.forever.fengyuchenglun.commit.model.ChangeScope;
import com.forever.fengyuchenglun.commit.model.ChangeType;
import com.forever.fengyuchenglun.commit.ui.CommitTable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author duanledexianxianxian
 * @data 2019/2/15
 */
public class ConfigCommitPanel {
    private JPanel mainPanel;
    private CommitSetting commitSetting;
    private CommitTable changeTypeTable;
    private CommitTable changeScopeTable;

    private String[] changeTypeColumnNames = {"Type", "Description"};
    private String[] changeScopeColumnNames = {"Scope", "Description"};

    public ConfigCommitPanel(CommitSetting commitSetting) {
        this.commitSetting = commitSetting;
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));

        changeTypeTable = new CommitTable(commitSetting.getChangeTypeList().stream().collect(Collectors.toMap(ChangeType::getType, x -> x.getDescription(), (key1, key2) -> key2, LinkedHashMap::new)), Arrays.asList(changeTypeColumnNames));
        JPanel changeTypeLocalPanel = ToolbarDecorator.createDecorator(changeTypeTable).createPanel();
        JPanel changeTypePanel = new JPanel(new BorderLayout());
        changeTypePanel.setBorder(IdeBorderFactory.createTitledBorder("Change of Type", false, new Insets(0, 0, 10, 0)));
        changeTypePanel.add(changeTypeLocalPanel, BorderLayout.CENTER);

        changeScopeTable = new CommitTable(commitSetting.getChangeScopeList().stream().collect(Collectors.toMap(ChangeScope::getScope, x -> x.getDescription(), (key1, key2) -> key2, LinkedHashMap::new)), Arrays.asList(changeScopeColumnNames));
        JPanel changeScopeLocalPanel = ToolbarDecorator.createDecorator(changeScopeTable).createPanel();
        JPanel changeScopePanel = new JPanel(new BorderLayout());
        changeScopePanel.setBorder(IdeBorderFactory.createTitledBorder("Change of Scope", false, new Insets(0, 0, 10, 0)));
        changeScopePanel.add(changeScopeLocalPanel, BorderLayout.CENTER);

        mainPanel.add(changeTypePanel, getConstraints(0, 0));
        mainPanel.add(changeScopePanel, getConstraints(1, 0));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    @SuppressWarnings("unchecked")
    private boolean checkIfTableContentModified(Map<String, String> templatesTableSettings,
                                                Map<String, String> templatesSettings) {
        boolean result = false;

        Map.Entry<String, String>[] templatesTableEntries =
                templatesTableSettings.entrySet().toArray(new Map.Entry[templatesTableSettings.size()]);
        Map.Entry<String, String>[] templatesEntries =
                templatesSettings.entrySet().toArray(new Map.Entry[templatesSettings.size()]);
        if (templatesEntries.length == templatesTableEntries.length) {
            for (int i = 0; i < templatesEntries.length; i++) {
                result = result || !templatesEntries[i].getKey().equals(templatesTableEntries[i].getKey());
                result = result || !templatesEntries[i].getValue().equals(templatesTableEntries[i].getValue());
            }
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 比较是否已经改变了值
     *
     * @return
     */
    public boolean isModified() {
        boolean result = false;

        Map<String, String> s1 = changeScopeTable.getSettings();
        Map<String, String> s2 = commitSetting.getChangeScopeList().stream().collect(Collectors.toMap(ChangeScope::getScope, x -> x.getDescription(), (key1, key2) -> key2, LinkedHashMap::new));
        result = result || checkIfTableContentModified(s2, s1);


        Map<String, String> s3 = changeTypeTable.getSettings();
        Map<String, String> s4 = commitSetting.getChangeTypeList().stream().collect(Collectors.toMap(ChangeType::getType, x -> x.getDescription(), (key1, key2) -> key2, LinkedHashMap::new));
        result = result || checkIfTableContentModified(s4, s3);
        return result;
    }

    /**
     * 应用配置
     */
    public void apply() {
        List<ChangeType> changeTypeList = Lists.newArrayList();
        for (Map.Entry<String, String> entry : changeScopeTable.getSettings().entrySet()) {
            changeTypeList.add(new ChangeType(entry.getKey(), entry.getValue()));
        }

        List<ChangeScope> changeScopeList = Lists.newArrayList();
        for (Map.Entry<String, String> entry : changeScopeTable.getSettings().entrySet()) {
            changeScopeList.add(new ChangeScope(entry.getKey(), entry.getValue()));
        }
        commitSetting.getChangeTypeList().addAll(changeTypeList);
        commitSetting.getChangeScopeList().addAll(changeScopeList);
    }

    public void reset() {
        // 重置
        commitSetting.noStateLoaded();
        changeTypeTable.setSettingsModel(commitSetting.getChangeTypeList().stream().collect(Collectors.toMap(ChangeType::getType, x -> x.getDescription())));
        changeScopeTable.setSettingsModel(commitSetting.getChangeScopeList().stream().collect(Collectors.toMap(ChangeScope::getScope, x -> x.getDescription())));

    }


    private GridConstraints getConstraints(int row, int column) {
        return new GridConstraints(row, column, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false);
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
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setEnabled(true);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
