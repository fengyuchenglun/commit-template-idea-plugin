package com.forever.fengyuchenglun.commit.util;

import com.forever.fengyuchenglun.commit.CommitSetting;
import com.forever.fengyuchenglun.commit.model.ChangeScope;
import com.forever.fengyuchenglun.commit.model.ChangeType;
import com.forever.fengyuchenglun.commit.model.CommitChange;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.forever.fengyuchenglun.commit.Constant.DEFAULT_CHANGE_SCOPE_VALUE;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * @author Damien Arrachequesne <damien.arrachequesne@gmail.com>
 */
public class CommitMessageUtils {
    //  https://stackoverflow.com/a/2120040/5138 796
    private static final int MAX_LINE_LENGTH = 100;
    private static Pattern HEADER_PATTERN = Pattern.compile("^([a-z]+)(\\((.*)\\))?: ([^\n]+)+?(.*)?\n?", Pattern.DOTALL | Pattern.MULTILINE);

    public static String buildContent(ChangeType changeType, ChangeScope changeScope, String shortDescription, String longDescription, String closedIssues, String breakingChanges) {
        StringBuilder builder = new StringBuilder();
        builder.append(changeType.getType());
        if (null != changeScope && !StringUtils.equalsIgnoreCase(changeScope.getScope(), DEFAULT_CHANGE_SCOPE_VALUE)) {
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
        CommitChange commitChange = new CommitChange();
        if (StringUtils.isBlank(commitMessage)) {
            return commitChange;
        }
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
        if (StringUtils.contains(sections[0], "Closes ")) {
            String s = new String(sections[0]);
            int index = StringUtils.indexOf(s, "Closes ");
            sections=new String[2];
            sections[0] = StringUtils.substring(s, 0, index);
            sections[1] = StringUtils.substring(s, index);
        }
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

            if (StringUtils.isNotBlank(shortDescription)) {
                commitChange.setShortDescription(shortDescription.trim());
            }

            if (StringUtils.isNotBlank(longDescription)) {
                commitChange.setLongDescription(longDescription.trim());
            }
        }

        // 解析breaking change+close部分
        if (sections.length > 1) {
            // 至少包含一个closes
            if (sections[1].contains("Closes")) {
                String[] section1 = sections[1].split("Closes");
                System.out.println(Arrays.toString(section1));
                if (StringUtils.isNotBlank(section1[0])) {
                    commitChange.setBreakingChanges(section1[0].trim());
                }
                List<String> closes = Lists.newArrayList();
                for (int i = 1; i < section1.length; i++) {
                    closes.add(section1[i].trim());
                }
                commitChange.setClosedIssues(StringUtils.join(closes.toArray(), ","));
            }else {
                commitChange.setBreakingChanges(sections[1].trim());
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