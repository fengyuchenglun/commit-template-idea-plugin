package com.forever.fengyuchenglun.commit;

import com.forever.fengyuchenglun.commit.model.ChangeScope;
import com.forever.fengyuchenglun.commit.model.ChangeType;
import com.forever.fengyuchenglun.commit.model.CommitChange;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * @author Damien Arrachequesne <damien.arrachequesne@gmail.com>
 */
public class CommitMessage {
    //  https://stackoverflow.com/a/2120040/5138 796
    private static final int MAX_LINE_LENGTH = 100;
    private static Pattern HEADER_PATTERN = Pattern.compile("^([a-z]+)(\\((.*)\\))?: (.*)\n(.*)*",Pattern.DOTALL | Pattern.MULTILINE);
//    private static Pattern BREAKING_CHANGE_PATTERN = Pattern.compile("((?!Closes).)*",Pattern.DOTALL | Pattern.MULTILINE);

    public static String buildContent(ChangeType changeType, ChangeScope changeScope, String shortDescription, String longDescription, String closedIssues, String breakingChanges) {
        StringBuilder builder = new StringBuilder();
        builder.append(changeType.getType());
        if (null != changeScope) {
            builder
                    .append('(')
                    .append(changeScope.getScope())
                    .append(')');
        }
        builder
                .append(": ")
                .append(StringUtils.isNotBlank(shortDescription) ? shortDescription : changeType.getDescription())
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append(WordUtils.wrap(longDescription, MAX_LINE_LENGTH));

        if (isNotBlank(breakingChanges)) {
            builder
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(WordUtils.wrap("BREAKING CHANGE: " + breakingChanges, MAX_LINE_LENGTH));
        }

        if (isNotBlank(closedIssues)) {
            builder.append(System.lineSeparator());
            for (String closedIssue : closedIssues.split(",")) {
                builder
                        .append(System.lineSeparator())
                        .append("Closes ")
                        .append(closedIssue);
            }
        }

        return builder.toString();
    }

    public static CommitChange buildCommitChange(String commitMessage, CommitSetting commitSetting) {
        CommitChange commitChange;
        if (StringUtils.isBlank(commitMessage)) {
            return null;
        }
        commitChange = new CommitChange();
        List<ChangeType> changeTypes = commitSetting.getChangeTypeList();
        List<ChangeScope> changeScopes = commitSetting.getChangeScopeList();

        // 如果配置项的changeType为空
        if (CollectionUtils.isEmpty(changeTypes)) {
            commitChange.setChangeType(null);
        }
        if (CollectionUtils.isEmpty(changeScopes)) {
            commitChange.setChangeScope(null);
        }
        // 分成两部分
        String[] sections = commitMessage.split("\nBREAKING CHANGE:\\s");
        // 解析header与body部分
        Matcher m = HEADER_PATTERN.matcher(sections[0]);
        if (m.find()) {
            String changeType = m.group(1);
            String changeScope = m.group(3);
            String shortDescription = m.group(4);
            String longDescription = m.group(5);

            // 对changType 进行处理
            if (StringUtils.isNotBlank(changeType)) {
                // 匹配成功 匹配量不大  没有做优化
                Optional<ChangeType> oChangeType = changeTypes.stream().filter(x -> changeType.equalsIgnoreCase(x.getType())).findFirst();
                if (oChangeType.isPresent()) {
                    commitChange.setChangeType(oChangeType.get());
                } else {
                    // 没有一个匹配到
                    commitChange.setChangeType(changeTypes.get(0));
                }
            }
            // 对changScope进行处理
            if (StringUtils.isNotBlank(changeScope)) {
                // 匹配成功 匹配量不大  没有做优化
                Optional<ChangeScope> oChangeScope = changeScopes.stream().filter(x -> changeScope.equalsIgnoreCase(x.getScope())).findFirst();
                if (oChangeScope.isPresent()) {
                    commitChange.setChangeScope(oChangeScope.get());
                } else {
                    // 没有一个匹配到
                    commitChange.setChangeScope(changeScopes.get(0));
                }
            }

            if (StringUtils.isNotBlank(shortDescription)){
                commitChange.setShortDescription(shortDescription.trim());
            }

            if (StringUtils.isNotBlank(longDescription)){
                commitChange.setLongDescription(longDescription.trim());
            }
        }

        // 解析breaking change+close部分
        if (sections.length>1){
            // 至少包含一个closes
            if(sections[1].contains("Closes")){
                String[] section1=sections[1].split("Closes");
                System.out.println(Arrays.toString(section1));
                if(StringUtils.isNotBlank(section1[0])){
                    commitChange.setBreakingChanges(section1[0].trim());
                }
                List<String> closes= Lists.newArrayList();
                for (int i=1;i<section1.length;i++){
                    closes.add(section1[i].trim());
                }
                commitChange.setClosedIssues(StringUtils.join(closes.toArray(),","));
            }
//           // 用Closes再次切分
//            String[] section1=sections[1].split("Closes");
//                for (int i=0;i<section1.length;i++){
//                    if(section1[i].contains("Closes"))
//                }
        }


        return commitChange;
    }

}