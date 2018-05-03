package boot.demo.util.filed;

import java.io.*;
import java.time.LocalDate;
import java.util.UUID;

public class ReportResponse {



    public static String importFile(String fileUrl) {

        String imgPath = "";
        InputStream inputStream = null;
        File file = new File(fileUrl);
        String name = file.getName();
        try {
            inputStream = new FileInputStream(file);
            byte[] data = readInputStream(inputStream);
            imgPath = "D:/file/file";
            File  imageFile = new File(imgPath);
            if (!imageFile.exists() && !imageFile.isDirectory()) {
                imageFile.mkdirs();
            }
            FileOutputStream outStream = new FileOutputStream(imageFile+"\\"+ LocalDate.now()+ UUID.randomUUID()+name);
            outStream.write(data);
            //关闭输出流
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgPath;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
    public static void main(String[] args) throws Exception {
        ReportResponse.importFile("D:\\港宇资产负债.xlsx");
    }
}