package verso;

import java.sql.SQLException;

public interface Session {
    void exec(String sql) throws SQLException;
}
