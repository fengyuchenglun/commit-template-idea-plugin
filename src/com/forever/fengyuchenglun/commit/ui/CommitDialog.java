package com.forever.fengyuchenglun.commit.ui;

import com.forever.fengyuchenglun.commit.model.CommitChange;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Damien Arrachequesne
 */
public class CommitDialog extends DialogWrapper {

    private final CommitPanel panel;

   public CommitDialog(@Nullable Project project,@NotNull CommitChange commitChange) {
        super(project);
        panel = new CommitPanel(project,commitChange);
        setTitle("Commit");
        setOKButtonText("OK");
        init();
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
