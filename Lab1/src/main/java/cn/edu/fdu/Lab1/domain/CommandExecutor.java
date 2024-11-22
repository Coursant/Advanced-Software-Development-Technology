package cn.edu.fdu.Lab1.domain;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.service.Command;
import cn.edu.fdu.Lab1.service.impl.*;

import java.util.HashMap;

public class CommandExecutor {
    private final CommandContext context;
    private final CommandHistory history;

    public CommandExecutor() {
        this.context = initializeContext();
        this.history = new CommandHistory();
    }

    private CommandContext initializeContext() {
        CommandContext context = new CommandContext();
        HTMLElement htmlElement = new HTMLElement("html", "html", null);
        HTMLElement head = new HTMLElement("head", "head", null);
        HTMLElement body = new HTMLElement("body", "body", null);

        htmlElement.addChild(head);
        htmlElement.addChild(body);

        context.setIdMap(new HashMap<>());
        context.getIdMap().put("html", htmlElement);
        context.getIdMap().put("head", head);
        context.getIdMap().put("body", body);

        return context;
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
                    history.undo();
                    break;
                case "redo":
                    history.redo();
                    break;
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
        Command command = new InsertHTMLElementCommand(context, tagName, idValue, insertLocation, textContent);
        history.addCommand(command);
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
        Command command = new AppendHTMLElementCommand(context, tagName, idValue, parentElementId, textContent);
        history.addCommand(command);
    }

    private void handleDelete(String args) {
        String idValue = args.trim();
        Command command = new DeleteHTMLElementCommand(context, idValue);
        history.addCommand(command);
    }

    private void handleEditId(String args) {
        String[] params = args.split(" ");
        if (params.length < 2) {
            System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            return;
        }
        String oldId = params[0];
        String newId = params[1];
        Command command = new EditHTMLElementIdCommand(context, oldId, newId);
        history.addCommand(command);
    }

    private void handleEditText(String args) {
        String[] params = args.split(" ", 2);
        if (params.length < 2) {
            System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            return;
        }
        String idValue = params[0];
        String newTextContent = params[1];
        Command command = new EditHTMLElementTextCommand(context, idValue, null, newTextContent);
        history.addCommand(command);
    }

    private void handlePrintTree() {
        HTMLElement root = context.getIdMap().get("html");
        if (root != null) {
            System.out.println(root.print(0));
        } else {
            System.out.println("HTML tree is empty.");
        }
    }

    private void handlePrintIndent(String args) {
        int indent = Integer.parseInt(args.trim());
        HTMLElement root = context.getIdMap().get("html");
        if (root != null) {
            System.out.println(root.print(indent));
        } else {
            System.out.println("HTML tree is empty.");
        }
    }

    private void handleSpellCheck() {
        Command command = new SpellCheckCommand(context);
        history.addCommand(command);
    }
}