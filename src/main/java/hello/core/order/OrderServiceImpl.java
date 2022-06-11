package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;

    // ctrl + alt + b 구현체들 나옴
    private final DiscountPolicy discountPolicy;
//
//    public OrderServiceImpl(MemberRepository memberRepository, DiscouhntPolicy discouhntPolicy) {
//        this.memberRepository = memberRepository;
//        this.discouhntPolicy = discouhntPolicy;
//    }


//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscouhntPolicy discouhntPolicy = new FixDiscountPolicy();
//    private final DiscouhntPolicy discouhntPolicy = new RateDiscountPolicy();

//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscouhntPolicy discouhntPolicy;


//    @Autowired
//    public void setDiscouhntPolicy(DiscouhntPolicy discouhntPolicy) {
//        this.discouhntPolicy = discouhntPolicy;
//    }
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    public OrderServiceImpl(MemberRepository memberRepository, DiscouhntPolicy discouhntPolicy) {
//        this.memberRepository = memberRepository;
//        this.discouhntPolicy = discouhntPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
