package com.stt.itoken.web.posts.feign.fallback;

import com.stt.itoken.common.hystrix.Fallback;
import com.stt.itoken.web.posts.feign.PostsFeignService;
import org.springframework.stereotype.Component;


@Component
public class PostsServiceFallback implements PostsFeignService {

	@Override
	public String page(int pageNum, int pageSize, String postsPostJson) {
		return Fallback.badGatewayJson();
	}

	@Override
	public String get(String postGuid) {
		return Fallback.badGatewayJson();
	}

	@Override
	public String save(String tbPostsPostJson, String optsBy) {
		return Fallback.badGatewayJson();
	}
}
