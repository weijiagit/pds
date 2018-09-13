package com.fykj.kernel._c.bean;

import java.util.List;
import java.util.Map;

import com.fykj.kernel._c.model.SessionUser;
import com.fykj.util.JJSON;

public class _T_ {

	public static class B{
		private String bName;
		
		private List<String> str;
		
		private Map<String, SessionUser> map;

		public String getbName() {
			return bName;
		}

		public void setbName(String bName) {
			this.bName = bName;
		}

		public List<String> getStr() {
			return str;
		}

		public void setStr(List<String> str) {
			this.str = str;
		}

		public Map<String, SessionUser> getMap() {
			return map;
		}

		public void setMap(Map<String, SessionUser> map) {
			this.map = map;
		}
		
		
	}

	
	public static class A{
		
		private String aName;

		private B b;
		
		private B[] barray;
		
		private List<B> bs;
		
		private List<String> st;

		private Map<String, SessionUser> map;

		public Map<String, SessionUser> getMap() {
			return map;
		}

		public void setMap(Map<String, SessionUser> map) {
			this.map = map;
		}

		public String getaName() {
			return aName;
		}

		public void setaName(String aName) {
			this.aName = aName;
		}

		public B getB() {
			return b;
		}

		public void setB(B b) {
			this.b = b;
		}

		public List<B> getBs() {
			return bs;
		}

		public void setBs(List<B> bs) {
			this.bs = bs;
		}

		public List<String> getSt() {
			return st;
		}

		public void setSt(List<String> st) {
			this.st = st;
		}

		public B[] getBarray() {
			return barray;
		}

		public void setBarray(B[] barray) {
			this.barray = barray;
		}
		
		

	}
	
	
	public static void main(String[] args) throws Exception {
		
//		SessionUser sessionUser=new RandomObjectBinder<SessionUser>().createObject(SessionUser.class);
//		
//		System.out.println(sessionUser);
		
//		A a=new RandomObjectBinder<A>().createObject(A.class);
//		System.out.println(JJSON.get().formatObject(a));
		
		A[] as=new RandomObjectBinder<A[]>().createObject(A[].class);
		System.out.println(JJSON.get().formatObject(as));
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
