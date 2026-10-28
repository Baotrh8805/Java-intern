/**
 * Lớp VehicleOwner mô tả chủ sở hữu của phương tiện.
 * Áp dụng tính đóng gói (encapsulation): mọi thuộc tính đều là private,
 * chỉ được truy cập qua getter/setter, và setter luôn kiểm tra dữ liệu
 * hợp lệ trước khi gán giá trị.
 */
public class VehicleOwner {

    private String citizenId;
    private String fullName;
    private String email;

    // Regex email đơn giản, phù hợp với bài tập Java cơ bản: ten@ten-mien.duoi
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public VehicleOwner(String citizenId, String fullName, String email) {
        setCitizenId(citizenId);
        setFullName(fullName);
        setEmail(email);
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        // citizenId phải là String (không phải long) để giữ được số 0 ở đầu
        if (citizenId == null || !citizenId.matches("\\d{12}")) {
            throw new IllegalArgumentException(
                    "Căn cước công dân phải gồm đúng 12 chữ số");
        }
        this.citizenId = citizenId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Email không đúng định dạng");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "CCCD=" + citizenId + ", HoTen='" + fullName + "', Email='" + email + "'";
    }
}
