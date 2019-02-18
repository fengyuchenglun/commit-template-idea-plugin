package com.forever.fengyuchenglun.commit;

import com.forever.fengyuchenglun.commit.model.ChangeScope;
import com.forever.fengyuchenglun.commit.model.ChangeType;
import com.google.common.collect.Lists;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * commit提交配置类
 * 可进行持久化
 *
 * @author duanledexianxianxian
 * @data 2019 /2/15
 */
@State(
        name = "CommitSetting",
        storages = {@Storage(
                file = "$APP_CONFIG$/commit-setting.xml"
        )}
)
public class CommitSetting implements  PersistentStateComponent<Element> {
    /**
     * header type list
     */
    private List<ChangeType> changeTypeList= Lists.newArrayList();
    /**
     * header scope list
     */
    private List<ChangeScope> changeScopeList= Lists.newArrayList();


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CommitSetting getInstance() {
        return  ServiceManager.getService(CommitSetting.class);
    }

    /**
     * 保存的时候触发
     * @return
     */
    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("commitSetting");
        Element changeTypeListElement = new Element("changeTypes");
        changeTypeList.stream().forEach(x -> {
            Element changeTypeElement = new Element("changeType");
            changeTypeElement.setAttribute("type", x.getType());
            changeTypeElement.setAttribute("description", x.getDescription());
            changeTypeListElement.addContent(changeTypeElement);
        });
        Element changeScopeListElement = new Element("changeScopes");
        changeScopeList.stream().forEach(x -> {
            Element changeScopElement = new Element("changeScope");
            changeScopElement.setAttribute("scope", x.getScope());
            changeScopElement.setAttribute("description", x.getDescription());
            changeTypeListElement.addContent(changeScopElement);
        });
        element.addContent(changeTypeListElement);
        element.addContent(changeScopeListElement);
        return element;
    }

    @Override
    public void loadState(@NotNull Element element) {
        Element changeTypes=element.getChild("changeTypes");
        changeTypes.getChildren("changeType").stream().forEach(x->{
            this.changeTypeList.add(new ChangeType(x.getChild("type").getText(),x.getChild("description").getText()));
        });
        Element changeScopes=element.getChild("changeScopes");
        changeScopes.getChildren("changeScope").stream().forEach(x->{
            this.changeScopeList.add(new ChangeScope(x.getChild("scope").getText(),x.getChild("description").getText()));
        });
    }

    /**
     * Gets change type list.
     *
     * @return the change type list
     */
    public List<ChangeType> getChangeTypeList() {
        return changeTypeList;
    }

    /**
     * Sets change type list.
     *
     * @param changeTypeList the change type list
     */
    public void setChangeTypeList(List<ChangeType> changeTypeList) {
        this.changeTypeList = changeTypeList;
    }

    /**
     * Gets change scope list.
     *
     * @return the change scope list
     */
    public List<ChangeScope> getChangeScopeList() {
        return changeScopeList;
    }

    /**
     * Sets change scope list.
     *
     * @param changeScopeList the change scope list
     */
    public void setChangeScopeList(List<ChangeScope> changeScopeList) {
        this.changeScopeList = changeScopeList;
    }

}
