/**
 * 
 */
package com.fykj.kernel._c.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.fykj.util.JStringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 一种抽象实体类，提供ID和版本属性，以及基本的持久化方法
 * 
 * @author xiongp
 */
@MappedSuperclass
public abstract class AbstractEntity extends PropertyToStringAbstract implements JModel{

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
    @Column(name = "id", length = 36)
	@NotFound(action= NotFoundAction.IGNORE)
    private String id;

    @Version
    @Column(name = "version")
    private int version;
    
    /**
     * 0 为无效
     * 1 为有效
     */
    @Column(name = "is_available")
    private int isAvailable=1;
    
    @Column(name = "creator_id" , updatable = false, length = 36)
    private String creatorId;
    
    @Column(name = "create_date" , updatable = false)
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date createDate;
    
	@Transient
	private String createDateFormat;
    
    @Column(name = "modifier_id", length = 36)
    private String modifierId;
    
    @Column(name = "modify_date")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date modifyDate;
    
    @Transient
	private String modifyDateFormat;
//    @PreUpdate
//    public void preUpdate() {
//    	modifierId = CurrentUserHelper.getUserAccount();
//    	modifyDate = new Date();
//    }
//
//    @PrePersist
//    public void prePersist() {
//    	createDate = new Date();
//    	modifyDate = createDate;
//    	creatorId = CurrentUserHelper.getUserAccount();
//    	modifierId = creatorId;
//    	isAvailable = Availability.available;
//    }

    /**
     * 获得实体的标识
     *
     * @return 实体的标识
     */
    public String getId() {
    	if(JStringUtils.isNullOrEmpty(id)) {
    		return null;
    	}
    	
        return id;
    }

	/**
	 * @return the isAvailable
	 */
	public int getIsAvailable() {
		return isAvailable;
	}
	/**
	 * @return the isAvailable
	 */
	public int getIsAvailableInt() {
		return isAvailable;
	}
	
	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setIsAvailable(Availability isAvailable) {
		this.isAvailable = isAvailable.ordinal();
	}
	
	/**
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifierId
	 */
	public String getModifierId() {
		return modifierId;
	}

	/**
	 * @param modifierId the modifierId to set
	 */
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
     * 设置实体的标识
     *
     * @param id 要设置的实体标识
     */
    public void setId(String id) {
    	
    	if(JStringUtils.isNullOrEmpty(id)) {
    		id = null;
    	}
    	
        this.id = id;
    }

    /**
     * 获得实体的版本号。持久化框架以此实现乐观锁。
     *
     * @return 实体的版本号
     */
    public int getVersion() {
        return version;
    }

    /**
     * 设置实体的版本号。持久化框架以此实现乐观锁。
     *
     * @param version 要设置的版本号
     */
    public void setVersion(int version) {
        this.version = version;
    }

	public String getCreateDateFormat() {
		return createDateFormat;
	}

	public void setCreateDateFormat(String createDateFormat) {
		this.createDateFormat = createDateFormat;
	}

	/**
	 * @return the modifyDateFormat
	 */
	public String getModifyDateFormat() {
		return modifyDateFormat;
	}

	/**
	 * @param modifyDateFormat the modifyDateFormat to set
	 */
	public void setModifyDateFormat(String modifyDateFormat) {
		this.modifyDateFormat = modifyDateFormat;
	}

}
