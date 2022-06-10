
# 스프링 핵심 원리 - 기본편

## 2022/ 06/ 07

* ### 비즈니스 요구사항과 설계

  1. 회원
      - 회원을 가입하고 조회할 수 있다.
      - 회원은 일반과 VIP 두 가지 등급이 있다.
      - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)
  2. 주문과 할인 정책
      - 회원은 상품을 주문할 수 있다.
      - 회원 등급에 따라 할인 정책을 적용할 수 있다.
      - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경 될 수 있다.)
      - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다.
          최악의 경우 할인을 적용하지 않을 수 도 있다. (미확정)
     

      요구사항을 보면 회원 데이터, 할인 정책 같은 부분은 지금 결정하기 어려운 부분이다.
      그렇다고 이런 정책이 결정될 때 까지 개발을 무기한 기다릴 수 도 없다.
      
      우리는 앞에서 배운 객체 지향 설계 방법이 있지 않은가!
      인터페이스를 만들고 구현체를 언제든지 갈아끼울 수 있도록 설계하면 된다.



* ### 회원도메인 설계

   1. 회원 도메인 요구사항
      - 회원을 가입하고 조회할 수 있다.
      - 회원은 일반과 VIP 두 가지 등급이 있다.
      - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. ( 미확정 )


![image](https://user-images.githubusercontent.com/60100532/172396784-3b35d3fc-6794-42e8-984d-0916d594694d.png)


회원클래스 다이어그램

![image](https://user-images.githubusercontent.com/60100532/172398798-e8c4dd41-731e-4ac6-94fe-64c9e9c6d5f7.png)


![image](https://user-images.githubusercontent.com/60100532/172399563-2003b4ec-7b3e-49ad-ab63-27cfc573453b.png)


## 2022/ 06/ 08

* ### 주문과 할인 도메인 설계

    1. 주문과 할인 정책
       - 회원은 상품을 주문할 수 있다.
       - 회원 등급에 따라 할인 정책을 적용할 수 있다.
       - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경 될 수 있다.)
       - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을
         미루고 싶다. 최악의 경우 할인을 적용하지 않을 수 도 있다. (미확정)

![image](https://user-images.githubusercontent.com/60100532/172598205-9becae48-16d1-46e1-9a8c-7d3a8af935ea.png)


![image](https://user-images.githubusercontent.com/60100532/172598742-29470312-a250-449b-ac77-c14ff722c051.png)
1. 주문 생성: 클라이언트는 주문 서비스에 주문 생성을 요청한다.
2. 회원 조회: 할인을 위해서는 회원 등급이 필요하다. 그래서 주문 서비스는 회원 저장소에서 회원을
   조회한다.
3. 할인 적용: 주문 서비스는 회원 등급에 따른 할인 여부를 할인 정책에 위임한다.
4. 주문 결과 반환: 주문 서비스는 할인 결과를 포함한 주문 결과를 반환한다




#### 회원클래스 다이어그램
![image](https://user-images.githubusercontent.com/60100532/172599279-ad253d3c-4a06-4d3c-83f7-14cb81ec603b.png)

역할과 구현을 분리해서 자유롭게 구현 객체를 조립할 수 있게 설계했다. 덕분에 회원 저장소는 물론이고,
할인 정책도 유연하게 변경할 수 있다.



![image](https://user-images.githubusercontent.com/60100532/172599778-ea6485d4-47d9-4913-919f-4e272b92d4be.png)

![image](https://user-images.githubusercontent.com/60100532/172599823-aaa45eda-9dec-425f-bb22-b9304bb517ad.png)

![image](https://user-images.githubusercontent.com/60100532/172600197-467d1e8d-e05e-4c3c-991b-d11255ab7d74.png)



* ### 새로운 할인 정책 개발
    #### 새로운 할인 정책을 확장해보자.

  - 악덕 기획자: 서비스 오픈 직전에 할인 정책을 지금처럼 고정 금액 할인이 아니라 좀 더 합리적인 주문 금액당 할인하는 정률% 할인으로 변경하고 싶어요. 예를 들어서 기존 정책은 VIP가 10000원을 주문하든
20000원을 주문하든 항상 1000원을 할인했는데, 이번에 새로 나온 정책은 10%로 지정해두면 고객이 10000원 주문시 1000원을 할인해주고, 20000원 주문시에 2000원을 할인해주는 거에요!
  - 순진 개발자: 제가 처음부터 고정 금액 할인은 아니라고 했잖아요.
  - 악덕 기획자: 애자일 소프트웨어 개발 선언 몰라요? “계획을 따르기보다 변화에 대응하기를”
  - 순진 개발자: … (하지만 난 유연한 설계가 가능하도록 객체지향 설계 원칙을 준수했지 후후


* ### 새로운 할인 정책 적용과 문제점

![image](https://user-images.githubusercontent.com/60100532/172649902-d4aa0dc0-0329-4d55-aa04-c3c2e4baab5d.png)

 
클래스 의존관계를 분석해 보면. 추상(인터페이스) 뿐만 아니라 구체(구현) 클래스에도 의존하고 있다.
추상(인터페이스) 의존: DiscountPolicy
구체(구현) 클래스: FixDiscountPolicy , RateDiscountPolicy

OCP: 변경하지 않고 확장할 수 있다고 했는데!
지금 코드는 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다! 따라서 OCP를 위반!!!


![image](https://user-images.githubusercontent.com/60100532/172650795-eae7e74e-8320-4d5f-8aad-0ba9da60f69a.png)

![image](https://user-images.githubusercontent.com/60100532/172650918-6151d7f8-be58-4246-8135-c3dabe4a582f.png)

* ### 어떻게 문제를 해결할 수 있을까?
  - 클라이언트 코드인 OrderServiceImpl 은 DiscountPolicy 의 인터페이스 뿐만 아니라 구체 클래스도 함께 의존한다.
  - 그래서 구체 클래스를 변경할 때 클라이언트 코드도 함께 변경해야 한다.
  - DIP 위반 추상에만 의존하도록 변경(인터페이스에만 의존)
  - DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존관계를 변경하면 된다

- 해 결 방 안
  - 이 문제를 핵결하려면 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy 객체를 대신 생성하고 주입해주어야 한다.


## 2022/ 06/ 09

* ### DIP를 지키기 

  - AppConfig 생성에서 어플리케이션의 실제 동작에 필요한 구현 객체를 생성해
    생성자 주입(연결)해줘 기존 추상과 구현 모두 의존하던 MemberServiceImpl 와 OrderServiceImpl은 단지 각 인터페이스에만 의존하도록 되었다.
  - 각 MemberServiceImpl 와 OrderServiceImpl의 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
  - 생성자를 통해서 어떤 구현 객체가 주입될지는 오직 외부 (AppConfig)에서 결정된다.
  - MemberServiceImpl 와 OrderServiceImpl은 이제 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중하면된다.

![image](https://user-images.githubusercontent.com/60100532/172851578-10107472-b89c-40c3-a8dc-86b89eae7f0f.png)

![image](https://user-images.githubusercontent.com/60100532/172851864-b36095ab-fdb5-4e17-8c3b-88b3e10830b3.png)



![image](https://user-images.githubusercontent.com/60100532/172857113-877b9113-cd87-472d-94e6-e8cdc48bcd1e.png)
![image](https://user-images.githubusercontent.com/60100532/172857185-ef348a42-dc67-421e-b0dc-1e3e33153d0e.png)


* ### 좋은 객체 지향 설계의 5가지 원칙 적용
  * 여기서 3가지 SRP, DIP, OCP적용
 
  * SRP 단일 책임의 원칙
    * 한 클래스는 하나의 책임만 가져야한다.
    * 구현 객체를 생성하고 연결하는 책임은 AppConfig가 담당
    * 클라이언트 객체는 실행하는 것만 담당 
  
  * DIP 의존관계 역전 원칙
    * 프로그래머는 추상화에 의존해야지, 구체화에 의존하면 안된다, 의존성 주입은 이 원칙을 따르는 방법중 하나다.
    * AppConfig가 클라이언트코드를 대신해 클라이언트 코드에 의존관계를 주입해준다.
  * OCP
    * 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
    * 다형성을 사용하고 클라이언트가 DIP를 지킴
    * 애플리케이션을 사용 영역과 구성 영역으로 나움
    * AppConfig에서 의존관계를 클라이언트 코드에 주입하므로 클라이언트 코드는 변경하지 않아도댐
    * 즉 소프트웨어 요소를 새롭게 확장해도 사용영역의 변경은 닫혀있다.
  

* ### IoC, DI 그리고 컨테이너
  * ### 제어의 역전 IoC(Inversion of Control)
  * 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IoC)이라 한다.
  * 클라이언트 구현 객체가 스스로 필요한 구현객체를 생성하고 연결하고 실행 (프로그램의 흐름을 직접 제어) 
   -> 클라이언트 구현 객체는 자신의 로직을 실행하는 역할만 담당 제어 흐름은 AppConfig를 통해(프로그램 흐름을 외부에서 관리)
  - > 프레임워크 vs 라이브러리
    >  - 프레임워크가 내가 직성한 코드를 제어하고, 대신 실행하면 그것은 프레임워크가 맞다.( JUnit)
    >  - 반면 내가 작성한 코드가 직접 제어의 흐름을 담당한다면 그건은 프레임워크가 아니라 라이브러리다.

  * ### 의존관계 주입DI (Dependency Injection)
    * 의존관계는 정적인 클래스 의존 관계와, 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계  이둘은 분리해서 생각해야한다.
    
      - 정적인 클래스 의존관계
        - 클래스가 사용하는 import 코드만 보고 의존관계를 쉽게 판단할 수 있다. 정적인 의존관계는 어플리케이션을 실행하지 않아도 분석할 수 있다. 
      - 동적인 객체 인스턴스 의존 고나계
        - 어플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존관계
        - 어플리케이션 실행시점(런타임)에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해 클라이언트와 서버의 실제의존관계가 연결되는 것을 의존관계 주입이라 한다.
        - 객체 인스턴스를 생성하고, 그 참조값을 전달해서 연결.
        - 의존관계 주입을 사용하면 클라이언트 코드를 변경하지 않고, 클라이언트가 호출하는 대상의 타입
          인스턴스를 변경할 수 있다.
        - 의존관계 주입을 사용하면 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를
          쉽게 변경할 수 있다
        
  * ### IoC컨테이너, DI컨테이너
    * AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 IoC 컨테이너 또는 DI컨테이너라 한다.
    * 최근에는 주로 DI컨테이너라 한다 ( 어샘블러, 오브젝트 팩토리 ) 등으로 불리기도함. 



* ### 스프링으로 전환
  * 스프링은 모든것이 ApplicationContext 라는것으로 시작 스프링 컨테이너
  * 기존에는 개발자가 AppConfig를 사용해 직접 객체를 생성하고 DI했지만 이젠 스프링 컨테이너를 통해 사용
  * 스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용한다. 여기서 @Bean
    이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다. 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다.
  * 스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다. ( memberService ,orderService )
  * 이전에는 개발자가 필요한 객체를 AppConfig 를 사용해서 직접 조회했지만, 이제부터는 스프링
    컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 한다. 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있다.
  * 기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제부터는 스프링 컨테이너에 객체를 스프링 빈으로 등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다


## 2022/ 06/ 10

  > // 스프링 컨테이너 생성
  > 
  > ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
  > 
- ApplicationContext를 스프링 컨테이너라 한다.
- ApplicationContext는 인터페이스다.
- 스프링 컨테이너는 XML을 기반으로 만들 수 있고, 애노테이션 기반의 자바 설정 클래스로 만들 수 있다.
- 직전에 AppConfig 를 사용했던 방식이 애노테이션 기반의 자바 설정 클래스로 스프링 컨테이너를 만든
  것이다. 
- 자바 설정 클래스를 기반으로 스프링 컨테이너( ApplicationContext )를 만들어보자.
  new AnnotationConfigApplicationContext(AppConfig.class);
  이 클래스는 ApplicationContext 인터페이스의 구현체이다.

![image](https://user-images.githubusercontent.com/60100532/173075957-a2400f23-d006-47bc-9cc7-67c67b762d0d.png)

 - 빈 이름은 메서드 이름을 사용한다.
 - 빈 이름을 직접 부여할 수 도 있다. 
  > @Bean(name="memberService") 

![image](https://user-images.githubusercontent.com/60100532/173077215-ec7ab7ff-73fc-4a01-a913-82a47ef050ce.png)

* ### 스프링 빈 조회

![image](https://user-images.githubusercontent.com/60100532/173085563-783ba8af-c246-4b69-83d5-70d57ae09610.png)

* ### BeanFactory & ApplicationContext
![image](https://user-images.githubusercontent.com/60100532/173089993-69c8024a-762b-4dca-9094-cd46ac7d4672.png)

* ### BeanFactory
 - 스프링 컨테이너의 최상위 인터페이스다.
 - 스프링 빈을 관리하고 조회하는 역할을 담당한다.

* ### ApplicationContext
 - BeanFactory 기능을 모두 상속받아서 제공한다.
 - 빈을 관리하고 검색하는 기능을 BeanFactory가 제공해주는데, 그러면 둘의 차이가 뭘까?
 - 애플리케이션을 개발할 때는 빈을 관리하고 조회하는 기능은 물론이고, 수 많은 부가기능이 필요하다. 

![image](https://user-images.githubusercontent.com/60100532/173090635-f1232847-9374-497e-972d-d3d977f22a77.png)

 - ApplicationContext는 BeanFactory의 기능을 상속받는다.
 - ApplicationContext는 빈 관리기능 + 편리한 부가 기능을 제공한다.
 - BeanFactory를 직접 사용할 일은 거의 없다. 부가기능이 포함된 ApplicationContext를 사용한다.
 - BeanFactory나 ApplicationContext를 스프링 컨테이너라 한다


* ### 다양한 설정 형식 지원 -자바코드, xml
![image](https://user-images.githubusercontent.com/60100532/173091631-5d98b1a4-76ba-4230-89ec-1f8cf0d7e32e.png)



