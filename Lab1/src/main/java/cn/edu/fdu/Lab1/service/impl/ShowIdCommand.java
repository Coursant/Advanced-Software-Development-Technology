package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;

import java.util.Objects;

public class ShowIdCommand implements Command {
    private CommandContext context;  //命令上下文
    private HTMLElement rootElement; //html元素
    private String idValue;          //命令参数
    private boolean previousState;   //上一次的showId状态

    public ShowIdCommand(CommandContext context, HTMLElement rootElement, String idValue) {
        this.context = context;
        this.rootElement = rootElement;
        this.idValue = idValue;
        this.previousState = false;
    }
    @Override
    public void execute(){
        if (rootElement == null) {
            System.out.println("HTML元素为空");
            return;
        }
        previousState = rootElement.getIsShowId(); //获取之前的状态
        if(Objects.equals(idValue, "true")){
            rootElement.changeShowId(true);
        }else if (Objects.equals(idValue, "false")){
            rootElement.changeShowId(false);
        }else {
            System.out.println("错误指令");
        }
    }

    @Override
    public void undo(){
        if (rootElement == null) {
            System.out.println("HTML元素为空");
            return;
        }
        rootElement.changeShowId(previousState);
    }

}
