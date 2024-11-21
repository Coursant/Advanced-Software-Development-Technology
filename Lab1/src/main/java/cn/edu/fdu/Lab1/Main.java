package cn.edu.fdu.Lab1;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.HTMLDocument;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HTMLDocument document = new HTMLDocument();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter command:");
            String input = scanner.nextLine();
            String[] commands = input.split(" ");

            switch (commands[0]) {
                case "insert":
                    if (commands.length >= 4) {
                        String tagName = commands[1];
                        String idValue = commands[2];
                        String location = commands[3];
                        String textContent = commands.length == 5 ? commands[4] : null;
                        document.insertHTMLElement(tagName, idValue, location, textContent);
                    }
                    break;
                case "append":
                    if (commands.length >= 4) {
                        String tagName = commands[1];
                        String idValue = commands[2];
                        String parentElementId = commands[3];
                        String textContent = commands.length == 5 ? commands[4] : null;
                        document.appendHTMLElement(tagName, idValue, parentElementId, textContent);
                    }
                    break;
                case "edit-id":
                    if (commands.length == 3) {
                        document.editHTMLElementId(commands[1], commands[2]);
                    }
                    break;
                case "edit-text":
                    if (commands.length == 3) {
                        document.editHTMLElementText(commands[1], commands[2]);
                    }
                    break;
                case "delete":
                    if (commands.length == 2) {
                        document.deleteHTMLElement(commands[1]);
                    }
                    break;
                case "print-indent":
                    if (commands.length == 1) {
                        document.printIndent(2);
                    } else if (commands.length == 2) {
                        try {
                            int indent = Integer.parseInt(commands[1]);
                            document.printIndent(indent);
                        } catch (Exception e) {
                            System.out.println(ErrorMessageEnum.INVALID_ARGUMENT.getType());
                        }
                    }
                    break;
                case "print-tree":
                    document.printTree();
                    break;
                case "spell-check":
                    document.spellCheck();
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            }
        }
    }
}
