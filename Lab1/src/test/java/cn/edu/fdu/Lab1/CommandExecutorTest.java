package cn.edu.fdu.Lab1;

import cn.edu.fdu.Lab1.domain.*;
import cn.edu.fdu.Lab1.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
        commandExecutor.executeCommand("insert div myDiv 0 This is a test div.");
        testPrintTreeCommand();
    }

    @Test
    public void testAppendCommand() {
        testLoadCommand();
        // 执行 append 指令
        System.out.println("Executing testAppendCommand...");
        commandExecutor.executeCommand("append p myParagraph 0 This is a paragraph.");
        testPrintTreeCommand();
    }

    @Test
    public void testDeleteCommand() {
        testLoadCommand();
        testInsertCommand();
        // 执行 delete 指令
        System.out.println("Executing testDeleteCommand...");
        commandExecutor.executeCommand("delete myDiv");
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
