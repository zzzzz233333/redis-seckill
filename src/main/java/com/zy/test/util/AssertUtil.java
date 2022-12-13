package com.zy.test.util;

import java.util.Collections;
import org.springframework.util.Assert;

/**
 * @author {庄勇}
 * @since 2022/12/13 11:35
 */
public class AssertUtil {

  /**
  * Assert 翻译为中文为“断言”，使用过 JUnit 的读者都熟知这个概念，
   它断定某一个实际的运行值和预期想一样，否则就抛出异常。
  * Spring 对方法入参的检测借用了这个概念，其提供的 Assert 类拥有众多按规则对方法入参进行断言的方法，
   * 可以满足大部分方法入参检测的要求。
  * 这些断言方法在入参不满足要求时就会抛出 IllegalArgumentException。
  * 下面，我们来认识一下 Assert 类中的常用断言方法：
  */

  //断言方法说明
   /*
   当 object 为 null 时抛出异常，
      notNull(Object object)
      notNull(Object object, String message) //方法允许您通过 message 定制异常信息。
    和notNull() 方法断言规则相反的方法是,它要求入参一定是 null；
      isNull(Object object)
      isNull(Object object, String message)
    当 expression 不为 true 抛出异常；
      isTrue(boolean expression)
      isTrue(boolean expression, String message)
    当集合未包含元素时抛出异常。
      notEmpty(Collection collection)
      notEmpty(Collection collection, String message)
    分别对 Map 和 Object[] 类型的入参进行判断；
      notEmpty(Map map)
      notEmpty(Map map, String message)
      notEmpty(Object[] array, String message)
      notEmpty(Object[] array, String message)
   当 text 为 null 或长度为 0 时抛出异常；
      hasLength(String text)
      hasLength(String text, String message)
    text 不能为 null 且必须至少包含一个非空格的字符，否则抛出异常；
      hasText(String text)
      hasText(String text, String message)
    如果 obj 不能被正确造型为 clazz 指定的类将抛出异常；
      isInstanceOf(Class clazz, Object obj)
      isInstanceOf(Class type, Object obj, String message)
    subType 必须可以按类型匹配于 superType，否则将抛出异常；
     isAssignable(Class superType, Class subType)
     isAssignable(Class superType, Class subType, String message)
      */
  public static void isTrue(boolean object,String message){
    Assert.isTrue(object,message);
  }

  public static  void mustLogin(String object){
    Assert.isTrue(object !=null ,"必须登录");
  }

  public static void isNotEmpty(String ids){
    Assert.notEmpty(Collections.singleton(ids.getBytes()), "数据不能为空");
  }
}
