package config;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.test.prob") // 스프링 데이터 JPA 설정... 리포지토리를 이용해서 반복되는 기본 기능 구조에 대한 구현을 줄일 수 있음
public class SpringConfig {

    @Value("${db.driver}")
    private String driver;

    @Value("${db.jdbcURL}")
    private String jdbcURL;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;



    @Bean //프로퍼티 파일 설정
    public static PropertySourcesPlaceholderConfigurer properties(){
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("db.properties"));
        return  configurer;
    }


    @Bean(destroyMethod = "close")
    public ComboPooledDataSource dataSource() { //db 연결은 위한 dataSource 설정...
        // 왜 예제처럼 그냥 DataSource 를 리턴타입으로 설정했을 때 디스트로이 메서드에 에러나지?

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl(jdbcURL);
        dataSource.setUser(user);
        dataSource.setPassword(password);


        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean emfFactory() {
        // LocalContainerEntityManagerFactoryBean 클래스는 EntityManagerFactory를 초기화할때 META-INF/persistence.xml 파일을 사용
        //JPA의 PErsistence.createEntityManagerFactory를 사용해서 EntityManagerFactory를 생성할 때와의 차이점은 스프링에서 설정한 DataSource를 사용한다는 것


        LocalContainerEntityManagerFactoryBean emfBean =  new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(dataSource()); //팩토리를 초기화하기 위한 팩토리빈 생성시 dataSource 사용
        emfBean.setPersistenceUnitName("testjpa");

        HibernateJpaVendorAdapter jva = new HibernateJpaVendorAdapter();
        jva.setDatabase(Database.MYSQL);
        jva.setShowSql(true);
        emfBean.setJpaVendorAdapter(jva);
        return emfBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        //JPA 트랜잭션 매니저 설정. 스프링 트랜잭션과 JPA 트랜잭션을 연동한다. 이 트랜잭션 관리자를 사용하면 JPA 트랜잭션 사용할 필요없이 스프링 트랜잭션 처리 기능을 사용하면 됨
        JpaTransactionManager txMgr = new JpaTransactionManager();
        txMgr.setEntityManagerFactory(emf);
        return txMgr;
    }
}
//하이버네이트의 Dialect 클래스를 사용하고 싶다면 setDatabase() 대신 setDataBsdrPlatform() 메서드에 Dialect 클래스 이름을 설정하면 된다