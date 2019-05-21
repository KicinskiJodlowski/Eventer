package com.example.eventer.model;

public class QrCodeModel {
    private String eventQRCode;

    public QrCodeModel(String qrCode) {
        this.eventQRCode = qrCode;
    }

    public String getQrCode() {
        return eventQRCode;
    }

    public void setQrCode(String qrCode) {
        this.eventQRCode = qrCode;
    }
}
