package md2html;

import markup.Document;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;


public class Md2Html {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Specify exact 2 arguments: input and output file names");
            return;
        }
        try {
            String input = Files.readString(Path.of(args[0]), CHARSET);
            MarkdownParser markdownParser = new MarkdownParser();
            Document parsed = markdownParser.parse(input);
            StringBuilder output = new StringBuilder();
            parsed.toHtml(output);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], CHARSET));
                try {
                    writer.write(output.toString());
                } catch (IOException e) {
                    System.err.println("IO error while writing to file: " + e.getMessage());
                } finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        System.err.println("IO error while closing write file");
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Output file not found: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("IO error while opening write file: " + e.getMessage());
            }
        } catch (InvalidPathException e) {
            System.err.println("Invalid input file path: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            System.err.println("Input file is too big: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("Not allowed to read input file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error while reading input file: " + e.getMessage());
        }
    }
}
