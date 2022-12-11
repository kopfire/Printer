package dev.kopfire.site.printer.core.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private final Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    @Override
    public String decodeQR(byte[] qrCodeBytes) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(qrCodeBytes);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
            BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
            BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
            MultiFormatReader multiFormatReader = new MultiFormatReader();
            Result result = multiFormatReader.decode(binaryBitmap);
            return result.getText();
        } catch (NotFoundException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<String> generateAndSaveQR(int first, int count) throws IOException, WriterException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String data = "АГУ_Картирдж_" + (first + i);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    data,
                    BarcodeFormat.QR_CODE, 150, 150);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "png", outputStream);

            String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());

            list.add(base64Image);
        }
        return list;
    }
}