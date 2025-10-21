package cc.duduhuo.cacheversioncheck.demo1

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JdbcTests {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun test() {
        val tableName = "user_test"
        // 1. 首先创建数据表
        val ddl = """
            CREATE TABLE IF NOT EXISTS `${tableName}` (
                id BIGINT PRIMARY KEY NOT NULL,
                name VARCHAR(20),
                create_at VARCHAR(30)
            );
        """.trimIndent()

        jdbcTemplate.execute(ddl)

        // 2. 插入一条数据
        val ret = jdbcTemplate.update(
            "INSERT INTO `${tableName}` (`id`, `name`, `create_at`) VALUES (?, ?, ?);",
            1L,
            "test",
            LocalDateTime.now().toString()
        )
        logger.info("Insert result: $ret")

        // 3. 检索一条数据
        val user = jdbcTemplate.queryForMap("SELECT * FROM `${tableName}` WHERE `id` = ?", 1L)
        logger.info("User: $user")
    }
}
