package cn.edu.fdu.Lab1.util;

import cn.edu.fdu.Lab1.domain.HTMLElement;

import java.io.IOException;

/**
 * 拼写检查工具接口
 */
public interface SpellCheckUtil {
    public boolean check(String content) throws IOException;
    public void checkSpellErrors(HTMLElement element);
}
