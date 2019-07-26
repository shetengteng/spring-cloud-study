package com.stt.itoken.web.posts.feign;

import com.stt.itoken.web.posts.feign.fallback.AdminServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Administrator on 2019/7/15.
 */
@FeignClient(value = "itoken-service-admin",fallback = AdminServiceFallback.class)
public interface AdminFeignService {
}
