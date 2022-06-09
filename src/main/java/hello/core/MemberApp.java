package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

//        MemberService memberService = new MemberServiceImpl();

        // 스프링은 모든것이 ApplicationContext 라는것으로 시작 (스프링 컨테이너라고 보면된다)
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1l, "memberA", Grade.VIP);
        memberService.join(member);;

        Member findMember = memberService.findMember(1l);
        System.out.println("new member : " + member.getName());
        System.out.println("find member : " + findMember.getName());
    }

}
