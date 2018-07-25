package boot.demo.entity.bo;

import com.alibaba.fastjson.annotation.JSONField;
public class BeforeResponse {
    private String sendDate;
    private String message;
    @JSONField(name = "checkListState")
    private String ticketCheckState;

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTicketCheckState() {
        return ticketCheckState;
    }

    public void setTicketCheckState(String ticketCheckState) {
        this.ticketCheckState = ticketCheckState;
    }
}
