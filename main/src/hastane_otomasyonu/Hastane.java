/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hastane_otomasyonu;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Hastane<T> {

	private List<Doktor> doktor_list;
	private List<Hasta> hasta_list;
	private List<Randevu> randevu_list;

	public Hastane() {
		
		
		/*//DOKTOR LİSTESİ
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em = emf.createEntityManager();
		Query query_d = em.createQuery("select d FROM Doktor d");
		
		this.doktor_list = query_d.getResultList();

		for (Doktor d : doktor_list) {
		
			System.out.println(d.getAd() + " " + d.getSoyad() + " " + d.getBolum() + " " + d.getUnvan() + " " + d.getSifre());
		}
		em.close();
		emf.close();
		
		
		//HASTA LİSTESİ
		//EntityManagerFactory emf_1 = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em_hasta = emf.createEntityManager();
		Query query_h = em_hasta.createQuery("select h FROM Hasta h");
		
		this.hasta_list = query_h.getResultList();
		for (Doktor d : doktor_list) {
		
			System.out.println(d.getAd() + " " + d.getSoyad() + " " + d.getBolum() + " " + d.getUnvan() + " " + d.getSifre());
		}
		em_hasta.close();
		emf.close();
		
		
		//RANDEVU LİSTESİ START
		EntityManagerFactory emf_2 = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em_2 = emf_2.createEntityManager();
		Query query_r = em_2.createQuery("select r FROM Randevu r");
		
		this.randevu_list = query_r.getResultList();
		for (Doktor d : doktor_list) {
		
			System.out.println(d.getAd() + " " + d.getSoyad() + " " + d.getBolum() + " " + d.getUnvan() + " " + d.getSifre());
		}
		em_2.close();
		emf_2.close();
		//RANDEVU LİSTESİ STOP
		*/
		
	}
	
	List<T> getDoktorList(){
		//DOKTOR LİSTESİ
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em = emf.createEntityManager();
		Query query_d = em.createQuery("select d FROM Doktor d");
		
		this.doktor_list = query_d.getResultList();

		for (Doktor d : doktor_list) {
		
			System.out.println(d.getAd() + " " + d.getSoyad() + " " + d.getBolum() + " " + d.getUnvan() + " " + d.getSifre());
		}
		em.close();
		emf.close();
		
		return (List<T>)doktor_list;
	}
	
	List<T> getHastaList(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em = emf.createEntityManager();
		Query query_h = em.createQuery("select h FROM Hasta h");
		
		this.hasta_list = query_h.getResultList();
		
		for (Hasta h : hasta_list) {
			System.out.println(h.getAd()+" "+h.getSoyad());
		}
		
		em.close();
		emf.close();
		
		return (List<T>)hasta_list;
	}
	
	List<T> getRandevuList(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em = emf.createEntityManager();
		Query query_r = em.createQuery("select r FROM Randevu r");
		
		this.randevu_list = query_r.getResultList();
		
		em.close();
		emf.close();
		return (List<T>)randevu_list;
	}
}
