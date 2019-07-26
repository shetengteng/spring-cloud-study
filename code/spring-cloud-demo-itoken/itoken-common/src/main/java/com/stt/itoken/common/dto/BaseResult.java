package com.stt.itoken.common.dto;

import com.google.common.collect.Lists;
import com.stt.itoken.common.constants.HttpStatusConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Administrator on 2019/7/14.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult {

	// ok / not_ok
	private String result;
	private Object data;
	private String success;
	private Cursor cursor;
	private List<Error> errors;

	public static BaseResult createResult(String result,Object data,String success,List<Error> errors){
		return BaseResult.builder()
				.result(result)
				.data(data)
				.success(success)
				.errors(errors)
				.build();

	}

	private static BaseResult createResult(String result, Object data, String success, Cursor cursor, List<Error> errors) {
		BaseResult baseResult = new BaseResult();
		baseResult.setResult(result);
		baseResult.setData(data);
		baseResult.setSuccess(success);
		baseResult.setCursor(cursor);
		baseResult.setErrors(errors);

		return baseResult;
	}

	public static BaseResult ok(Object data){
		return BaseResult.createResult("ok",data,"成功操作",null,null);
	}

	public static BaseResult ok(String success){
		return BaseResult.createResult("ok",null,success,null,null);
	}

	public static BaseResult page(Object data,Cursor cursor){
		return BaseResult.builder()
				.result("ok")
				.data(data)
				.success("成功操作")
				.errors(null)
				.cursor(cursor)
				.build();
	}

	public static BaseResult ok(){
		return BaseResult.ok(null);
	}

	public static BaseResult notOk(List<Error> errors){
		return BaseResult.createResult("not_ok",null,"",null,errors);
	}

	public static BaseResult notOk(Error error){
		return BaseResult.notOk(Lists.newArrayList(error));

	}


	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Cursor {
		private int offset;
		private int limit;
		private long total;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Error {
		private String field;
		private String message;

		public static Error fromHttpStatus(HttpStatusConstant s){
			return new Error(s.getStatus()+"",s.getContent());
		}

	}

}
