package cn.picturecool.utils.image.sort;


import java.util.*;

/**
 * @program: pictureCool
 * @description: 对传入的汉明度进行排序筛选分析，并选择性的提供新的map
 * @author: 赵元昊
 * @create: 2020-02-06 21:20
 **/
public class HashSort {

    public final static Integer SAME = 100;
    public final static Integer NEARLY = 93;
    public final static Integer NOTBAD = 87;

    /*
     *获取相同汉明度的hashmap
     * */
    public static Map<String, Integer> getSameSortMap(Map<String, Integer> sortMap) {
        return getSortMap(sortMap, SAME, SAME);
    }

    /*
     *获取几乎相同汉明度的hashmap
     * */
    public static Map<String, Integer> getNearlySortMap(Map<String, Integer> sortMap) {
        return getSortMap(sortMap, SAME, NEARLY);
    }

    public static Map<String, Integer> getAllSortMap(Map<String, Integer> sortMap) {
        return getSortMap(sortMap, null, null);
    }

    public static Map<String, Integer> getSortMap(Map<String, Integer> sortMap) {
        return getSortMap(sortMap, SAME, NOTBAD);
    }

    public static Map<String, Integer> getSortMap(Map<String, Integer> sortMap, Integer min) {
        return getSortMap(sortMap, SAME, min);
    }

    public static Map<String, Integer> getSortMap(Map<String, Integer> sortMap, Integer max, Integer min) {
        List<Map.Entry<String, Integer>> mapKey = new ArrayList<Map.Entry<String, Integer>>();
        Map<String, Integer> sorted = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : sortMap.entrySet()) {
            mapKey.add(entry);
        }

        Collections.sort(mapKey, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().intValue() - o1.getValue().intValue();
            }
        });

        if ((min != null) && (max != null)) {
            for (Map.Entry<String, Integer> entry : mapKey) {
                if ((entry.getValue().intValue() <= max.intValue())
                        && (entry.getValue().intValue() >= min.intValue())) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }
        } else {
            for (Map.Entry<String, Integer> entry : mapKey) {
                sorted.put(entry.getKey(), entry.getValue());
            }
        }
        return sorted;
    }
}
