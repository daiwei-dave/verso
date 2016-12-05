package verso;

import java.sql.SQLException;

public interface Session {
    <T> T select(String sql, Class<T> mapperResult, Object... arg) throws SQLException;
}
