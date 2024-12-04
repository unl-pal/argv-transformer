/**
 * 
 */
package com.github.gm.hotconf.test.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.gm.hotconf.annotations.HotConfigurableProperty;
import com.github.gm.hotconf.annotations.HotConfigurationHookAfter;
import com.github.gm.hotconf.annotations.HotConfigurationHookBefore;

/**
 * @author Gwendal Mousset
 *
 */
@Service
public class TestingServiceImpl implements TestingService {

	@HotConfigurableProperty
	@Value("${int.min}")
	private int intPrimProp;
	
	@HotConfigurableProperty
	@Value("${int.max}")
	private Integer integerProp;
	
	@HotConfigurableProperty("long.primitive")
	private long longPrimProp;
	
	@HotConfigurableProperty("long.obj")
	private Long longProp;
	
	@HotConfigurableProperty("double.primitive")
	private double doublePrimProp;
	
	@HotConfigurableProperty("double.obj")
	private Double doublePrim;
	
	@HotConfigurableProperty("float.primitive")
	private float floatPrimProp;
	
	@HotConfigurableProperty("float.obj")
	private Float floatProp;
	
	@HotConfigurableProperty
	private String stringProp;

	private List<String> hooksProof = new ArrayList<>();
	

}
