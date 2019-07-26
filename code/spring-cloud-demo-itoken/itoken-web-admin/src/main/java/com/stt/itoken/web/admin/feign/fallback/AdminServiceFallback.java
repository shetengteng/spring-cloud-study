package com.stt.itoken.web.admin.feign.fallback;

import com.stt.itoken.common.dto.BaseResult;
import com.stt.itoken.common.hystrix.Fallback;
import com.stt.itoken.web.admin.feign.AdminFeignService;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/15.
 */
// 注意必须要加入到容器中，使用Component注解
@Component
public class AdminServiceFallback implements AdminFeignService {

}
