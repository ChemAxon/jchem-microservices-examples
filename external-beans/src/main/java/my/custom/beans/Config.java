package my.custom.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }
    
    @Bean
    public Bean2 bean2() {
        return new Bean2();
    }
    
}
