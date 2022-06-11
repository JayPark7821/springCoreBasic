package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 , url = " + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect(){
        System.out.println("connect: " + url);
    }

    // 서비스 시작히 호출
    public void call(String message){
        System.out.println("call : " + url + " message : " + message);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close " + url);
    }


    public void init() {
        // 의존관계 주입이 끝나면 호출됨
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }


    public void close(){
        // 빈이 소멸되기 전에 호출
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
