package com.sample.scraping.web;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ConfigController {

  private final Semaphore stopper;
  private final ReentrantLock lock;

  /**
   * 잠금 장치를 설정합니다.
   *
   * @return http status 200
   * @throws InterruptedException 예외
   */
  @GetMapping(path = "/stop")
  public synchronized ResponseEntity<Void> stop() throws InterruptedException {
    stopper.acquire();
    log.info("-----------------> 종료 되었습니다");
    return ResponseEntity.ok().build();
  }

  /**
   * 잠금 장치를 해제 합니다.
   *
   * @return http status 200
   * @throws InterruptedException 예외
   */
  @GetMapping(path = "/release")
  public synchronized ResponseEntity<Void> release() {
    stopper.release();
    return ResponseEntity.ok().build();
  }

  @GetMapping(path = "/recovery")
  public void recover() {
    stopper.drainPermits();
    stopper.release(1);
    log.info("회복");
  }
}
