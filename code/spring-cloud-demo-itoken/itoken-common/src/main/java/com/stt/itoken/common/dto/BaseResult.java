package com.stt.itoken.common.dto;

import com.stt.itoken.common.constants.HttpStatusConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/7/14.
 */
@Data
@Builder
public class BaseResult {

	// ok / not_ok
	private String result;
	private Object data;
	private String success;
	private List<Error> errors;

	public static BaseResult createResult(String result,Object data,String success,List<Error> errors){
		return BaseResult.builder()
				.result(result)
				.data(data)
				.success(success)
				.errors(errors)
				.build();
	}

	public static BaseResult ok(Object data){
		return BaseResult.createResult("ok",data,"成功操作",null);
	}

	public static BaseResult ok(){
		return BaseResult.ok(null);
	}

	public static BaseResult notOk(List<Error> errors){
		return BaseResult.createResult("not_ok",null,"",errors);
	}

	@Data
	@Builder
	@AllArgsConstructor
	public static class Cursor {
		private int total;
		private int offset;
		private int limit;
	}

	@Data
	@Builder
	@AllArgsConstructor
	public static class Error {
		private String field;
		private String message;

		public static Error fromHttpStatus(HttpStatusConstant s){
			return new Error(s.getStatus()+"",s.getContent());
		}

	}

}
