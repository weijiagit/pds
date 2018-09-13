package com.fykj._b._core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.kernel._c.async.Call;
import com.fykj.kernel._c.async.Catch;
import com.fykj.kernel._c.async.Task;
import com.fykj.kernel._c.async.TaskExecutor;
import com.fykj.kernel._c.model.InvokeResult;

@Controller
@RequestMapping("/_test")
public class _TestController {

	@Autowired
	private TaskExecutor executor;
	
	@RequestMapping(path="/promise",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult promise() {
		
		executor.promise(new Task() {
			@Override
			public void run() {
				System.out.println(".............");
				throw new RuntimeException("=======-========");
			}
		}).then(new Call<Object,Object>() {
			@Override
			public Object call(Object input) {
				System.out.println("input : " +input);
				return null;
			}
		}).cat(new Catch() {
			@Override
			public void cat(Throwable error) {
				error.printStackTrace();
			}
		});
		
		return InvokeResult.success(true);
	}
	
	
}
