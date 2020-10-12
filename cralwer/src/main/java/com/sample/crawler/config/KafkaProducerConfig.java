package com.sample.crawler.config;

import com.sample.common.message.ScrapingMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.var;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.SenderOptions;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

  @Value("${kafka.brokers}")
  private String brokers;
  @Value("${kafka.produce.compression}")
  private String compression;

  @Bean
  public ReactiveKafkaProducerTemplate<String, ScrapingMessage> reactiveKafkaProducerTemplate() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10000);
    props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
    props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compression);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, SimplePartitioner.class);
    props.put(ProducerConfig.ACKS_CONFIG, "-1");
    SenderOptions<String, ScrapingMessage> stringScrapingMessageSenderOptions =
        SenderOptions.<String, ScrapingMessage>create(props);

    return new ReactiveKafkaProducerTemplate<>(stringScrapingMessageSenderOptions);
  }

  /**
   * kafka partitioning 전략
   */
  public static class SimplePartitioner extends DefaultPartitioner {

    private int num;

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
      List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
      return (num++ + 1) % partitions.size();
    }
  }

}
