package com.forever.fengyuchenglun.commit.ui;

import com.google.common.collect.Lists;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.EditableModel;
import com.intellij.util.ui.UIUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

/**
 * 表格组件
 *
 * @author Sergey Timofiychuk
 */
public class CommitTable extends JBTable {

    private List<Entry<String, String>> settings;
    private List<String> columnNames;

    /**
     * Instantiates a new Templates table.
     *
     * @param model the model
     */
    @SuppressWarnings("unchecked")
    public CommitTable(Map<String, String> model, List columnNames) {
        this.columnNames = columnNames;
        // 条纹
        setStriped(true);
        // 自动绘制大小
        setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
        settings = new LinkedList<Entry<String, String>>();
        CollectionUtils.addAll(settings, model.entrySet().toArray(new Entry[model.entrySet().size()]));
        // 设置table数据
        setModel(new TableModel(columnNames));
        // 获取表格所有的列
        Enumeration<TableColumn> columns = getColumnModel().getColumns();
        // 循环自定义设置单元格样式
        while (columns.hasMoreElements()) {
            columns.nextElement().setCellRenderer(new FieldRenderer());
        }
    }

    /**
     * Gets settings model.
     *
     * @return clone of the original settings model
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getSettings() {
        return MapUtils.putAll(new LinkedHashMap(), settings.toArray());
    }

    /**
     * Sets settings model.
     *
     * @param model the model
     */
    @SuppressWarnings("unchecked")
    public void setSettingsModel(Map<String, String> model) {
        settings.clear();
        CollectionUtils.addAll(settings, model.entrySet().toArray(new Entry[model.entrySet().size()]));
        ((TableModel) getModel()).fireTableDataChanged();
    }

    @Override
    public boolean editCellAt(int row, int column, EventObject e) {
        if (e == null) {
            return false;
        }
        if (e instanceof MouseEvent) {
            MouseEvent event = (MouseEvent) e;
            // 多次点击
            if (event.getClickCount() == 1) {
                return false;
            }
        }
        // 打开对话框
        CommitConfigDialog dialog = new CommitConfigDialog(settings.get(row),columnNames);
        dialog.show();
        if (dialog.isOK()) {
            settings.set(row, dialog.getModel());
        }
        return false;
    }

    private class TableModel extends AbstractTableModel implements EditableModel {

        private List<String> columnNames;

        /**
         * Instantiates a new Table model.
         */
        public TableModel(List<String> columnNames) {
            if (null == columnNames) {
                this.columnNames = Lists.newLinkedList();
            } else {
                this.columnNames = columnNames;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames.get(column);
        }

        @Override
        public void addRow() {
            CommitConfigDialog dialog = new CommitConfigDialog(columnNames);
            dialog.show();
            if (dialog.isOK()) {
                settings.add(dialog.getModel());
            }
        }

        @Override
        public void removeRow(int index) {
            settings.remove(index);
        }

        @Override
        public void exchangeRows(int oldIndex, int newIndex) {
            Entry<String, String> oldItem = settings.get(oldIndex);
            settings.set(oldIndex, settings.get(newIndex));
            settings.set(newIndex, oldItem);
        }

        @Override
        public boolean canExchangeRows(int oldIndex, int newIndex) {
            return true;
        }

        @Override
        public int getRowCount() {
            return settings.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return columnIndex == 0 ? settings.get(rowIndex).getKey() : settings.get(rowIndex).getValue();
        }

    }

    /**
     * 自定义单元格
     */
    private static class FieldRenderer extends JLabel implements TableCellRenderer {

        /**
         * Instantiates a new Field renderer.
         */
        public FieldRenderer() {
            // 设置不透明
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // 设置背景
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            // 设置前景
            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            // 设置border
            setBorder(hasFocus ?
                    UIUtil.getTableFocusCellHighlightBorder() : BorderFactory.createEmptyBorder(1, 1, 1, 1));
            // 设置单元格文本值
            setText(value == null ? "" : value.toString());
            return this;
        }

    }


}
