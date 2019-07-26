package com.stt.itoken.service.posts.controller;

import com.github.pagehelper.PageInfo;
import com.stt.itoken.common.domain.TbPostsPost;
import com.stt.itoken.common.dto.BaseResult;
import com.stt.itoken.common.dto.BaseResult.Cursor;
import com.stt.itoken.common.utils.MapperUtils;
import com.stt.itoken.service.posts.service.PostsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/v1/posts")
public class PostsController {

	@Autowired
	private PostsService postsService;

	@GetMapping("/{postGuid}")
	public BaseResult get(@PathVariable("postGuid") String postGuid){

		TbPostsPost t = new TbPostsPost();
		t.setPostGuid(postGuid);
		return BaseResult.ok(postsService.selectOne(t));
	}

	/**
	 * feign调用支持对象序列化传参，但是有防火墙会失败，因此传递json字符串跨过防火墙
	 * @param postsPostJson
	 * @param optsBy
	 * @return
	 */
	@PostMapping
	public BaseResult save(
			@RequestParam(required = true) String postsPostJson,
	        @RequestParam(required = true) String optsBy
	){
		int result = 0;
		TbPostsPost postsPost = null;
		try {
			postsPost = MapperUtils.json2pojo(postsPostJson,TbPostsPost.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!Objects.isNull(postsPost)){
			if(StringUtils.isEmpty(postsPost.getPostGuid())){
				// 表示新增
				result = postsService.insert(postsPost,optsBy);
			}else{
				// 表示编辑
				result = postsService.update(postsPost,optsBy);
			}
			if(result > 0){
				return BaseResult.ok("保存文章成功");
			}
		}
		return BaseResult.ok("保存文章失败");
	}


	@ApiOperation(value = "内容分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "pageSize", value = "笔数", required = true, dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "tbPostsPostJson", value = "内容对象 JSON 字符串", dataTypeClass = String.class, paramType = "json")
	})

	@GetMapping("/page/{pageNum}/{pageSize}")
	public BaseResult page(
			@PathVariable(required = true) int pageNum,
			@PathVariable(required = true) int pageSize,
			@RequestParam(required = false,value="tbPostsPostJson")String postsPostJson){

		TbPostsPost postsPost = null;
		if(!StringUtils.isEmpty(postsPostJson)){
			try {
				postsPost = MapperUtils.json2pojo(postsPostJson,TbPostsPost.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		PageInfo<TbPostsPost> page = postsService.page(pageNum, pageSize, postsPost);

		return BaseResult.page(
				page.getList(),
				Cursor.builder()
						.limit(page.getPageSize())
						.offset(page.getPageNum())
						.total(page.getTotal())
						.build());
	}
}