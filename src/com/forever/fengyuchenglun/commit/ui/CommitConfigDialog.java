package com.forever.fengyuchenglun.commit.ui;

import com.forever.fengyuchenglun.commit.CommitSetting;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * 配置对话框组件
 * todo 此处应该使用子类在继承
 *
 * @author Sergey Timofiychuk
 */
public class CommitConfigDialog extends DialogWrapper {
    CommitSetting commitSetting = CommitSetting.getInstance();
    /**
     * The Model.
     */
    private Entry<String, String> model;

    /**
     * The Name field.
     */
    private JTextField nameField;
    /**
     * The Template field.
     */
    private JTextArea templateField;

    /**
     * The Column names.
     */
    private List<String> columnNames;
    private List<Entry<String, String>> tableModel;

    /**
     * Sets column names.
     *
     * @param columnNames the column names
     */
    public void setColumnNames(List<String> columnNames) {
        if (null == columnNames && columnNames.size() < 2) {
            throw new IllegalArgumentException("column name is illegal");
        }
        this.columnNames = columnNames;
        // 重新绘制页面
        this.getContentPanel().updateUI();
    }


    /**
     * Instantiates a new Template config dialog.
     */
    public CommitConfigDialog(List<String> columnNames, List<Entry<String, String>> tableModel) {
        this(null, columnNames, tableModel);
    }

    /**
     * Instantiates a new Template config dialog.
     *
     * @param mode the mode
     */
    public CommitConfigDialog(Entry<String, String> mode, List<String> columnNames, List<Entry<String, String>> tableModel) {
        super(true);
        this.setColumnNames(columnNames);
        this.tableModel = tableModel;
        if (mode != null) {
            Map<String, String> modelCopy = new HashMap<String, String>();
            modelCopy.put(mode.getKey(), mode.getValue());
            this.model = modelCopy.entrySet().iterator().next();
        }
        init();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (nameField.getText().trim().length() == 0) {
            return new ValidationInfo(String.format("%s  is require", columnNames.get(0)));
        }
        // bad code
        if ("Type".equalsIgnoreCase(columnNames.get(0))) {
            Optional<Entry<String, String>> changeType = tableModel.stream().filter(x -> x.getKey().equalsIgnoreCase(nameField.getText())).findFirst();

            if (!changeType.isPresent() || (changeType.isPresent() && (null != model && model.getKey().equalsIgnoreCase(nameField.getText())))) {
                return super.doValidate();
            } else {
                return new ValidationInfo(String.format("%s  have exist!", nameField.getText()));
            }
        }
        if ("Scope".equalsIgnoreCase(columnNames.get(0))) {
            Optional<Entry<String, String>> changeScope = tableModel.stream().filter(x -> x.getKey().equalsIgnoreCase(nameField.getText())).findFirst();
            if (!changeScope.isPresent() || (changeScope.isPresent() && (null != model && model.getKey().equalsIgnoreCase(nameField.getText())))) {
                return super.doValidate();
            } else {
                return new ValidationInfo(String.format("%s  have exist!", nameField.getText()));
            }
        }
        return super.doValidate();
    }

    /**
     * Gets table model.
     *
     * @return the model
     */
    public Entry<String, String> getModel() {
        Map<String, String> model = new HashMap<String, String>();
        model.put(nameField.getText(), templateField.getText());
        return model.entrySet().iterator().next();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));

        nameField = new JTextField();
        nameField.setHorizontalAlignment(SwingConstants.LEFT);
        nameField.setPreferredSize(new Dimension(500, 10));
        if (model != null) {
            nameField.setText(model.getKey());
        }
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(IdeBorderFactory.createTitledBorder(columnNames.get(0), false, new Insets(0, 0, 10, 0)));
        namePanel.add(nameField, BorderLayout.CENTER);

        templateField = new JTextArea();
        if (model != null) {
            templateField.setText(model.getValue());
        }
        JPanel templatePanel = new JPanel(new BorderLayout());
        templatePanel.setBorder(IdeBorderFactory.createTitledBorder(columnNames.get(1), false, new Insets(0, 0, 0, 0)));
        templatePanel.add(templateField, BorderLayout.CENTER);
        templateField.setPreferredSize(new Dimension(500, 80));

        panel.add(namePanel, getConstraints(0, 0));
        panel.add(templatePanel, getConstraints(1, 0));
        return panel;
    }

    /**
     * Gets constraints.
     *
     * @param row    the row
     * @param column the column
     * @return the constraints
     */
    private GridConstraints getConstraints(int row, int column) {
        return new GridConstraints(row, column, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false);
    }

}
