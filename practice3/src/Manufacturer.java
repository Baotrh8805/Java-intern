/**
 * Enum liệt kê các hãng sản xuất phương tiện được chấp nhận trong chương trình.
 * Dùng enum thay vì String giúp tránh lỗi gõ sai tên hãng và dễ mở rộng
 * danh sách hãng sau này.
 */
public enum Manufacturer {
    HONDA,
    YAMAHA,
    TOYOTA,
    SUZUKI;

    /**
     * Chuyển một chuỗi người dùng nhập (không phân biệt hoa/thường)
     * thành giá trị Manufacturer tương ứng.
     *
     * @param text chuỗi nhập vào, ví dụ "honda", "Honda", "HONDA"
     * @return giá trị Manufacturer nếu hợp lệ, hoặc null nếu không khớp hãng nào
     */
    public static Manufacturer fromString(String text) {
        if (text == null) {
            return null;
        }
        String normalized = text.trim().toUpperCase();
        for (Manufacturer manufacturer : Manufacturer.values()) {
            if (manufacturer.name().equals(normalized)) {
                return manufacturer;
            }
        }
        return null;
    }
}
