package fiscalapi.icg.es.apifiscal.Utils;

public class WrongVoucher {
    private String voucherNumber;
    private String errorMessage;

    public WrongVoucher() {
        super();
    }

    public WrongVoucher(String voucherNumber, String errorMessage) {
        this.voucherNumber = voucherNumber;
        this.errorMessage = errorMessage;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
