/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hastane_otomasyonu;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author faruk
 */
public class mySQL<T> {

	//herhagi bir query çalıştırır
	ResultSet runQuery(String query) {
		String using_password = "App";
		String user_name = "App";
		String url = "jdbc:derby://localhost:1527/Hastane";
		Connection connection = null;
		Statement statement = null;
		ResultSet result_set = null;
		try {
			connection = DriverManager.getConnection(url, user_name, using_password);

			statement = connection.createStatement();

			result_set = statement.executeQuery(query);

		} catch (Exception ex) {
			System.out.println("QUERY CALISTIRILAMADI");
			System.out.println("EXCEPTION IS : " + ex.toString());
		}

		return result_set;
	}

	//data base e herhangi bir obje ekler
	void addToDatabase(T object) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		if (object != null) {
			em.persist(object);

		} else {
			System.out.println("DATABASE E EKLEME YAPILAMADI");
		}
		em.getTransaction().commit();
		em.close();
		emf.close();

	}

	//randevunu tarihini formatlar
	String getFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//YYYY/MM/DD yapıldıgında cıktı istendigi gibi gelmiyor.
		String formatDate = sdf.format(date);
		return formatDate;
	}

	//herhangi bir objeyi database den kaldırır
	void remove(T data) {
		String using_password = "App";
		String user_name = "App";
		String url = "jdbc:derby://localhost:1527/Hastane";
		Connection connection = null;
		Statement statement = null;

		try {
			connection = DriverManager.getConnection(url, user_name, using_password);

			statement = connection.createStatement();
			String delete_q = null;
			if (data instanceof Randevu) {
				delete_q = "DELETE FROM RANDEVU WHERE RANDEVU_KODU='" + ((Randevu) data).getRandevuKodu() + "'";
			} else if (data instanceof Doktor) {
				delete_q = "DELETE FROM DOKTOR WHERE SIFRE='" + ((Doktor) data).getSifre() + "'";
				System.out.println("İSTİFA GERÇEKLEŞMİŞTİR(DOKTOR");
			} else if (data instanceof Hasta) {
				int num = (int)((Hasta) data).getTc();
				delete_q = "DELETE FROM HASTA WHERE TC=" +num+ "";
				//delete_q = "DELETE FROM HASTA WHERE CAST(TC AS INTEGER)='"+((Hasta) data).getTc()+ "'";
				System.out.println("HASTA ÇIKTI");
			} else {
				System.out.println("QUERY DOKTORU VEYA RANDEVUYU SİLEMEDİ");
				return;
			}
			//silme işlemini gerçekleştir.
			statement.executeUpdate(delete_q);

		} catch (Exception ex) {
			System.out.println("QUERY CALISTIRILAMADI");
			System.out.println("EXCEPTION IS : " + ex.toString());
		}
	}

	void updateSifre(T data, String eskiSifre, String yeniSifre) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em = emf.createEntityManager();
		//Doktorun sifresini degistir
		if (data instanceof Doktor) {
			Query doktor_q = em.createQuery("UPDATE Doktor d set d.sifre=:pSifre where d.sifre=:pEskiSifre");
			doktor_q.setParameter("pSifre", yeniSifre);
			doktor_q.setParameter("pEskiSifre", eskiSifre);
			em.getTransaction().begin();
			doktor_q.executeUpdate();// kaç tane kayıdın etkilendiğini döndürür.
			em.getTransaction().commit();
			em.close();
			emf.close();
		} //doktorun sahip olduğu randevulardaki bilgiyide değiştir.
		else if (data instanceof Randevu) {
			Query doktor_q = em.createQuery("UPDATE Randevu r set r.doktorSifre=:pSifre where r.doktorSifre=:pEskiSifre");
			doktor_q.setParameter("pSifre", yeniSifre);
			doktor_q.setParameter("pEskiSifre", eskiSifre);
			em.getTransaction().begin();
			int count = doktor_q.executeUpdate();// kaç tane kayıdın etkilendiğini döndürür.
			System.out.println(count + " tane randevunun doktor sifresi güncellenmiştir.");
			em.getTransaction().commit();
			em.close();
			emf.close();
		}
		System.out.println(data.getClass());
	}

	//randevunun kodunu güncellemek için yapılmıştır
	void updateRandevuKod(String eskiKod, String yeniKod) {
		System.out.println("UPDATE KOD BAŞLANGIÇ");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hastane_Otomasyonu_DatabasePU");
		EntityManager em = emf.createEntityManager();
		//Randevunun kodunu updatele
		Query q = em.createQuery("UPDATE Randevu r set r.randevuKodu=:pyeniKod where r.randevuKodu=:peskiKod");
		q.setParameter("peskiKod", eskiKod);
		q.setParameter("pyeniKod", yeniKod);
		System.out.println("DEĞİŞİM BAŞLADI ....");
		em.getTransaction().begin();
		int randevuDeğişimi = q.executeUpdate();
		System.out.println("" + randevuDeğişimi + " adet randevunun kodu güncellenmiştir");
		em.getTransaction().commit();
		System.out.println("DEĞİŞİM BİTTİ");
		em.close();
		emf.close();
		System.out.println("Randevu kodu güncellendi.....");
	}

}
