
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Props implements IPropAccess {

    private static final long serialVersionUID = -946308138953031874L;
    private Properties properties;

    Props(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            this.properties = new Properties();
            this.properties.load(fileReader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<String> getProp(String key) {
        String[] values = properties.getProperty(key).split(",");
        return new ArrayList<String>(Arrays.asList(values));
    }

    @Override
    public Map<String, List<String>> getAll() {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        properties.forEach((k, v) -> {
            map.put(k.toString(), getProp(k.toString()));
        });

        return map;
    }

}