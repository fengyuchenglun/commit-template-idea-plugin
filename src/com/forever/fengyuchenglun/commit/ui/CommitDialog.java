package com.forever.fengyuchenglun.commit.ui;

import com.forever.fengyuchenglun.commit.model.CommitChange;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Damien Arrachequesne
 */
public class CommitDialog extends DialogWrapper {

    private final CommitPanel panel;

    public CommitDialog(@Nullable Project project, @NotNull CommitChange commitChange) {
        super(project);
        panel = new CommitPanel(project, commitChange);
        setTitle("Commit");
        setOKButtonText("OK");
        init();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (panel.getShortDescription().getText().trim().length() > 100) {
            return new ValidationInfo("Short description max size is 100.");
        }
        if (panel.getLongDescription().getText().trim().length() > 200) {
            return new ValidationInfo("Long description max size is 100.");
        }
        if (panel.getBreakingChanges().getText().trim().length() > 200) {
            return new ValidationInfo("Breaking changes max size is 100.");
        }
        return super.doValidate();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel.getMainPanel();
    }

    public String getCommitMessage() {
        return panel.getCommitMessage();
    }

}
