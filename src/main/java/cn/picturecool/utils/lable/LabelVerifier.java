package cn.picturecool.utils.lable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: tuku
 * @description: 标签分类校验器
 * @author: 赵元昊
 * @create: 2020-03-04 15:18
 **/
public class LabelVerifier {

    public static String labelOfSqlString(String sqlLabel, String targetLabel) {
        if (sqlLabel != null && targetLabel != null) {
            List<String> list = labelMerge(sqlLabel, targetLabel);
            String destString = stringListSplice(list);
            return destString;
        } else if (sqlLabel != null && targetLabel == null) {
            return sqlLabel;
        } else if (sqlLabel == null && targetLabel != null) {
            return stringListSplice(labelFilter(labelCut(targetLabel)));
        } else {
            return "";
        }
    }

    public static String onlySpliceAndFilter(String targetLabel) {
        return stringListSplice(labelFilter(labelCut(targetLabel)));
    }

    public static String stringListSplice(List<String> targetList) {
        String destString = "";
        for (String str : targetList) {
            destString = destString + "#" + str;
        }
        return destString;
    }

    public static List<String> labelMerge(String sqlLabel, String targetLabel) {
        List<String> sqlList = labelFilter(labelCut(sqlLabel));
        List<String> targetList = labelFilter(labelCut(targetLabel));
        for (String targetStr : targetList) {
            if (!sqlList.contains(targetStr)) {
                sqlList.add(targetStr);
            }
        }
        return sqlList;
    }

    public static List<String> labelCut(String sourceLabel) {
        if (sourceLabel == null) {
            return null;
        } else {
            String[] labels = sourceLabel.split("#");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < labels.length; i++) {
                list.add(labels[i]);
            }
            return list;
        }
    }

    public static List<String> labelFilter(List<String> sourceList) {
        List<String> list = new ArrayList<>();
        for (String l : sourceList) {
            switch (l) {
                case Labels.fengJing:
                    if (list.contains(Labels.fengJing)) {
                    } else {
                        list.add(Labels.fengJing);
                    }
                    break;
                case Labels.yingShi:
                    if (list.contains(Labels.yingShi)) {
                    } else {
                        list.add(Labels.yingShi);
                    }
                    break;
                case Labels.shouJi:
                    if (list.contains(Labels.shouJi)) {
                    } else {
                        list.add(Labels.shouJi);
                    }
                    break;
                case Labels.sheJi:
                    if (list.contains(Labels.sheJi)) {
                    } else {
                        list.add(Labels.sheJi);
                    }
                    break;
                case Labels.renWu:
                    if (list.contains(Labels.renWu)) {
                    } else {
                        list.add(Labels.renWu);
                    }
                    break;
                case Labels.dongMan:
                    if (list.contains(Labels.dongMan)) {
                    } else {
                        list.add(Labels.dongMan);
                    }
                    break;
                case Labels.wenZi:
                    if (list.contains(Labels.wenZi)) {
                    } else {
                        list.add(Labels.wenZi);
                    }
                    break;
                default:
                    break;
            }
        }
        return list;
    }
}
