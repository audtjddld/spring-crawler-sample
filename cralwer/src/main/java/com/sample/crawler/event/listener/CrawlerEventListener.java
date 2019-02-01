package com.sample.crawler.event.listener;

import com.sample.crawler.event.CrawlerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CrawlerEventListener {

  @EventListener
  public void crawlerStart(CrawlerEvent event) {
    // TODO pipe 아이디에 대한 정규식 및 대상 위치 정보 얻어옴.
    // 변경되는 영역으로 찾은 곳은 품절 체크를 위한 버튼 영역과, 상품 이미지 영역을 추출하는 곳이다.

  }
}
