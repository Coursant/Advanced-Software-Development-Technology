package cn.edu.fdu.Lab1.domain;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.service.Command;
import cn.edu.fdu.Lab1.service.impl.*;

import java.util.HashMap;

public class CommandExecutor {
    private final FileSessionManager fileSessionManager;

    public CommandExecutor() {
        this.fileSessionManager = new FileSessionManager();
    }

    public void executeCommand(String input) {
        String[] parts = input.split(" ", 2);
        String commandName = parts[0].toLowerCase();

        try {
            switch (commandName) {
                case "insert":
                    handleInsert(parts[1]);
                    break;
                case "append":
                    handleAppend(parts[1]);
                    break;
                case "delete":
                    handleDelete(parts[1]);
                    break;
                case "edit-id":
                    handleEditId(parts[1]);
                    break;
                case "edit-text":
                    handleEditText(parts[1]);
                    break;
                case "print-tree":
                    handlePrintTree();
                    break;
                case "print-indent":
                    handlePrintIndent(parts[1]);
                    break;
                case "spell-check":
                    handleSpellCheck();
                    break;
                case "undo":
                    handleUndo();
                    break;
                case "redo":
                    handleRedo();
                    break;
                //钱：添加多编辑器命令
                case "load":
                    fileSessionManager.loadFile(parts[1]);
                    break;
                case "save":
                    fileSessionManager.saveFile(parts[1]);
                    break;
                case "close":
                    fileSessionManager.closeFile();
                    break;
                case "editor-list":
                    fileSessionManager.listEditors();
                    break;
                case "edit":
                    fileSessionManager.editFile(parts[1]);
                    break;
                //增强部分2：是否显示#id
                case "showid":
                    handleShowid(parts[1]);
                //增强部分3：显示文件目录
                case "dir-tree":
                    handleDirTree(parts[0]);
                case "dir-ident":
                    handelDirIdent(parts[0]);
                default:
                    throw new IllegalArgumentException("Unknown command: " + commandName);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleInsert(String args) {
        String[] params = args.split(" ", 4);
        if (params.length < 4) {
            System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            return;
        }
        String tagName = params[0];
        String idValue = params[1];
        String insertLocation = params[2];
        String textContent = params[3];
        Command command = new InsertHTMLElementCommand(fileSessionManager.getActiveSession().getCommandContext(), tagName, idValue, insertLocation, textContent);
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
        fileSessionManager.getActiveSession().markAsModified();
    }

    private void handleAppend(String args) {
        String[] params = args.split(" ", 4);
        if (params.length < 4) {
            System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            return;
        }
        String tagName = params[0];
        String idValue = params[1];
        String parentElementId = params[2];
        String textContent = params[3];
        Command command = new AppendHTMLElementCommand(fileSessionManager.getActiveSession().getCommandContext(), tagName, idValue, parentElementId, textContent);
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
        fileSessionManager.getActiveSession().markAsModified();
    }

    private void handleDelete(String args) {
        String idValue = args.trim();
        Command command = new DeleteHTMLElementCommand(fileSessionManager.getActiveSession().getCommandContext(), idValue);
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
        fileSessionManager.getActiveSession().markAsModified();
    }

    private void handleEditId(String args) {
        String[] params = args.split(" ");
        if (params.length < 2) {
            System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            return;
        }
        String oldId = params[0];
        String newId = params[1];
        Command command = new EditHTMLElementIdCommand(fileSessionManager.getActiveSession().getCommandContext(), oldId, newId);
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
        fileSessionManager.getActiveSession().markAsModified();
    }

    private void handleEditText(String args) {
        String[] params = args.split(" ", 2);
        if (params.length < 2) {
            System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            return;
        }
        String idValue = params[0];
        String newTextContent = params[1];
        Command command = new EditHTMLElementTextCommand(fileSessionManager.getActiveSession().getCommandContext(), idValue, newTextContent);
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
        fileSessionManager.getActiveSession().markAsModified();
    }

    private void handlePrintTree() {
        HTMLElement root = fileSessionManager.getActiveSession().getCommandContext().getIdMap().get("html");
        if (root != null) {
            System.out.println(root.print(0));
        } else {
            System.out.println("HTML tree is empty.");
        }
    }

    private void handlePrintIndent(String args) {
        int indent = Integer.parseInt(args.trim());
        HTMLElement root = fileSessionManager.getActiveSession().getCommandContext().getIdMap().get("html");
        if (root != null) {
            System.out.println(root.print(indent));
        } else {
            System.out.println("HTML tree is empty.");
        }
    }

    private void handleSpellCheck() {
        Command command = new SpellCheckCommand(fileSessionManager.getActiveSession().getCommandContext());
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
    }

    private void handleUndo() {
        fileSessionManager.getActiveSession().getCommandHistory().undo();
        fileSessionManager.getActiveSession().markAsModified();
    }
    private void handleRedo() {
        fileSessionManager.getActiveSession().getCommandHistory().redo();
        fileSessionManager.getActiveSession().markAsModified();

    }

    private void handleShowid(String args){
        HTMLElement root = fileSessionManager.getActiveSession().getCommandContext().getIdMap().get("html");
        Command command = new ShowIdCommand(fileSessionManager.getActiveSession().getCommandContext(),root,args);
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
        fileSessionManager.getActiveSession().markAsModified();
    }

    private void handleDirTree(String args){
        Command command = new DirCommand(fileSessionManager.getSessions(),args,fileSessionManager.getActiveSession().getFilename());
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
        fileSessionManager.getActiveSession().markAsModified();
    }
    private void handelDirIdent(String args){
        Command command = new DirCommand(fileSessionManager.getSessions(),args,fileSessionManager.getActiveSession().getFilename());
        fileSessionManager.getActiveSession().getCommandHistory().addCommand(command);
        fileSessionManager.getActiveSession().markAsModified();
    }
}