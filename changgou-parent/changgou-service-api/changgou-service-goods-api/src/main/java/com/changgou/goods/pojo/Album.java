package com.changgou.goods.pojo;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Album构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_album")
public class Album implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

	private Long id;//编号


    @Column(name = "title")
	private String title;//相册名称
    @Column(name = "image")
	private String image;//相册封面
    @Column(name = "image_items")
	private String imageItems;//图片列表


	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getTitle() {
		return title;
	}

	//set方法
	public void setTitle(String title) {
		this.title = title;
	}
	//get方法
	public String getImage() {
		return image;
	}

	//set方法
	public void setImage(String image) {
		this.image = image;
	}
	//get方法
	public String getImageItems() {
		return imageItems;
	}

	//set方法
	public void setImageItems(String imageItems) {
		this.imageItems = imageItems;
	}


}
