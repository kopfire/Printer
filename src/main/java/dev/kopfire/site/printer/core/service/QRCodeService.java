package dev.kopfire.site.printer.core.service;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

public interface QRCodeService {

    String decodeQR(byte[] qrCodeBytes);

    List<String> generateAndSaveQR(int first, int count) throws IOException, WriterException;


}