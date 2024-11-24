package cn.edu.fdu.Lab1.util.impl;

import cn.edu.fdu.Lab1.util.SpellCheckUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SpellCheckUtilImpl implements SpellCheckUtil {
    private static final Set<String> DICTIONARY = new HashSet<>(Arrays.asList(
            "hello", "world", "sample", "text", "paragraph", "html", "div", "span", "body"
    ));

    /**
     * 查询拼写错误
     * @param content
     * @return
     */
    @Override
    public  boolean check(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }

        String[] words = content.split("\\s+");
        for (String word : words) {
            // 有单词不在词典中找到拼写错误，返回true
            if (!DICTIONARY.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
