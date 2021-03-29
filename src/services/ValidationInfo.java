package services;

public class ValidationInfo {
    private boolean valid[];
    private String errorMessage;

    public ValidationInfo(boolean valid[], String errorMessage){
        this.errorMessage = errorMessage;
        this.valid = valid.clone();
    }

    public boolean[] getValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
