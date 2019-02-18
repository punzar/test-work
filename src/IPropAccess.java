import java.util.List;
import java.util.Map;

public interface IPropAccess {

    List<String> getProp(String key);

    Map<String, List<String>> getAll();

}