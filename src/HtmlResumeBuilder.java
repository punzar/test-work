
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class HtmlResumeBuilder implements IResumeBuilder {

  private IPropAccess props = null;
  private String templatePath = "";

  private StringBuilder markup = new StringBuilder();

  public HtmlResumeBuilder(IPropAccess props) {
    this.props = props;
  }

  private void LoadTemplate(String templatePath) {

    try {

      BufferedReader br = new BufferedReader(new FileReader(templatePath));

      String line;
      while ((line = br.readLine()) != null) {
        markup.append(line);
      }

      int x = 4;

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void reset() {
    if (markup != null)
      markup.setLength(0);

  }

  public String getResult() {
    return markup.toString();
  }

  /**
   * Заменяет переменные вида {{ properyName }} в пераданном HTML-шаблоне
   * значением из properties фала, которое соответствует ключу в двойных фигурных
   * скобках
   */
  @Override
  public void applyTemplate(String templatePath) {

    reset();
    LoadTemplate(templatePath);

    Map<String, List<String>> all = props.getAll();
    all.forEach((k, v) -> {
      String pattern = "\\{\\{\\s*" + k + "\\s*\\}\\}";
      String buff = markup.toString();

      if (v.size() == 1) {
        String val = v.get(0);

        if (k.matches(".*[uU][rR][lL].*")) {
          buff = buff.replaceAll(pattern, "<a target=\"_blank\" href=\"" + val + "\">" + val + "</a>");
        } else
          buff = buff.replaceAll(pattern, val);

      } else if (v.size() > 1) {
        StringBuilder ul = new StringBuilder();
        ul.append("<ul class=\"list-group\">");
        v.forEach(el -> {
          if (el != null) {
            if (k.matches(".*[uU][rR][lL][sS]?.*")) {
              ul.append("<li class=\"list-group-item\"><a target=\"_blank\" href=\"").append(el).append("\">")
                  .append(el).append("</a></li>");
            } else {
              ul.append("<li class=\"list-group-item\">").append(el).append("</li>");
            }
          }
        });
        ul.append("</ul>");

        buff = buff.replaceAll(pattern, ul.toString());
      }

      markup.setLength(0);
      markup.append(buff);

    });

  }

}