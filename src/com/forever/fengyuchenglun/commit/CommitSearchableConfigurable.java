package com.forever.fengyuchenglun.commit;

import com.forever.fengyuchenglun.commit.ui.ConfigCommitPanel;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * commit配置面板
 *
 * @author duanledexianxianxian
 * @data 2019/2/15
 */
public class CommitSearchableConfigurable implements SearchableConfigurable {
    private ConfigCommitPanel configCommitPanel;
    private CommitSetting commitSetting = CommitSetting.getInstance();

    public CommitSearchableConfigurable() {
    }

    @NotNull
    @Override
    public String getId() {
        return "Commit Setting";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return this.getId();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == this.configCommitPanel) {
            this.configCommitPanel = new ConfigCommitPanel(commitSetting);
        }

        return this.configCommitPanel.getMainPanel();
    }

    /**
     * 配置项是否已经修改
     *
     * @return
     */
    @Override
    public boolean isModified() {
        if (null == this.configCommitPanel) {
            return false;
        }
        return this.configCommitPanel.isModified();
    }

    /**
     * 应用配置项
     *
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        this.configCommitPanel.apply();
    }

    /**
     * 重置
     */
    @Override
    public void reset() {
        this.configCommitPanel.reset();

    }
}
