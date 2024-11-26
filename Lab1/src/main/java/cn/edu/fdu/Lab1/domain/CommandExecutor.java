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

    private FileSession getActiveSession() {
        return fileSessionManager.getActiveSession();
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
                    handleLoadFile(parts[1]);
                    break;
                case "save":
                    handleSaveFile(parts[1]);
                    break;
                case "close":
                    handleCloseFile();
                    break;
                case "editor-list":
                    handleListEditors();
                    break;
                case "edit":
                    handleEditFiles(parts[1]);
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
        Command command = new InsertHTMLElementCommand(getActiveSession().getCommandContext(), tagName, idValue, insertLocation, textContent);
        getActiveSession().getCommandHistory().addCommand(command);
        getActiveSession().markAsModified();
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
        Command command = new AppendHTMLElementCommand(getActiveSession().getCommandContext(), tagName, idValue, parentElementId, textContent);
        getActiveSession().getCommandHistory().addCommand(command);
        getActiveSession().markAsModified();
    }

    private void handleDelete(String args) {
        String idValue = args.trim();
        Command command = new DeleteHTMLElementCommand(getActiveSession().getCommandContext(), idValue);
        getActiveSession().getCommandHistory().addCommand(command);
        getActiveSession().markAsModified();
    }

    private void handleEditId(String args) {
        String[] params = args.split(" ");
        if (params.length < 2) {
            System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            return;
        }
        String oldId = params[0];
        String newId = params[1];
        Command command = new EditHTMLElementIdCommand(getActiveSession().getCommandContext(), oldId, newId);
        getActiveSession().getCommandHistory().addCommand(command);
        getActiveSession().markAsModified();
    }

    private void handleEditText(String args) {
        String[] params = args.split(" ", 2);
        if (params.length < 2) {
            System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            return;
        }
        String idValue = params[0];
        String newTextContent = params[1];
        Command command = new EditHTMLElementTextCommand(getActiveSession().getCommandContext(), idValue, newTextContent);
        getActiveSession().getCommandHistory().addCommand(command);
        getActiveSession().markAsModified();
    }

    private void handlePrintTree() {
        HTMLElement root = getActiveSession().getCommandContext().getIdMap().get("html");
        if (root != null) {
            System.out.println(root.print(0));
        } else {
            System.out.println("HTML tree is empty.");
        }
    }

    private void handlePrintIndent(String args) {
        int indent = Integer.parseInt(args.trim());
        HTMLElement root = getActiveSession().getCommandContext().getIdMap().get("html");
        if (root != null) {
            System.out.println(root.print(indent));
        } else {
            System.out.println("HTML tree is empty.");
        }
    }

    private void handleSpellCheck() {
        Command command = new SpellCheckCommand(getActiveSession().getCommandContext());
        getActiveSession().getCommandHistory().addCommand(command);
    }

    private void handleUndo() {
        getActiveSession().getCommandHistory().undo();
        getActiveSession().markAsModified();
    }
    private void handleRedo() {
        getActiveSession().getCommandHistory().redo();
        getActiveSession().markAsModified();

    }private void handleLoadFile(String args) {
        Command command = new LoadFileCommand(fileSessionManager, args);
        command.execute();
    }

    private void handleSaveFile(String args) {
        Command command = new SaveFileCommand(fileSessionManager, args);
        command.execute();
    }

    private void handleCloseFile() {
        Command command = new CloseFileCommand(fileSessionManager);
        command.execute();
    }

    private void handleListEditors() {
        Command command = new ListEditorsCommand(fileSessionManager);
        command.execute();
    }

    private void handleEditFiles(String args) {
        Command command = new EditFileCommand(fileSessionManager, args);
        command.execute();
    }

    private void handleShowid(String args){
        HTMLElement root = getActiveSession().getCommandContext().getIdMap().get("html");
        Command command = new ShowIdCommand(getActiveSession().getCommandContext(),root,args);
        getActiveSession().getCommandHistory().addCommand(command);
        getActiveSession().markAsModified();
    }

    private void handleDirTree(String args){
        Command command = new DirCommand(fileSessionManager.getSessions(),args,getActiveSession().getFilename());
        getActiveSession().getCommandHistory().addCommand(command);
        getActiveSession().markAsModified();
    }
    private void handelDirIdent(String args){
        Command command = new DirCommand(fileSessionManager.getSessions(),args,getActiveSession().getFilename());
        getActiveSession().getCommandHistory().addCommand(command);
        getActiveSession().markAsModified();
    }
}