package com.wuda.testdata;

import com.wuda.testdata.cli.CliArgs;
import com.wuda.testdata.cli.CliOptionUtils;
import com.wuda.testdata.config.DatabaseConfiguration;
import com.wuda.testdata.generate.AlreadyStoppedException;
import com.wuda.testdata.generate.DataGenerator;
import com.wuda.testdata.generate.FullDataGenerator;
import com.wuda.testdata.orm.MyObjectRelationalMapper;
import com.wuda.testdata.repository.MysqlRepository;
import com.wuda.testdata.statistic.DataGenerateStat;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

/**
 * 程序启动类.以SpringBoot项目方式启动.
 *
 * @author wuda
 */
@SpringBootApplication
@MapperScan(basePackages = "com.wuda.testdata.entity")
public class Bootstrap {

    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        // 解析命令行参数
        CliArgs cliArgs = CliOptionUtils.parser(CliOptionUtils.getOptions(), args);
        cliArgs.validate();
        // spring boot
        ConfigurableApplicationContext context = SpringApplication.run(Bootstrap.class, args);
        SpringApplicationContextHolder.setApplicationContext(context);
        // 使用命令行传过来的数据库参数
        DatabaseConfiguration.applyArgs(context.getBean(DataSource.class), cliArgs);
        // 数据库访问类
        MysqlRepository mysqlRepository = context.getBean(MysqlRepository.class);
        MyObjectRelationalMapper orm = new MyObjectRelationalMapper(cliArgs);
        DataGenerateStat statistic = new DataGenerateStat();
        for (int i = 0; i < cliArgs.getThread(); i++) {
            // 全量数据生成器
            DataGenerator generator = new FullDataGenerator(mysqlRepository, orm, statistic, cliArgs);
            try {
                // 启动线程,执行数据生成任务
                generator.generate();
            } catch (AlreadyStoppedException e) {
                // 不会发生,因为没有多次调用
                logger.warn(e.getMessage(), e);
            }
        }
    }
}
