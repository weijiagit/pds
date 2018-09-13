package com.fykj.kernel.redis;

public interface UidRepository <K,V> {

	/**
	 * 保存信息
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void store(K key,V value) throws Exception;
	
	/**
	 * 校验信息 userId 正确就成功
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public boolean exists(K key,V value) throws Exception;
	
	/**
	 * 获取用户信息
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 * @author zhangj
	 */
	public V get(K key) throws Exception;
	
	public boolean contains(K key) throws Exception;
	
	/**
	 * 删除
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean remove(K key) throws Exception;


}
