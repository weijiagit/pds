package com.fykj._b._core.departGovernment.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Created by weijia on 2017/12/21.
 *
 */
public class DepartGovernmentAddInVO implements JInputModel {

	/**
	 *部门ID
	 */
	private String departId;

	/**
	 *图片路径
	 */
	private String imagePath;

	/**
	 * 行政审批单位
	 */
	private String imageText;

	/**
	 *是否显示
	 */
	private String showPic;

	/**
	 *显示顺序
	 */
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
