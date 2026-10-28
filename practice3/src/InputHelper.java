import java.time.Year;
import java.util.Scanner;

/**
 * InputHelper gom tất cả các phương thức đọc dữ liệu từ bàn phím và kiểm
 * tra hợp lệ (validate) ngay tại bước nhập. Mỗi phương thức lặp lại việc
 * hỏi người dùng cho đến khi nhập đúng định dạng, để chương trình không
 * bao giờ bị crash vì người dùng gõ sai.
 *
 * Lưu ý quan trọng về Scanner: ta luôn đọc dữ liệu bằng scanner.nextLine()
 * (đọc nguyên một dòng dưới dạng String), sau đó mới tự chuyển đổi kiểu
 * bằng Integer.parseInt()/Double.parseDouble(). Cách này tránh được lỗi
 * kinh điển khi trộn lẫn nextInt() với nextLine() (nextInt() không đọc
 * ký tự xuống dòng, khiến lần nextLine() kế tiếp bị "nhảy" mất một bước).
 */
public class InputHelper {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    /** Đọc một chuỗi không được để trống. */
    public static String readNonEmptyString(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Dữ liệu không được để trống, vui lòng nhập lại.");
        }
    }

    /**
     * Đọc mã số phương tiện: phải đúng 5 ký tự, không trùng với phương tiện
     * đã có trong danh sách (so sánh không phân biệt hoa/thường).
     */
    public static String readVehicleNumber(Scanner scanner, VehicleManagement management) {
        while (true) {
            System.out.print("Nhập mã số phương tiện (đúng 5 ký tự): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Mã số phương tiện không được để trống.");
                continue;
            }
            if (input.length() != 5) {
                System.out.println("Mã số phương tiện phải có đúng 5 ký tự.");
                continue;
            }
            if (management.isVehicleNumberExists(input)) {
                System.out.println("Mã số \"" + input + "\" đã tồn tại, vui lòng nhập mã khác.");
                continue;
            }
            return input;
        }
    }

    /** Đọc hãng sản xuất, chỉ chấp nhận Honda/Yamaha/Toyota/Suzuki. */
    public static Manufacturer readManufacturer(Scanner scanner) {
        while (true) {
            System.out.print("Nhập hãng sản xuất (Honda/Yamaha/Toyota/Suzuki): ");
            String input = scanner.nextLine();
            Manufacturer manufacturer = Manufacturer.fromString(input);
            if (manufacturer != null) {
                return manufacturer;
            }
            System.out.println("Hãng sản xuất không hợp lệ. Chỉ chấp nhận: "
                    + "Honda, Yamaha, Toyota, Suzuki.");
        }
    }

    /** Đọc năm sản xuất: phải lớn hơn 2000 và không vượt quá năm hiện tại. */
    public static int readManufactureYear(Scanner scanner) {
        int currentYear = Year.now().getValue();
        while (true) {
            System.out.print("Nhập năm sản xuất (2001 - " + currentYear + "): ");
            String input = scanner.nextLine().trim();
            try {
                int year = Integer.parseInt(input);
                if (year > 2000 && year <= currentYear) {
                    return year;
                }
                System.out.println("Năm sản xuất phải lớn hơn 2000 và không vượt quá "
                        + currentYear + ".");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    /** Đọc một số nguyên phải lớn hơn 0 (dùng cho số chỗ ngồi ...). */
    public static int readPositiveInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Giá trị phải lớn hơn 0.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    /** Đọc một số thực phải lớn hơn 0 (dùng cho dung tích, trọng tải ...). */
    public static double readPositiveDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Giá trị phải lớn hơn 0.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số thực hợp lệ.");
            }
        }
    }

    /** Đọc CCCD: đúng 12 chữ số. Dùng regex \\d{12} để kiểm tra. */
    public static String readCitizenId(Scanner scanner) {
        while (true) {
            System.out.print("Nhập CCCD (đúng 12 chữ số): ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{12}")) {
                return input;
            }
            System.out.println("CCCD không hợp lệ, phải gồm đúng 12 chữ số.");
        }
    }

    /** Đọc email và kiểm tra đúng định dạng bằng regex đơn giản. */
    public static String readEmail(Scanner scanner) {
        while (true) {
            System.out.print("Nhập email: ");
            String input = scanner.nextLine().trim();
            if (input.matches(EMAIL_REGEX)) {
                return input;
            }
            System.out.println("Email không đúng định dạng, ví dụ hợp lệ: user@gmail.com");
        }
    }

    /**
     * Đọc thông tin chủ phương tiện. Nếu CCCD đã tồn tại trong hệ thống:
     * - Họ tên và email khớp với dữ liệu cũ -> trả về CHÍNH đối tượng
     *   VehicleOwner cũ (không tạo đối tượng mới) để phương tiện mới cùng
     *   trỏ về một chủ xe duy nhất.
     * - Họ tên hoặc email không khớp -> báo lỗi và trả về null; nơi gọi
     *   (Main) phải hủy thao tác thêm phương tiện.
     */
    public static VehicleOwner readOwner(Scanner scanner, VehicleManagement management) {
        String citizenId = readCitizenId(scanner);
        String fullName = readNonEmptyString(scanner, "Nhập họ tên chủ xe: ");
        String email = readEmail(scanner);

        VehicleOwner existingOwner = management.findOwnerByCitizenId(citizenId);
        if (existingOwner == null) {
            return new VehicleOwner(citizenId, fullName, email);
        }

        boolean sameFullName = existingOwner.getFullName().equals(fullName);
        boolean sameEmail = existingOwner.getEmail().equals(email);
        if (sameFullName && sameEmail) {
            System.out.println("CCCD đã tồn tại, sử dụng lại thông tin chủ xe cũ: "
                    + existingOwner);
            return existingOwner;
        }

        System.out.println("Lỗi: CCCD " + citizenId
                + " đã tồn tại nhưng họ tên/email không khớp với dữ liệu cũ ("
                + existingOwner + "). Dữ liệu chủ xe không nhất quán.");
        return null;
    }
}
