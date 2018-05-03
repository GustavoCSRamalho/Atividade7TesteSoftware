package xml.cidadePrevisao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement(name = "previsao")
@XmlAccessorType(XmlAccessType.FIELD)
public class Previsao {
//    @XmlElement(name = "id")
    public Integer id;
    @XmlElement(name = "dia")
    public Date dia;
    @XmlElement(name = "tempo")
    public String tempo;
    @XmlElement(name = "minima")
    public String minima;
    @XmlElement(name = "maxima")
    public String maxima;
    @XmlElement(name = "iuv")
    public String iuv;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getNome() {
        return dia;
    }

    public void setNome(Date nome) {
        this.dia = nome;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getMinima() {
        return minima;
    }

    public void setMinima(String minima) {
        this.minima = minima;
    }

    public String getMaxima() {
        return maxima;
    }

    public void setMaxima(String maxima) {
        this.maxima = maxima;
    }

    public String getIuv() {
        return iuv;
    }

    public void setIuv(String iuv) {
        this.iuv = iuv;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }
}
