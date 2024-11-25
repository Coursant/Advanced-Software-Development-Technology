package cn.edu.fdu.Lab1.util.impl;

import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.FileSession;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.util.SpellCheckUtil;
import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpellCheckUtilImplTest extends TestCase {

    public void testCheck() throws Exception {
        FileSession session = new FileSession("test.html");
        Path filePath = Paths.get("test.html");
        String content = Files.readString(filePath);

        CommandContext commandcontext = session.getCommandContext();
        commandcontext.parser(content);
        HTMLElement HTML = session.getCommandContext().getIdMap().get("html");
        SpellCheckUtil util = new SpellCheckUtilImpl();
        util.checkSpellErrors(HTML);
        String out = HTML.print(4);
        System.out.println(out);
    }
}