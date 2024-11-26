package cn.edu.fdu.Lab1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileSessionTest {
    private FileSession fileSession;

    @BeforeEach
    void setUp() {
        // Initialize the FileSession object with a test filename
        fileSession = new FileSession("test.html");
        // The path for the test HTML file
        String filePath = "test.html";

        // Sample HTML content
        String htmlContent = "    <html id=\"html\">\n" +
                "      <head id=\"head\">\n" +
                "      </head>\n" +
                "      <body id=\"body\">\n" +
                "      </body>\n" +
                "\n" +
                "    </html>";

        // Create the file and write the HTML content to it
        try (FileWriter fileWriter = new FileWriter(new File(filePath))) {
            fileWriter.write(htmlContent);
            System.out.println("File 'test.html' created and content written.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testInitialization() {
        assertNotNull(fileSession.getCommandContext(), "Command context should be initialized.");
        assertNotNull(fileSession.getCommandHistory(), "Command history should be initialized.");
        assertNotNull(fileSession.getFileSessionList(), "File session list should be initialized.");
    }


    @Test
    void testSave() throws IOException {
        // Set the content to be saved
        fileSession.markAsModified(); // Mark file as modified

        // Save the file
        fileSession.save();

        // Verify that the file exists and is not modified
        assertFalse(fileSession.isModified(), "File should be marked as unmodified after saving.");
        assertTrue(Files.exists(Paths.get(fileSession.getFilename())), "File should be saved to the disk.");
    }


    @Test
    void testMarkAsModified() {
        fileSession.markAsModified();
        assertTrue(fileSession.isModified(), "File should be marked as modified.");
    }

    @Test
    void testGetFilename() {
        assertEquals("testFile.html", fileSession.getFilename(), "Filename should be 'testFile.html'.");
    }
}
