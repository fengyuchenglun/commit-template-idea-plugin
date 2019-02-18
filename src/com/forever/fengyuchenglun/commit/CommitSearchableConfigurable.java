package com.forever.fengyuchenglun.commit;
import com.forever.fengyuchenglun.commit.ui.ConfigCommitForm;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * commit配置面板
 * @author duanledexianxianxian
 * @data 2019/2/15
 */
public class CommitSearchableConfigurable implements SearchableConfigurable {
    private ConfigCommitForm configCommitForm;
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
        if(null == this.configCommitForm) {
            this.configCommitForm = new ConfigCommitForm(commitSetting);
        }

        return this.configCommitForm.getMainPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
