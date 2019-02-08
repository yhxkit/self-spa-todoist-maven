package config;
//JSP를 위한 뷰리졸버 설정
//디폴트 서블릿 핸들러 설정

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration // @Component의 기능까지 포함하고 있음... @Configuration이 스캔 대상이 된다고~
@EnableWebMvc //    <mvc:annotation-driven /> 과 동일... 서블릿 관리하는데서 쓰면 되는...
@ComponentScan(basePackages = "com.test.prob")// com.test.prob 패키지에 위치한 스프링빈을 자동으로 등록하도록 함
public class MvcConfig extends WebMvcConfigurerAdapter {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MvcConfig.class);

    //JSP를 위한 viewResolver를 설정할때에는 configureViewResolvers() 메서드를 오버라이드
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
////        super.configureViewResolvers(registry);
//        registry.jsp().prefix("/WEB-INF.view/").suffix(".jsp");
//        //jsp메서드는 InternalRsourceViewResolver를 설정하므로 UrlBasedResolverRegistration객체를 리턴...
//        //이 객체의 prefix suffix 메서드로 필요한부분 설정가능
//        //suffix 는 .sp 가 기본값이라 생략 가능
//    }
    //문제는 스프링 4.0은 위의 방식을 지원하지 않으므로 InternalResourceViewResolver타입의 빈을 직접 설정해줘야 한다고 함...ㅎ....
    @Bean
    public  ViewResolver viewResolver(){
        log.info(" 뷰 리졸버 ");
        InternalResourceViewResolver result = new InternalResourceViewResolver();
        result.setPrefix("/WEB-INF/view/");
        result.setSuffix(".jsp");
//        result.setSuffix(".html");
        return result;
    }

/////////////////////////////


@Override//디폴트 서블릿 핸들러 설정
public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//    super.configureDefaultServletHandling(configurer);
    log.info("디폴트 서블릿핸들러");
    configurer.enable();

    }


}
