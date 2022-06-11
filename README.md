
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


## 2022/ 06/ 11

* ### 스프링 빈 설정 메타 정보 -BeanDefinition
  * 스프링은 어떻게 이런 다양한 설정 형식을 지원하는 것일까? 그 중심에는 BeanDefinition이라는 추상화가 있다.
  * 쉽게 이야기해서 역할과 구현을 개념적으로 나눈 것 이다.
  * 스프링 컨테이너는 자바 코드인지, XML인지 몰라도 된다. 오직 BeanDefinition만 알면 된다.

  * BeanDefinition을 빈 설정 메타정보라 한다. @Bean당 각각 하나씩 메타 정보 생성됨.
  * 스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성.

![image](https://user-images.githubusercontent.com/60100532/173167142-b1a9083a-089b-4bfd-81b9-ede6fed073b2.png)

![image](https://user-images.githubusercontent.com/60100532/173169291-51915052-2133-4415-863d-1d6c6350e053.png)

![image](https://user-images.githubusercontent.com/60100532/173169325-e11eb2e5-b0ec-4304-89cb-3d63e3ae0e38.png)

BeanDefinition에 대해서는 너무 깊이있게 이해하기 보다는, 스프링이 다양한 형태의 설정 정보를
BeanDefinition으로 추상화해서 사용하는 것 정도만 이해하면 된다.
가끔 스프링 코드나 스프링 관련 오픈 소스의 코드를 볼 때, BeanDefinition 이라는 것이 보일 때가 있다.
이때 이러한 메커니즘을 떠올리면 된다.

> ### - 스프링은 BeanDefinition으로 스프링빈의 설정 메타정보를 추상화한다.
> ### - 스프링빈을 만들때는 2가지 방법이 있는데 
> 1. 직접 스프링 빈을 등록 ( xml )
> 2. FactoryBean이라는것을 이용하여 빈을 등록 ( 자바 config를 사용하는것이 이에 해당 )


* ## 웹 어플리케이션과 싱글톤

![image](https://user-images.githubusercontent.com/60100532/173170355-f8c102b0-1f5c-4a48-9b28-327e0d319d9a.png)

- 우리가 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로
생성한다.
- 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! 메모리 낭비가 심하다.
- 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. 싱글톤 패턴

* ### 싱글톤 패턴
  * 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴이다.
  * 그래서 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야한다.
    * private 생성자를 사용해서 외부에서 임의로 new키워드를 사용하지 못하도록 막아야 한다.

![image](https://user-images.githubusercontent.com/60100532/173170573-5f0b43d9-1784-45e8-973d-17ab0503ace3.png)

- Static 영역에 객체 instance를 미리 하나 생성해서 올려둔다.
- static 영역에 올려놓은 객체 instance가 필요하면 오직 getInstance() 메서드를 통해서만 조회가능 
  - (이 메서드를 통해 항상 static에 올려놓은 같은 인스턴스를 반환)
- 딱 1개의 객체 instance만 존재해야 하므로, 생성자를 private로 막아서 혹시라도 외부에서  new 키워드로 객체 인스턴스가 생성되는것을 막는다.

> 싱글톤 패턴을 적용하면 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를
공유해서 효율적으로 사용할 수 있다. 하지만 싱글톤 패턴은 다음과 같은 수 많은 문제점들을 가지고 있다.

* ### 싱글톤 패턴 문제점
  - 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
  - 의존관계상 클라이언트가 구체 클래스에 의존한다. DIP를 위반한다.
  - 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
  - 테스트하기 어렵다.
  - 내부 속성을 변경하거나 초기화 하기 어렵다.
  - private 생성자로 자식 클래스를 만들기 어렵다.
  - 결론적으로 유연성이 떨어진다.
  - 안티패턴으로 불리기도 한다.

* ## 싱글톤 컨테이너
  - 스프링 컨테이너는 싱글톤 패턴의 문제점을 해결하면서, 객체 인스턴스를 싱글톤(1개만 생성)으로 관리한다.
  
* ### 싱글톤 컨테이너
  * 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다. (컨테이너는 객체를 하나만 생성해서 관리한다.)
  * 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라 한다.
  * 스프링 컨테이너의 이런 기능 덕분에 싱글턴 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수있다.
  * 싱글톤 패턴을 위한 지저분한 코드가 들어가지 않아도 된다.
  * DIP, OCP, 테스트, private 생성자로 부터 자유롭게 싱글톤을 사용할 수 있다.

![image](https://user-images.githubusercontent.com/60100532/173171405-c0fc9ed9-9224-4985-bf6c-26dbc1512e46.png)


* ### 싱글톤 방식의 주의점
  * 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안된다.
  * 무상태(stateless)로 설계해야 한다!
    * 특정 클라이언트에 의존적인 필드가 있으면 안된다.
    * 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다!
    * 가급적 읽기만 가능해야 한다.
    * 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
  * 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다!!!

![image](https://user-images.githubusercontent.com/60100532/173172227-044f9c91-f619-438d-bb67-a75d99d1be02.png)
  * ThreadA가 사용자A 코드를 호출하고 ThreadB가 사용자B 코드를 호출한다 가정하자.
  * StatefulService 의 price 필드는 공유되는 필드인데, 특정 클라이언트가 값을 변경한다.
  * 사용자A의 주문금액은 10000원이 되어야 하는데, 20000원이라는 결과가 나왔다.
  * 실무에서 이런 경우를 종종 보는데, 이로인해 정말 해결하기 어려운 큰 문제들이 터진다.(몇년에 한번씩 꼭 만난다.)
  * 진짜 공유필드는 조심해야 한다! 스프링 빈은 항상 무상태(stateless)로 설계하자.



  * ### @Configuration과 싱글톤
![image](https://user-images.githubusercontent.com/60100532/173172522-9257a9be-60e5-4403-b79a-674efa631ae3.png)
  - memberService 빈을 만드는 코드를 보면 memberRepository() 를 호출한다.
      - 이 메서드를 호출하면 new MemoryMemberRepository() 를 호출한다.
  - orderService 빈을 만드는 코드도 동일하게 memberRepository() 를 호출한다.
      - 이 메서드를 호출하면 new MemoryMemberRepository() 를 호출한다.
     

  - 결과적으로 각각 다른 2개의 MemoryMemberRepository 가 생성되면서 싱글톤이 깨지는 것 처럼 보인다.
      스프링 컨테이너는 이 문제를 어떻게 해결할까?

![image](https://user-images.githubusercontent.com/60100532/173173065-3242102d-c2aa-4707-b772-7a7cc35b6e21.png)
 - 확인해보면 memberRepository인스턴스는 모두 같은 인스턴스가 공유되어 사용된다.

* ### @Configuration과 바이트코드 조작의 마법
  > 스프링 컨테이너는 싱글톤 레지스트리다. 따라서 스프링 빈이 싱글톤이 되도록 보장해주어야 한다. 그런데
  스프링이 자바 코드까지 어떻게 하기는 어렵다. 저 자바 코드를 보면 분명 3번 호출되어야 하는 것이 맞다.
  그래서 스프링은 클래스의 바이트코드를 조작하는 라이브러리를 사용한다.
  모든 비밀은 @Configuration 을 적용한 AppConfig 에 있다.
  >

* 사실 AnnotationConfigApplicationContext 에 파라미터로 넘긴 값은 스프링 빈으로 등록된다. 그래서 AppConfig 도 스프링 빈이 된다.
* AppConfig 스프링 빈을 조회해서 클래스 정보를 출력해보자.

![image](https://user-images.githubusercontent.com/60100532/173173349-981372a4-2c8c-463b-8945-569acd7283bc.png)
* 순수한 클래스라면 다음과 같이 출력되어야 한다.
 > class hello.core.AppConfig

그런데 예상과는 다르게 클래스 명에 xxxCGLIB가 붙으면서 상당히 복잡해진 것을 볼 수 있다. 
이것은 내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 
AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다!

![image](https://user-images.githubusercontent.com/60100532/173173398-483e3e4f-15f4-449c-b739-b55ea73f5dc4.png)
> 참고 AppConfig@CGLIB는 AppConfig의 자식 타입이므로, AppConfig 타입으로 조회 할 수 있다.

![image](https://user-images.githubusercontent.com/60100532/173173445-fd17180b-1ae5-4f1f-8518-1fb119539646.png)
