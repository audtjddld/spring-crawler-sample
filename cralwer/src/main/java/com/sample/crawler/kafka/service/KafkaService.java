package com.sample.crawler.kafka.service;

import com.sample.common.message.ScrapingMessage;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * kafka service는 stateless 하기 때문에 static으로 처리해도 문제가 없을 거라 생각함.
 */
@Slf4j
@Service
public class KafkaService {

  private final KafkaTemplate<String, byte[]> kafkaTemplate;

  public static KafkaService kafkaService;

  @Autowired
  public KafkaService(KafkaTemplate<String, byte[]> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(ScrapingMessage scrapingMessage) {
    log.info("received message : {}", scrapingMessage);
  }

  @PostConstruct
  public void init() {
    this.kafkaService = this;
  }
}
