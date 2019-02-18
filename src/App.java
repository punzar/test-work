import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    private static Map<String, String> commandKeys = new HashMap<String, String>();
    private static List<String> inputFiles = new ArrayList<String>();

    public static void main(String[] args) {

        if (args.length == 0) {
            printHelp();
            System.exit(0);
        } else {
            collectArgs(args);
        }

        for (String file : inputFiles) {

            IPropAccess ps = new Props(file);
            Redactor redactor = new Redactor();
            HtmlResumeBuilder builder = new HtmlResumeBuilder(ps);
            String tplt = commandKeys.get("template");
            try {
                if (tplt == null)
                    throw new Exception("No template specified.");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            redactor.composeHtmlResume(builder, tplt);
            String html = builder.getResult();

            try {
                File cv = new File(file.replace(".properties", ".html"));
                FileWriter writer = new FileWriter(cv);
                writer.write(html);
                writer.close();

            } catch (IOException e) {
                System.err.println("Не удалось создать файл анкеты.");
                System.exit(0);
            } catch (Exception t) {
                System.err.println("Не удалось создать файл анкеты.");
                System.exit(0);
            }
        }

    }

    private static void printHelp() {
        System.out.println("Resumaker v0.1, 2019");
    }

    private static void collectArgs(String[] args) {
        for (int j = 0; j < args.length; j++) {
            String s = args[j];
            boolean canNext = j < args.length - 1;
            String next = canNext ? args[j + 1] : null;

            if (s.endsWith(".properties"))
                inputFiles.add(s);
            else if (s.startsWith("--") && canNext && !next.startsWith("--")) {
                commandKeys.put(s.substring(2), args[j + 1]);
                j++;
            }

        }
    }

}
