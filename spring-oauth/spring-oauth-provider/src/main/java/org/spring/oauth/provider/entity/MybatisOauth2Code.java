package org.spring.oauth.provider.entity;
import java.io.Serializable;  
  
/**
 * 
 * ClassName: MybatisOauth2Code <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2017年6月22日 上午11:25:04 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
public class MybatisOauth2Code implements Serializable {  
    private static final long serialVersionUID = -1799776184263988216L;  
  
    private String code;  
    private byte[] authentication;  
  
    public String getCode() {  
        return code;  
    }  
  
    public void setCode(String code) {  
        this.code = code;  
    }  
  
    public byte[] getAuthentication() {  
        return authentication;  
    }  
  
    public void setAuthentication(byte[] authentication) {  
        this.authentication = authentication;  
    }  
  
}  