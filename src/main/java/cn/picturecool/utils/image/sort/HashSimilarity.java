package cn.picturecool.utils.image.sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashSimilarity {

    public static int hammingDistance(String sourceHashCode, String hashCode) {
        int difference = 0;
        int len = sourceHashCode.length();
        for (int i = 0; i < sourceHashCode.length(); i++) {
            if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
                difference++;
            }
        }
        return difference;
    }

    public static int getSimilarity(String sourceHashCode, String hashCode) {
        int difference = 0;
        int result = 0;
        int len = sourceHashCode.length();
        difference = HashSimilarity.hammingDistance(sourceHashCode, hashCode);
        result = ((len - difference) * 100) / len;
        return result;
    }

    public static Map<String, Integer> getSimilarity(List<String> compareList, String sourceCode) {
        Map<String, Integer> toCompareMap = new HashMap<String, Integer>();
        for (String s : compareList) {
            toCompareMap.put(s, (Integer) getSimilarity(s, sourceCode));
        }
        return toCompareMap;
    }
}
