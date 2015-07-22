package org.sparta.springwebutils.entity;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Carlos Eduardo Endler Genz – Sparta Java Team
 * 
 *         History: Mar 4, 2014 - Carlos Eduardo Endler Genz
 * 
 */
public class EntryPoint {
	private Set<String> urls;
	private Class<?> type;
	private String methodName;
	private String methodDecorationName;
	private Set<RequestMethod> requestMethods;
	private List<EntryPointParameter> parameters;
	private Class<?> responseType;

	/**
	 * Getter method for type
	 * 
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Setter method for type
	 * 
	 * @param type the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * Getter method for methodName
	 * 
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Setter method for methodName
	 * 
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Getter method for requestMethods
	 * 
	 * @return the requestMethods
	 */
	public Set<RequestMethod> getRequestMethods() {
		return requestMethods;
	}

	/**
	 * Setter method for requestMethods
	 * 
	 * @param requestMethods the requestMethods to set
	 */
	public void setRequestMethods(Set<RequestMethod> requestMethods) {
		this.requestMethods = requestMethods;
	}

	/**
	 * Getter method for parameters
	 * 
	 * @return the parameters
	 */
	public List<EntryPointParameter> getParameters() {
		return parameters;
	}

	/**
	 * Setter method for parameters
	 * 
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<EntryPointParameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Getter method for urls
	 * @return the urls
	 */
	public Set<String> getUrls() {
		return urls;
	}

	/**
	 * Setter method for urls
	 * @param urls the urls to set
	 */
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

    /**
     * Getter method for methodDecorationName
     * @return the methodDecorationName
     */
    public String getMethodDecorationName() {
        return methodDecorationName;
    }

    /**
     * Setter method for methodDecorationName
     * @param methodDecorationName the methodDecorationName to set
     */
    public void setMethodDecorationName(String methodDecorationName) {
        this.methodDecorationName = methodDecorationName;
    }
    
    /**
     * Getter method for responseType
     * @return the responseType
     */
    public Class<?> getResponseType() {
        return responseType;
    }

    /**
     * Setter method for responseType
     * @param responseType the responseType to set
     */
    public void setResponseType(Class<?> responseType) {
        this.responseType = responseType;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
