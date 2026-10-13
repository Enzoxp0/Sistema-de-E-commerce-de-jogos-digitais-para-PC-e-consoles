package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class RequisitoSistema extends DefaultEntity {

    @Column(nullable = false, length = 100)
    private String so;

    @Column(nullable = false, length = 150)
    private String processador;

    @Column(nullable = false, length = 50)
    private String memoriaRam;

    @Column(nullable = false, length = 150)
    private String placaDeVideo;

    @Column(nullable = false, length = 80)
    private String armazenamento;

    public RequisitoSistema() {
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public String getPlacaDeVideo() {
        return placaDeVideo;
    }

    public void setPlacaDeVideo(String placaDeVideo) {
        this.placaDeVideo = placaDeVideo;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }
}
