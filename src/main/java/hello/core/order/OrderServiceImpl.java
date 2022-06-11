package hello.core.order;

import hello.core.discount.DiscouhntPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    // final은 무조건 값이 할당되어야한다.
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscouhntPolicy discouhntPolicy = new FixDiscountPolicy();
//    private final DiscouhntPolicy discouhntPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscouhntPolicy discouhntPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscouhntPolicy discouhntPolicy) {
        this.memberRepository = memberRepository;
        this.discouhntPolicy = discouhntPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discouhntPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
