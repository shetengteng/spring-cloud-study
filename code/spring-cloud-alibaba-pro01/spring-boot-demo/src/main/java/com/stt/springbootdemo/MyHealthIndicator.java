package com.stt.springbootdemo;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("myHealth")
public class MyHealthIndicator implements HealthIndicator {
	@Override
	public Health health() {

		int code = 0;
		if(code != 0){
			return Health.down().withDetail("code",code).withDetail("version",1.9).build();
		}

		return Health.up().withDetail("code",code).withDetail("version",1.2).build();
	}
}
