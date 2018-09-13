package com.fykj.kernel._c;

import java.io.Closeable;
import java.io.IOException;

import com.fykj.kernel._c.model.SessionUser;


public class ServerSessionHolder implements Closeable {

	private final static ThreadLocal<ServerSession> THREAD_LOCAL=new ThreadLocal<ServerSession>();
	
	public static void setServerSession(ServerSession serverSession){
		THREAD_LOCAL.set(serverSession);
	}
	
	@Override
	public void close() throws IOException {
		THREAD_LOCAL.remove();
	}
	
	
	/**
	 * get the Server Session.
	 * @return
	 */
	public static ServerSession getServerSession(){
		return THREAD_LOCAL.get();
	}
	
	/**
	 * get current user information.
	 * @return
	 */
	public static SessionUser getSessionUser(){
		
		ServerSession serverSession=getServerSession();
		if(serverSession==null){
			return SessionUser.DEFUALT;
		}else{
			SessionUser sessionUser=serverSession.getSessionUser();
			return sessionUser==null?SessionUser.DEFUALT:sessionUser;
		}
		
	}
	
}
