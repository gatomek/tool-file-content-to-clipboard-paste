package pl.gatomek.tool.paster;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class Paster {
    private static final Logger LOGGER = Logger.getLogger("paster");

    public static void main(String[] args) {
        try {
            String filePath = getFilePathFromArgs(args);
            String fileContent = readTextFileContent(filePath);
            copyToClipboard(fileContent);
        } catch (NoArgException e) {
            LOGGER.severe("No args given");
        } catch (NoSuchFileException e) {
            LOGGER.severe("No such file exception: " + e.getFile());
        } catch (IOException e) {
            LOGGER.severe("IO exception");
        } catch (NoFileContentException e) {
            LOGGER.severe("No file content exception");
        }
    }

    private static String getFilePathFromArgs(String[] args) {
        if (args == null || args.length == 0)
            throw new NoArgException();

        return args[0];
    }

    public static String readTextFileContent(String p) throws IOException {
        Path path = Paths.get(p);

        List<String> strings = Files.readAllLines(path, StandardCharsets.UTF_8);
        if (strings.isEmpty()) {
            throw new NoFileContentException();
        }

        return strings.getFirst();
    }


    public static void copyToClipboard(String text) {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection data = new StringSelection(text);
        cb.setContents(data, null);
    }
}

class NoArgException extends RuntimeException {
}

class NoFileContentException extends RuntimeException {
}

