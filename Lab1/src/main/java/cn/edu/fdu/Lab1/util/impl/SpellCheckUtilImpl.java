package cn.edu.fdu.Lab1.util.impl;

import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.util.SpellCheckUtil;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpellCheckUtilImpl implements SpellCheckUtil {
    JLanguageTool langTool = new JLanguageTool(new BritishEnglish());



    private static final Set<String> DICTIONARY = new HashSet<>(Arrays.asList(
            "hello", "world", "sample", "text", "paragraph", "html", "div", "span", "body","p"
    ));

    /**
     * 查询拼写错误
     * @param content
     * @return
     */
    @Override
    public  boolean check(String content) throws IOException {
        if (content == null || content.isEmpty()) {
            return false;
        }

        String[] words = content.split("\\s+");
        for (String word : words) {
            // 有单词不在词典中找到拼写错误，返回true
            if (!langTool.check(word.toLowerCase()).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    public void checkSpellErrors(HTMLElement element) {
        try {
            if (!check(element.getTextContent())) {
                element.setHasSpellError(false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //递归检查子标签
        for (HTMLElement child : element.getChildren()) {
            checkSpellErrors(child);
        }
    }
}
