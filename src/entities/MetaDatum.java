
package entities;

import java.io.Serializable;
import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaDatum implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    
    private final static long serialVersionUID = -7388876899300532240L;
    
    public MetaDatum(Integer id, String key, String value) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
	}
    
    public MetaDatum(Integer id, String key, HashMap<String,String> meta) {
		super();
		this.id = id;
		this.key = key;
		this.value = meta.toString();
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

}
