package hello.core;

import hello.core.discount.DiscouhntPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig{

    // 역할과 구현 클래스가 한눈에 들어온다.
    // 어플리케이션 전체 구성이 어떻게 되어있는지 빠르게 파악할 수 있다.

    // ctrl + alt + m
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    private DiscouhntPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }


    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }


    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }


}
