package cn.edu.fdu.Lab1.domain;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.service.Command;

import java.util.Stack;

public class CommandHistory {
    private Stack<Command> history = new Stack<>();
    private Stack<Command> undoHistory = new Stack<>();

    /**
     * 执行命令并将其添加到历史记录中
     * @param command
     */
    public void addCommand(Command command) {
        command.execute();
        history.push(command);
        undoHistory.clear();
    }

    /**
     * 撤销最近的命令
     */
    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
            undoHistory.push(command);
        } else {
            System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
        }
    }

    /**
     * 重做最近撤销的命令
     */
    public void redo() {
        if (!undoHistory.isEmpty()) {
            Command command = undoHistory.pop();
            command.execute();
            history.push(command);
        } else {
            System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
        }
    }
}

