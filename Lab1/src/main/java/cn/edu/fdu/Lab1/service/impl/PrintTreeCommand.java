package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;
import cn.edu.fdu.Lab1.util.impl.SpellCheckUtilImpl;

import java.io.IOException;

public class PrintTreeCommand implements Command {
    private CommandContext context;
    private HTMLElement rootElement;
    private SpellCheckUtilImpl spellCheckUtil;

    public PrintTreeCommand(CommandContext context, HTMLElement rootElement) {
        this.context = context;
        this.rootElement = rootElement;
    }

    @Override
    public void execute() {
        checkSpellErrors(rootElement);
        System.out.println(rootElement.print(0));
    }

    @Override
    public void undo() {
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }

    /**
     * 递归检查拼写错误
     * @param element
     */
    private void checkSpellErrors(HTMLElement element) {
        try {
            if (spellCheckUtil.check(element.getTextContent())) {
                element.setHasSpellError(true);
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

