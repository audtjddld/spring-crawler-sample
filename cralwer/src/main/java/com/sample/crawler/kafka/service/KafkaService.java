package com.sample.crawler.kafka.service;

import static com.sample.common.costant.Props.CRAWLER_TOPIC;

import com.sample.common.message.ScrapingMessage;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.kafka.sender.SenderRecord;

/**
 * kafka service는 stateless 하기 때문에 static으로 처리해도 문제가 없을 거라 생각함.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

  private final ReactiveKafkaProducerTemplate<String, ScrapingMessage> kafkaTemplate;

  public static KafkaService kafkaService;


  public void sendMessage(ScrapingMessage scrapingMessage) {
    log.info("received message : {}", scrapingMessage);
    SenderRecord<String, ScrapingMessage, String> senderRecord = SenderRecord.create(
        new ProducerRecord<>(CRAWLER_TOPIC, scrapingMessage),
        scrapingMessage.getLink()
    );

    kafkaTemplate.send(senderRecord).subscribe();
  }

  @PostConstruct
  public void init() {
    kafkaService = this;
  }
}
