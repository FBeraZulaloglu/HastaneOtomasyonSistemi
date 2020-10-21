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
@Table(name = "HASTA")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Hasta.findAll", query = "SELECT h FROM Hasta h")
	, @NamedQuery(name = "Hasta.findByAd", query = "SELECT h FROM Hasta h WHERE h.ad = :ad")
	, @NamedQuery(name = "Hasta.findBySoyad", query = "SELECT h FROM Hasta h WHERE h.soyad = :soyad")
	, @NamedQuery(name = "Hasta.findByTc", query = "SELECT h FROM Hasta h WHERE h.tc = :tc")
	, @NamedQuery(name = "Hasta.findByYas", query = "SELECT h FROM Hasta h WHERE h.yas = :yas")
	, @NamedQuery(name = "Hasta.findByCinsiyet", query = "SELECT h FROM Hasta h WHERE h.cinsiyet = :cinsiyet")})
public class Hasta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "AD")
	private String ad;
	@Column(name = "SOYAD")
	private String soyad;
	@Id
    @Basic(optional = false)
    @Column(name = "TC")
	private Integer tc;
	@Column(name = "YAS")
	private Integer yas;
	@Column(name = "CINSIYET")
	private String cinsiyet;

	public Hasta() {
	}

	public Hasta(Integer tc) {
		this.tc = tc;
	}

	public String getAd() {
		return ad;
	}

	public Hasta(String ad, String soyad, Integer tc, Integer yas, String cinsiyet) {
		this.ad = ad;
		this.soyad = soyad;
		this.tc = tc;
		this.yas = yas;
		this.cinsiyet = cinsiyet;
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

	public Integer getTc() {
		return tc;
	}

	public void setTc(Integer tc) {
		this.tc = tc;
	}

	public Integer getYas() {
		return yas;
	}

	public void setYas(Integer yas) {
		this.yas = yas;
	}

	public String getCinsiyet() {
		return cinsiyet;
	}

	public void setCinsiyet(String cinsiyet) {
		this.cinsiyet = cinsiyet;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tc != null ? tc.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Hasta)) {
			return false;
		}
		Hasta other = (Hasta) object;
		if ((this.tc == null && other.tc != null) || (this.tc != null && !this.tc.equals(other.tc))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "hastane_otomasyonu.Hasta[ tc=" + tc + " ]";
	}
	
}
