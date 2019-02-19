package com.forever.fengyuchenglun.commit;

import com.forever.fengyuchenglun.commit.ui.CommitDialog;
import com.forever.fengyuchenglun.commit.util.CommitMessageUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.changes.ui.CommitChangeListDialog;
import com.intellij.openapi.vcs.ui.Refreshable;
import org.jetbrains.annotations.Nullable;

/**
 * 创建commit action
 * @author fengyuchenglun
 */
public class CreateCommitAction extends AnAction implements DumbAware {

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        // 获得commitPanel对象
        final CommitChangeListDialog commitPanel = getCommitPanel(actionEvent);
        if (commitPanel == null){
            return;
        }

        CommitDialog dialog = new CommitDialog(actionEvent.getProject(), CommitMessageUtils.buildCommitChange(commitPanel.getCommitMessage(),CommitSetting.getInstance()));
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            commitPanel.setCommitMessage(dialog.getCommitMessage());
        }
    }

    /**
     * Gets commit panel.
     *
     * @param e the e
     * @return the commit panel
     */
    @Nullable
    private static CommitChangeListDialog getCommitPanel(@Nullable AnActionEvent e) {
        if (e == null) {
            return null;
        }
        Refreshable data = Refreshable.PANEL_KEY.getData(e.getDataContext());
        if (data instanceof CommitMessageI) {
            return (CommitChangeListDialog) data;
        }
        return null;
        // 暂时去掉
        //return VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.getDataContext());
    }
}
