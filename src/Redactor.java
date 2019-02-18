public class Redactor {

    public Redactor() {

    }

    public void composeHtmlResume(IResumeBuilder builder, String templatePath) {
        builder.applyTemplate(templatePath);
    }

}