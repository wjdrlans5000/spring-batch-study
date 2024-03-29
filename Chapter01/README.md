# 1장 스프링 배치

## 배치 어플리케이션이란
- 배치(batch) 는 일괄처리 라는 뜻을 갖고있다.
- 대용량 데이터를 한번에 처리하고 해당 결과를 저장하거나 사용하기 위한 어플리케이션
- 대용량 데이터를 매번 API 호출을 통해 처리한다면 그만큼 많은 I/O를 차지하게 되고 서버에 부하를 유발할수 있으므로 배치 어플리케이션을 사용

## 스프링 배치 강점
- `자동화` : 매번 단순반복작업을 쉽고 빠르게 자동화시켜줍니다.
- `대용량 처리` : 그것이 대용량이라 할지라도 가장 최적화된 성능을 보장합니다.
- `견고성` : 예측하지 못한 상황이나 동작에 대한 예외처리도 정의할 수 있습니다.
- `재사용성` : 공통적인 작업을 단위별로 재사용할 수 있습니다.

## 스프링 배치처리시 고려사항
- 단순하게
- 안전하게
    - 정의한 단위수만큼 데이터를 불러와서 처리하며
      처리해야 하는 데이터에 대한 예외적인 상황이 일어나지 않도록 데이터의 무결성이 보장되야 한다.
- 가볍게
    - 입력(Input)과 출력(Output)의 사용을 최소화하기 위해 최대한 적은 횟수로 데이터를 가져와서 -> 처리하고 -> 저장한다.
- 스프링 배치는 스케쥴러가 아니기때문에 별도 스케쥴링,이력관리 필요

## EnableBatchProcessing
- 스프링 배치가 제공하는 어노테이션ㅇ느로 배치 인프라스트럭쳐를 부트스트랩하는데 사용된다.

`인프라스트럭쳐 구성 컴포넌트`
- JobRepository : 실행중인 잡의 상태를 기록하는데 사용
- JobLauncher : 잡을 구동하는데 사용
- JobExplorer : JobRepository 사용해 읽기 전용 작업을 수행하는데 사용
- JobRegistry : 특정한 런쳐 구현체 사용시 잡을 찾는 용도
- PlatformTransactionManager : 잡 진행 과정에서 트랜잭션을 다루는데 사용
- JobBuilderFactory : 잡을 생성하는 빌더
- StepBuilderFactory : 스탭을 생성하는 빌더

## Tasklet(기능)
- Step을 구성하는 하나의 기능
- 스탭에서 Tasklet 과 Chunk(ItemReader,ItemProcessor,ItemWriter) 중 하나를 선택하여 사용
- 가볍고 몇줄되지 않는 작업이며, writer 가 불필요한 경우  **Tasklet** 으로 처리
- 알림메세지 전송과 같은 간단한 배치작업의 경우 **Tasklet** 을 구현한다.
- Tasklet 은 **RepeatStatus** 를 통해 작업의 완료여부를 반환할 수 있다.
  - FINISHED : 작업 완료
  - CONTINUABLE : 지속되어야 하는 상태

> 만약 CONTINUABLE 을 지속적으로 반환한다면 해당 스탭은 무한수행될 수 있음에 유의

## 잡 실행
- JobLauncher 에 의해 실행된 로그를 확인할 수 있다.
- **JobLauncherApplicationRunner** 라는 컴포넌트가 있고, 이는 스프링 배치 실행시 로드되며 JobLauncher 를 통해 모든 잡을 실행한다.

