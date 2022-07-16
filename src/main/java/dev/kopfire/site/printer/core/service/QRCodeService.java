package dev.kopfire.site.printer.core.service;

public interface QRCodeService {

    String decodeQR(byte[] qrCodeBytes);

}