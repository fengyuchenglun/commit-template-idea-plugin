package com.forever.fengyuchenglun.commit;

import com.forever.fengyuchenglun.commit.model.ChangeScope;
import com.forever.fengyuchenglun.commit.model.ChangeType;
import com.forever.fengyuchenglun.commit.model.CommitChange;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author duanledexianxianxian
 * @data 2019/2/16
 */
public class Demo {

    private static Pattern HEADER_PATTERN = Pattern.compile("^([a-z]+)(\\((.*)\\))?: (.*)\n");
    private static Pattern BODY_PATTERN = Pattern.compile("^([a-z]+)\\((.*)\\): (.*)$", Pattern.DOTALL | Pattern.MULTILINE);
    private static Pattern BREAKING_1_PATTERN = Pattern.compile(".*BREAKING CHANGE:(.*)$", Pattern.DOTALL | Pattern.MULTILINE);
    private static Pattern BREAKING_2_PATTERN = Pattern.compile(".*(Closes(.*\n))\1", Pattern.DOTALL | Pattern.MULTILINE);

    private static Pattern PATTERN = Pattern.compile("^([a-z]+)(\\(.*\\))?: ([^\n]+)\n(.*)?(BREAKING CHANGE: (.*))?(Closes ([^\n]+)\n)*", Pattern.DOTALL | Pattern.MULTILINE);

    private static Pattern CLOSES_PATTERN = Pattern.compile("Closes(.*)", Pattern.DOTALL | Pattern.MULTILINE);

    public static void main(String[] args) {
        List<ChangeType> changeTypeList = Lists.newArrayList();
        ChangeType s1 = new ChangeType("fix", "fix some bug");
        ChangeType s2 = new ChangeType("feat", "A new feature");
        changeTypeList.add(s1);
        changeTypeList.add(s2);
        List<ChangeScope> changeScopes = Lists.newArrayList();
        ChangeScope c1 = new ChangeScope("course", "课程");
        ChangeScope c2 = new ChangeScope("user", "用户");
        changeScopes.add(c1);
        changeScopes.add(c2);



        System.out.println(changeTypeList.toArray());


        String content = "feat(user): 123 456\n" +
                "\n" +
                "333333\n" +
                "\n" +
                "BREAKING CHANGE: 4444\n" +
                "\n" +
                "Closes 555\n" +
                "Closes 6666";
        System.out.println("------------ssssss-------------------");

        //返回true
        Matcher m = HEADER_PATTERN.matcher(content);
        while (m.find()) {
            // 打印
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.println(i + "   " + m.group(i));
            }
        }

        System.out.println("-------------------------------");
        String bc = "";
        m = BREAKING_1_PATTERN.matcher(content);
        while (m.find()) {
            // 打印
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.println(i + "   " + m.group(i));
            }
            bc = m.group(1);
        }


        System.out.println("-------------------------------");
        String b = "";
        m = BREAKING_2_PATTERN.matcher(bc);
        while (m.find()) {
            // 打印
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.println(i + "   " + m.group(i));
            }
        }


        System.out.println("-------------------------------");
        String c = "";
        m = CLOSES_PATTERN.matcher(bc);
        while (m.find()) {
            // 打印
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.println(i + "   " + m.group(i));
            }
        }

        CommitChange commitChange = new CommitChange();

        String[] sections = content.split("\nBREAKING CHANGE:\\s");
        System.out.println("sections:" + Arrays.toString(sections));
        if (sections.length == 1) {

        }else{

        }
        System.out.println("commitChange1:" + commitChange);

        CommitSetting commitSetting=new CommitSetting();
        commitSetting.setChangeTypeList(changeTypeList);
        commitSetting.setChangeScopeList(changeScopes);

        System.out.println("commitChange2:" + CommitMessage.buildCommitChange(content,commitSetting));


         content = "feat: 123 456\n" +
                "\n" +
                "333333\n" +
                 "333333\n" +
                 "333333\n" +
                 "333333\n" +
                "\n" +
                "BREAKING CHANGE: 4444\n" +
                 "333333\n" +
                 "333333\n" +
                 "333333\n" +
                "\n" +
                "Closes 555\n" +
                "Closes 6666";
        System.out.println("commitChange3:" + CommitMessage.buildCommitChange(content,commitSetting));


        content = "feat: 123 456\n";
        System.out.println("commitChange4:" + CommitMessage.buildCommitChange(content,commitSetting));

        content = "feat: 123 456\n" +
                "\n" +
                "BREAKING CHANGE: 4444\n" +
                "\n" +
                "Closes 6666";
        System.out.println("commitChange5:" + CommitMessage.buildCommitChange(content,commitSetting));
    }


}
