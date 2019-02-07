package com.sample.scraping.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value("${kafka.brokers}")
  private String brokers;
  @Value("${kafka.session.timeout.ms}")
  private int sessionTimeout;
  @Value("${kafka.poll.records}")
  private int pollRecords;
  @Value("${kafka.fetch.min.bytes}")
  private int minBytes;
  @Value("${kafka.fetch.max.bytes}")
  private int maxBytes;
  @Value("${kafka.fetch.maxwait}")
  private int maxWait;
  @Value("${kafka.partition.fetch.bytes}")
  private int partitionFetchBytes;
  @Value("${kafka.listener.concurrency}")
  private int concurrency;
  @Value("${kafka.group.id}")
  private String groupId;

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());

    if (concurrency > 1) {
      factory.setConcurrency(concurrency);
    }
    factory.setBatchListener(true);
    return factory;
  }

  @Bean
  public ConsumerFactory<String, byte[]> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, pollRecords);
    props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, minBytes);
    props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, maxBytes);
    props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, maxWait);
    props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, partitionFetchBytes);

    return props;
  }

}
