package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_2__Add_time_stamp extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));
        jdbcTemplate.execute("ALTER TABLE transfer ADD COLUMN created TIMESTAMP WITH TIME ZONE ");
        jdbcTemplate.execute("ALTER TABLE transfer ALTER COLUMN created SET DEFAULT CURRENT_TIMESTAMP ");
    }
}
