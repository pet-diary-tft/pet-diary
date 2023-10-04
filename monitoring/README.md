## Grafana + Prometheus

스프링을 Grafana와 Prometheus에 연동하여 각종 이벤트 및 리소스 모니터링을 하는 간단한 예제입니다.

### 준비 작업

- 스프링 부트 의존성 추가
  ```groovy
  implementation 'org.springframework.boot:spring-boot-starter-actuator'
  implementation 'io.micrometer:micrometer-registry-prometheus'
  ```
- properties 설정 추가

  ```yml
  management:
    endpoint:
      prometheus:
        enabled: true
  endpoints:
    web:
      exposure:
        include: prometheus,info,health
  prometheus:
    metrics:
      export:
        enabled: true
  ```

- grafana, prometheus의 docker 컨테이너 생성

  scrape_configs.static_configs.targets를 프로젝트 환경에 맞게 수정해줘야합니다. docker에서 prometheus를 실행할 때 네트워크 설정에 따라 `127.0.0.1` 주소가 docker 컨테이너 내부로의 루프백 주소로 해석될 수 있기 때문입니다. `host.docker.internal`를 사용하여 Docker 외부의 호스트 시스템을 가리킬 수 있습니다. 이 방법은 Mac, Windows에서 작동하며 리눅스에서는 지원하지않으니 참고해주세요.

  `metrics_path`는 스프링 부트 애플리케이션에서 Micrometer와 Actuator를 사용했을 경우 `/actuator/prometheus`가 기본값입니다. 메트릭을 스크랩하기 위한 엔드포인트입니다.

  ```shell
  docker-compose up -d
  ```

  localhost:3000으로 접속하면 grafana 화면이 뜹니다. 최초 로그인 정보는 **admin/admin** 입니다. **Connections -> Data Sources**에서 **Prometheus**를 클릭하고 **Prometheus server URL**을 `http://prometheus:9090`로 설정해줍니다. 설정 후 **Save & test**를 클릭해 `Successfully queried the Prometheus API.` 메시지가 나오면 잘 연동된 것입니다.

  localhost:9090으로 접속하면 prometheus 화면이 뜹니다. **Status -> Targets**에서 State를 확인하여 애플리케이션과 잘 연결되었는지 확인할 수 있습니다.

- 원하는 정보 로깅하는 법 예제

  ```java
  package com.petdiary.core.components;

  import io.micrometer.core.instrument.Metrics;
  import io.micrometer.core.instrument.Timer;
  import org.aspectj.lang.ProceedingJoinPoint;
  import org.aspectj.lang.annotation.Around;
  import org.aspectj.lang.annotation.Aspect;
  import org.springframework.stereotype.Component;

  @Aspect
  @Component
  public class MethodPerformanceAspect {
      @Around("execution(* com.petdiary..*.*(..))")
      public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
          Timer.Sample sample = Timer.start(Metrics.globalRegistry);
          Object proceed = joinPoint.proceed();
          Timer timer = Metrics.timer("method.execution.time", "class", joinPoint.getSignature().getDeclaringTypeName(), "method", joinPoint.getSignature().getName());
          sample.stop(timer);

          return proceed;
      }
  }
  ```

  위 AOP 코드는 method.execution.time 메트릭명으로 class, method 속성을 추가하고 메서드가 실행된 시간을 로깅합니다. 이제 서버를 실행하면 grafana metric에서 `method_execution_time_seconds_max` 등의 로그 정보를 확인할 수 있습니다.
