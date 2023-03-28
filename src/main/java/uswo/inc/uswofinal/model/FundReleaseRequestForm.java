package uswo.inc.uswofinal.model;

public class FundReleaseRequestForm {
    private String approvalNumber;
    private String particulars;
    private String remarks;
    private Integer lcode;
    private Integer did;
    public String getApprovalNumber() {
        return approvalNumber;
    }
    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }
    public String getParticulars() {
        return particulars;
    }
    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }
    public Integer getLcode() {
        return lcode;
    }
    public void setLcode(Integer lcode) {
        this.lcode = lcode;
    }
    public Integer getDid() {
        return did;
    }
    public void setDid(Integer did) {
        this.did = did;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    
}
