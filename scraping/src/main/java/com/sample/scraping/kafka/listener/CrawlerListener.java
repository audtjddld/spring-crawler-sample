package com.sample.scraping.kafka.listener;

import static com.sample.common.costant.Props.CRAWLER_TOPIC;

import com.sample.common.message.ScrapingMessage;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlerListener {

  private final Semaphore semaphore;

  @KafkaListener(topics = CRAWLER_TOPIC, containerFactory = "kafkaListenerContainerFactory")
  public void listen(List<ScrapingMessage> messages) throws Exception {
    log.info("{}", semaphore);
    semaphore.acquire();
    //TODO domain을 이용하여 해당 사이트 크롤링 정보를 가져온다. (품절 버튼 위치, 이미지 위치) 이때 database 또는 zookeeper를 이용할지 고려해본다.
    for (ScrapingMessage message : messages) {
      log.info("received message : {}", message);
      // TODO scrap 처리.
      TimeUnit.SECONDS.sleep(1);

    }
    semaphore.release();
  }

}
