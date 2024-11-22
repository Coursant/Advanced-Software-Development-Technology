package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;

public class PrintTreeCommand implements Command {
    private CommandContext context;
    private HTMLElement rootElement;

    public PrintTreeCommand(CommandContext context, HTMLElement rootElement) {
        this.context = context;
        this.rootElement = rootElement;
    }

    @Override
    public void execute() {
        System.out.println(rootElement.print(0));
    }

    @Override
    public void undo() {
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }
}

