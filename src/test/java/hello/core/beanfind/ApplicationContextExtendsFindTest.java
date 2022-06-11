package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 중복 오류가 발생한다.")
    void findBeanByParentTypeDuplicate(){
//        DiscouhntPolicy bean = ac.getBean(DiscouhntPolicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class ,
                () -> ac.getBean(DiscountPolicy.class));
    }
    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 빈 이름을 지정하면 된다")
    void findBeanByParentTypeBeanName(){
        DiscountPolicy rateDisocuntPolicy = ac.getBean("rateDisocuntPolicy", DiscountPolicy.class);
        assertThat(rateDisocuntPolicy).isInstanceOf(RateDiscountPolicy.class);
    }
    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType(){
        RateDiscountPolicy rateDiscountPolicy = ac.getBean(RateDiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);

    }
    @Test
    @DisplayName("부모 타입으로 모두 조회하기기")
    void findBeanByParentType(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String s : beansOfType.keySet()) {
            System.out.println("key : " + s + " value : " + beansOfType.get(s));

        }

    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기기 - Object")
    void findAllBeanByObject(){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
//        assertThat(beansOfType.size()).isEqualTo(2);
        for (String s : beansOfType.keySet()) {
            System.out.println("key : " + s + " value : " + beansOfType.get(s));

        }

    }

    @Configuration
    static class TestConfig{
        @Bean
        public DiscountPolicy rateDisocuntPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDisocuntPolicy(){
            return new FixDiscountPolicy();
        }
    }
}
