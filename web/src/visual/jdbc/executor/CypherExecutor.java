package visual.jdbc.executor;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Gabriel Rubino
 */
public interface CypherExecutor {
    Iterator<Map<String,Object>> query(String statement, Map<String,Object> params);
}
