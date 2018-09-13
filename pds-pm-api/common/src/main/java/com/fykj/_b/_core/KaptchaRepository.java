package com.fykj._b._core;

import com.fykj._b._core.kaptcha.KaptchaVO;

/**
 * all generated kaptchas will be cached in the central repository.
 * @author JIAZJ
 *
 * @param <K>
 * @param <V>
 */
public interface KaptchaRepository<K,V> {

	/**
	 * store the kaptcha 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void store(K key,V value) throws Exception;
	
	/**
	 * check if the kaptcha exists 
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public boolean exists(K key,V value) throws Exception;
	
	/**
	 * remove the kaptcha
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean remove(K key) throws Exception;

	/**
	 * an unique key reference to the kapthca {@link KaptchaVO#setKey(String)}
	 * @author JIAZJ
	 * @see TextGen
	 */
	public interface KeyGen{
		public String key(KaptchaVO kaptchaVO ) throws Exception;
	}

	/**
	 * how to generate kapthca {@link KaptchaVO#setText(String)}
	 * @author JIAZJ
	 *
	 * @param <T>
	 */
	public interface TextGen<T>{
		public String text(T context) throws Exception;
	}
	
}
