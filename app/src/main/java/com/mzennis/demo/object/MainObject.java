package com.mzennis.demo.object;

import java.io.Serializable;

/**
 * Created by mzennis on 9/3/16.
 */
public class MainObject implements Serializable {

	public static final String SAMPLE_TAG = "sample";

	private final String name;
	private final String desc;

	public MainObject(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
}