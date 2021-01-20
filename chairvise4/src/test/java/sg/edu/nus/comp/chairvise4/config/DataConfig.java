package sg.edu.nus.comp.chairvise4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/7/2020 4:36 PM
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("sg.edu.nus.comp.chairvise4.repository")
public class DataConfig {
}
