# Practice3 - Quản lý phương tiện giao thông (Java OOP)

## Cấu trúc
Tất cả file `.java` nằm phẳng trong thư mục `src/`, không khai báo `package`,
nên có thể copy các file vào cùng một thư mục bất kỳ và biên dịch/chạy ngay.

- `Manufacturer.java` - enum hãng sản xuất (Honda/Yamaha/Toyota/Suzuki)
- `VehicleOwner.java` - chủ phương tiện
- `Vehicle.java` - lớp cha abstract của phương tiện
- `Car.java`, `Motorcycle.java`, `Truck.java` - kế thừa `Vehicle`
- `ManufacturerStatistic.java` - lớp hỗ trợ thống kê theo hãng
- `VehicleManagement.java` - quản lý `ArrayList<Vehicle>`
- `InputHelper.java` - đọc và kiểm tra dữ liệu nhập từ bàn phím
- `Main.java` - menu console + dữ liệu mẫu

## Biên dịch và chạy bằng dòng lệnh

Các file nguồn được lưu ở UTF-8 và có chữ tiếng Việt, nên cần yêu cầu
`javac`/`java` dùng UTF-8, nếu không phần chữ có dấu sẽ hiển thị sai (lỗi
hiển thị của console, không phải lỗi chương trình).

Trên Windows (PowerShell hoặc cmd):

```
chcp 65001
cd src
javac -encoding UTF-8 -d ../bin *.java
java -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp ../bin Main
```

Trên Linux/macOS (thường mặc định đã là UTF-8):

```
cd src
javac -encoding UTF-8 -d ../bin *.java
java -cp ../bin Main
```

## Chạy trong VS Code

Mở thư mục `practice3` bằng VS Code (đã cài Extension Pack for Java), mở
`Main.java` rồi bấm "Run". File `.vscode/settings.json` đã cấu hình sẵn
`sourcePaths`, `outputPath` và tham số UTF-8 cho console debug.
