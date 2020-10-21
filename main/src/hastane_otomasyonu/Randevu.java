/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hastane_otomasyonu;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author faruk
 */
@Entity
@Table(name = "RANDEVU")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Randevu.findAll", query = "SELECT r FROM Randevu r")
	, @NamedQuery(name = "Randevu.findByHastaTc", query = "SELECT r FROM Randevu r WHERE r.hastaTc = :hastaTc")
	, @NamedQuery(name = "Randevu.findByDoktorSiFre", query = "SELECT r FROM Randevu r WHERE r.doktorSiFre = :doktorSiFre")
	, @NamedQuery(name = "Randevu.findByRandevuTarih", query = "SELECT r FROM Randevu r WHERE r.randevuTarih = :randevuTarih")
	, @NamedQuery(name = "Randevu.findByRandevuKodu", query = "SELECT r FROM Randevu r WHERE r.randevuKodu = :randevuKodu")})
public class Randevu implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "HASTA_TC")
	private Integer hastaTc;
	@Column(name = "DOKTOR_S\u0130FRE")
	private String doktorSiFre;
	@Column(name = "RANDEVU_TARIH")
    @Temporal(TemporalType.DATE)
	private Date randevuTarih;
	@Id
    @Basic(optional = false)
    @Column(name = "RANDEVU_KODU")
	private String randevuKodu;

	public Randevu() {
	}

	public Randevu(String randevuKodu) {
		this.randevuKodu = randevuKodu;
	}

	public Integer getHastaTc() {
		return hastaTc;
	}

	public void setHastaTc(Integer hastaTc) {
		this.hastaTc = hastaTc;
	}

	public String getDoktorSiFre() {
		return doktorSiFre;
	}

	public void setDoktorSiFre(String doktorSiFre) {
		this.doktorSiFre = doktorSiFre;
	}

	public Date getRandevuTarih() {
		return randevuTarih;
	}

	public void setRandevuTarih(Date randevuTarih) {
		this.randevuTarih = randevuTarih;
	}

	public String getRandevuKodu() {
		return randevuKodu;
	}

	public void setRandevuKodu(String randevuKodu) {
		this.randevuKodu = randevuKodu;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (randevuKodu != null ? randevuKodu.hashCode() : 0);
		return hash;
	}

	public Randevu(Integer hastaTc, String doktorSiFre, Date randevuTarih,String randevuKodu) {
		this.hastaTc = hastaTc;
		this.doktorSiFre = doktorSiFre;
		this.randevuTarih = randevuTarih;
		this.randevuKodu = randevuKodu;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Randevu)) {
			return false;
		}
		Randevu other = (Randevu) object;
		if ((this.randevuKodu == null && other.randevuKodu != null) || (this.randevuKodu != null && !this.randevuKodu.equals(other.randevuKodu))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "hastane_otomasyonu.Randevu[ randevuKodu=" + randevuKodu + " ]";
	}
	
}
