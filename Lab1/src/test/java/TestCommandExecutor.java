import cn.edu.fdu.Lab1.domain.CommandExecutor;
import org.junit.Test;

public class TestCommandExecutor {

    @Test
    public void testInsert() {
        CommandExecutor executor = new CommandExecutor();
        executor.executeCommand("insert div div1 body 'Hello World'");
        executor.executeCommand("print-tree");
    }

    @Test
    public void testAppend() {
        CommandExecutor executor = new CommandExecutor();
        executor.executeCommand("append span span1 body 'This is a span.'");
        executor.executeCommand("print-tree");
    }

    @Test
    public void testDeleteAndUndo() {
        CommandExecutor executor = new CommandExecutor();
        executor.executeCommand("insert p p1 body 'Paragraph to delete'");
        executor.executeCommand("delete p1");
        executor.executeCommand("undo");
        executor.executeCommand("print-tree");
    }

    @Test
    public void testEditId() {
        CommandExecutor executor = new CommandExecutor();
        executor.executeCommand("insert div div1 body 'Sample Text'");
        executor.executeCommand("edit-id div1 new-div1");
        executor.executeCommand("print-tree");
    }

    @Test
    public void testSpellCheck() {
        CommandExecutor executor = new CommandExecutor();
        executor.executeCommand("insert p p1 body 'This is a speled text.'");
        executor.executeCommand("spell-check");
    }
}