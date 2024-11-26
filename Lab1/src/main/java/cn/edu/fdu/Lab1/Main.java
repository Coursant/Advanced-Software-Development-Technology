package cn.edu.fdu.Lab1;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandExecutor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CommandExecutor executor = new CommandExecutor();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HTML Editor!");
        while (true) {
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Exiting...");
                scanner.close();
                break;
            }
            try {
                executor.executeCommand(input);
            } catch (Exception e) {
                System.out.println(ErrorMessageEnum.UNKNOWN_COMMAND.getType());
            }
        }
    }
}
