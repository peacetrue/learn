package com.github.peacetrue.learn.jline;

import org.jline.builtins.Completers;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

/**
 * @author peace
 **/
public class Main {

    //tag::basic[]
    public static void main(String[] args) throws IOException {
        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(TerminalBuilder.builder().system(true).build())
                .completer(new AggregateCompleter(
                        new ArgumentCompleter(
                                new StringsCompleter("CREATE"),
                                new Completers.FileNameCompleter(),
                                NullCompleter.INSTANCE
                        ),
                        new ArgumentCompleter(
                                new StringsCompleter("OPEN"),
                                new Completers.FileNameCompleter(),
                                new StringsCompleter("AS"),
                                NullCompleter.INSTANCE
                        )
                ))
                .build();

        String prompt = "peace > ";
        while (true) {
            String line;
            try {
                line = lineReader.readLine(prompt);
                // 如何根据 line 绑定到 方法
                System.out.println(line);
            } catch (UserInterruptException e) {
                // Do nothing
            } catch (EndOfFileException e) {
                System.out.println("\nBye.");
                return;
            }
        }
    }
    //end::basic[]
}
