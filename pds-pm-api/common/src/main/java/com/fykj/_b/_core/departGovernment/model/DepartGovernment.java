package com.fykj._b._core.departGovernment.model;

import com.fykj.kernel._c.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by weijia on 2017/12/21.
 *
 */
@Entity
@Table(name = "t_depart_government")
public class DepartGovernment extends AbstractEntity {

	@Column(name = "depart_id")
	private String departId;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "image_text")
	private String imageText;

	@Column(name = "show_pic")
	private String showPic;
	
	@Column(name = "sequence")
	private Integer sequence;

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageText() {
		return imageText;
	}

	public void setImageText(String imageText) {
		this.imageText = imageText;
	}

	public String getShowPic() {
		return showPic;
	}

	public void setShowPic(String showPic) {
		this.showPic = showPic;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
