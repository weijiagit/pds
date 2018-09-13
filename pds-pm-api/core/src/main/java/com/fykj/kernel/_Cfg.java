package com.fykj.kernel;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="cpp")
@Component
public class _Cfg {

	private JWT jwt;
	
	private Auth auth;
	
	private EhcacheCfg ehcache;
	
	private FileCfg file;

	private Department  department;

	private ExportCfg export;
	
	private RedisCfg redis;
	
	private MockCfg mock;
	
	private Sso sso;

	private Apr apr;

	private Oam oam;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public ExportCfg getExport() {
		return export;
	}

	public void setExport(ExportCfg export) {
		this.export = export;
	}

	public MockCfg getMock() {
		return mock;
	}

	public void setMock(MockCfg mock) {
		this.mock = mock;
	}

	public RedisCfg getRedis() {
		return redis;
	}

	public void setRedis(RedisCfg redis) {
		this.redis = redis;
	}

	public FileCfg getFile() {
		return file;
	}

	public void setFile(FileCfg file) {
		this.file = file;
	}

	public JWT getJwt() {
		return jwt;
	}

	public void setJwt(JWT jwt) {
		this.jwt = jwt;
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	
	
	public EhcacheCfg getEhcache() {
		return ehcache;
	}

	public void setEhcache(EhcacheCfg ehcache) {
		this.ehcache = ehcache;
	}

	
	
	public Sso getSso() {
		return sso;
	}

	public void setSso(Sso sso) {
		this.sso = sso;
	}

	public Apr getApr() {
		return apr;
	}

	public void setApr(Apr apr) {
		this.apr = apr;
	}

	public Oam getOam() {
		return oam;
	}

	public void setOam(Oam oam) {
		this.oam = oam;
	}

	public static class Auth{
		
		private boolean authentication;
		
		private boolean authorization;

		public boolean isAuthentication() {
			return authentication;
		}

		public void setAuthentication(boolean authentication) {
			this.authentication = authentication;
		}

		public boolean isAuthorization() {
			return authorization;
		}

		public void setAuthorization(boolean authorization) {
			this.authorization = authorization;
		}
		
		
	}
	
	public static class JWT{
		private String secret;
		
		private long expiration;
		
		private String header;
		
		public String getSecret() {
			return secret;
		}
		public void setSecret(String secret) {
			this.secret = secret;
		}
		public long getExpiration() {
			return expiration;
		}
		public void setExpiration(long expiration) {
			this.expiration = expiration;
		}
		public String getHeader() {
			return header;
		}
		public void setHeader(String header) {
			this.header = header;
		}
		
		
	}

	public static class EhcacheCfg{
		
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	public static class RedisCfg{
		
		private long expiredTime;

		private boolean redisOffProxy;
		
		private String header;
		
		private long sessionUserExpiredTime;


		public boolean isRedisOffProxy() {
			return redisOffProxy;
		}

		public void setRedisOffProxy(boolean redisOffProxy) {
			this.redisOffProxy = redisOffProxy;
		}
		
		public long getExpiredTime() {
			return expiredTime;
		}

		public void setExpiredTime(long expiredTime) {
			this.expiredTime = expiredTime;
		}

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public long getSessionUserExpiredTime() {
			return sessionUserExpiredTime;
		}

		public void setSessionUserExpiredTime(long sessionUserExpiredTime) {
			this.sessionUserExpiredTime = sessionUserExpiredTime;
		}
		
	}
	
	public static class FileCfg{
		
		private String dskPath;

		private String host;

		public String getDskPath() {
			return dskPath;
		}

		public void setDskPath(String dskPath) {
			this.dskPath = dskPath;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}
	}

	public static class Department{

		private String roleId;

		public String getRoleId() {
			return roleId;
		}

		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
	}

	public static class ExportCfg{
		private String dirPath;

		public String getDirPath() {
			return dirPath;
		}

		public void setDirPath(String dirPath) {
			this.dirPath = dirPath;
		}
	}
	
	public static class MockCfg{
		
		private boolean enable;
		
		private String headerKey;

		public boolean isEnable() {
			return enable;
		}

		public void setEnable(boolean enable) {
			this.enable = enable;
		}

		public String getHeaderKey() {
			return headerKey;
		}

		public void setHeaderKey(String headerKey) {
			this.headerKey = headerKey;
		}
		
		
	}
	
	public static class Sso{
		boolean enable;
		String host;
		String loginUrl;
		String loginOutUrl;
		String validTokenUrl;
	
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public boolean isEnable() {
			return enable;
		}
		public void setEnable(boolean enable) {
			this.enable = enable;
		}
		public String getLoginUrl() {
			return loginUrl;
		}
		public void setLoginUrl(String loginUrl) {
			this.loginUrl = loginUrl;
		}
		public String getLoginOutUrl() {
			return loginOutUrl;
		}
		public void setLoginOutUrl(String loginOutUrl) {
			this.loginOutUrl = loginOutUrl;
		}
		public String getValidTokenUrl() {
			return validTokenUrl;
		}
		public void setValidTokenUrl(String validTokenUrl) {
			this.validTokenUrl = validTokenUrl;
		}
	}

	public static class Apr{
		boolean enable;
		String host;
		String rejectUrl;
		String Authorization;

		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public boolean isEnable() {
			return enable;
		}
		public void setEnable(boolean enable) {
			this.enable = enable;
		}

		public String getRejectUrl() {
			return rejectUrl;
		}

		public void setRejectUrl(String rejectUrl) {
			this.rejectUrl = rejectUrl;
		}

		public String getAuthorization() {
			return Authorization;
		}

		public void setAuthorization(String authorization) {
			Authorization = authorization;
		}
	}

	public static class Oam{
		boolean enable;
		String url;
		String strkey;

		public boolean isEnable() {
			return enable;
		}

		public void setEnable(boolean enable) {
			this.enable = enable;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getStrkey() {
			return strkey;
		}

		public void setStrkey(String strkey) {
			this.strkey = strkey;
		}
	}
}
