
package entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Refund {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("refund")
    @Expose
    private String refund;
    @SerializedName("total")
    @Expose
    private String total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
