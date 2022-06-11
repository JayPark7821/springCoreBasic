package hello.core.discount;

import hello.core.annotation.MainDisCountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component
@MainDisCountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    // ctrl + shift + t test생성
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP){
            return price * discountPercent/100;
        }else{
            return 0;
        }

    }
}
