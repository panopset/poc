/**
 * 
 */
package com.panopset.poc.fvsv;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Karl Dinwiddie
 *
 */
public class TestScriptEngines {
  
  private final String EXPECTED = "foo bar";
  private final Map<String, Object> map = new HashMap<String, Object>();
  
  @Before
  public void init() {
    map.put("v", "bar");
  }

  @Test
  public void test() {
    Assert.assertEquals(EXPECTED, Util.rez2velocity("/test.txt", map));
    Assert.assertEquals(EXPECTED, Util.rez2flywheel("/test.txt", map));
  }

}
