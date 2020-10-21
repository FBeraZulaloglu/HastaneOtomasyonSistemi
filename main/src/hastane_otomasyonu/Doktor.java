/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hastane_otomasyonu;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author faruk
 */
@Entity
@Table(name = "DOKTOR")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Doktor.findAll", query = "SELECT d FROM Doktor d")
	, @NamedQuery(name = "Doktor.findByAd", query = "SELECT d FROM Doktor d WHERE d.ad = :ad")
	, @NamedQuery(name = "Doktor.findBySoyad", query = "SELECT d FROM Doktor d WHERE d.soyad = :soyad")
	, @NamedQuery(name = "Doktor.findByUnvan", query = "SELECT d FROM Doktor d WHERE d.unvan = :unvan")
	, @NamedQuery(name = "Doktor.findByBolum", query = "SELECT d FROM Doktor d WHERE d.bolum = :bolum")
	, @NamedQuery(name = "Doktor.findBySifre", query = "SELECT d FROM Doktor d WHERE d.sifre = :sifre")})
public class Doktor implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "AD")
	private String ad;
	@Column(name = "SOYAD")
	private String soyad;
	@Column(name = "UNVAN")
	private String unvan;
	@Column(name = "BOLUM")
	private String bolum;
	@Id
    @Basic(optional = false)
    @Column(name = "SIFRE")
	private String sifre;

	public Doktor() {
	}

	public Doktor(String sifre) {
		this.sifre = sifre;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public String getUnvan() {
		return unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}

	public String getBolum() {
		return bolum;
	}

	public void setBolum(String bolum) {
		this.bolum = bolum;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (sifre != null ? sifre.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Doktor)) {
			return false;
		}
		Doktor other = (Doktor) object;
		if ((this.sifre == null && other.sifre != null) || (this.sifre != null && !this.sifre.equals(other.sifre))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "hastane_otomasyonu.Doktor[ sifre=" + sifre + " ]";
	}
	
}
