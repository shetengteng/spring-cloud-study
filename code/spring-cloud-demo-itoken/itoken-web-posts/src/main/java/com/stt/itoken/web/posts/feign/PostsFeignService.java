package com.stt.itoken.web.posts.feign;

import com.stt.common.web.service.BaseClientService;
import com.stt.itoken.web.posts.feign.fallback.PostsServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "itoken-service-posts",fallback = PostsServiceFallback.class)
public interface PostsFeignService extends BaseClientService{

	@Override
	@GetMapping("/v1/posts/page/{pageNum}/{pageSize}")
	public String page(
			@PathVariable(required = true,value = "pageNum") int pageNum,
			@PathVariable(required = true,value = "pageSize") int pageSize,
			@RequestParam(required = false ,value = "tbPostsPostJson")String postsPostJson);

	@RequestMapping(value = "v1/posts/{postGuid}", method = RequestMethod.GET)
	public String get(
			@PathVariable(required = true, value = "postGuid") String postGuid
	);

	@RequestMapping(value = "v1/posts", method = RequestMethod.POST)
	public String save(
			@RequestParam(required = true, value = "tbPostsPostJson") String tbPostsPostJson,
			@RequestParam(required = true, value = "optsBy") String optsBy
	);
}
