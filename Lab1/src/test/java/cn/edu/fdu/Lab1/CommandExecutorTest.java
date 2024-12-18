package cn.edu.fdu.Lab1;

import cn.edu.fdu.Lab1.domain.*;
import org.junit.jupiter.api.Test;

public class CommandExecutorTest {

    private CommandExecutor commandExecutor = new CommandExecutor();

    public void testLoadCommand() {
        // 测试执行 load 指令
        System.out.println("Executing testLoadCommand...");
        commandExecutor.executeCommand("load testfile.html");
    }

    @Test
    public void testInsertCommand() {
        testLoadCommand();
        // 执行 insert 指令
        System.out.println("Executing testInsertCommand...");
        commandExecutor.executeCommand("insert div div1 body this is a test div1.");
        testPrintTreeCommand();
    }

    @Test
    public void testAppendCommand() {
        testLoadCommand();
        // 执行 append 指令
        System.out.println("Executing testAppendCommand...");
        commandExecutor.executeCommand("insert div div2 body this is a test div2.");
        testPrintTreeCommand();
    }

    @Test
    public void testDeleteCommand() {
        testLoadCommand();
        testInsertCommand();
        // 执行 delete 指令
        System.out.println("Executing testDeleteCommand...");
        commandExecutor.executeCommand("delete div1");
        testPrintTreeCommand();
    }

    @Test
    public void testPrintTreeCommand() {
        testLoadCommand();
        // 执行 printTree 指令
        System.out.println("Executing testPrintTreeCommand...");
        commandExecutor.executeCommand("print-tree");
    }
}
