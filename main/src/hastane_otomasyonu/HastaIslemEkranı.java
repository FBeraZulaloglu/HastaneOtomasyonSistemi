/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hastane_otomasyonu;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author faruk
 */
public class HastaIslemEkranı extends javax.swing.JFrame {

	/**
	 * Creates new form HastaIslemEkranı
	 */
	//Ekranlar
	Ana_Ekran anaEkran = new Ana_Ekran();
	Hastane hastane = anaEkran.hastane;
	//randevu tarih ve kodu
	LocalDate lastDate;
	LocalDate currentDate;
	String randevuKodu;
	//swing
	DefaultTableModel dtm = new DefaultTableModel();
	//objeler
	Doktor secilenDoktor;
	Hasta kulllaniciHasta;
	// obje listeleri
	List<Doktor> doktorList;
	List<Hasta> hastaList; 
	List<Randevu> randevuList;

	public HastaIslemEkranı() {
		initComponents();

	}

	public HastaIslemEkranı(Hasta kullanici) {
		initComponents();
		
		doktorTablo.setModel(dtm);
		
		currentDate = LocalDate.now();
		lastDate = LocalDate.now().plusMonths(3);
		
		doktorList = anaEkran.hastane.getDoktorList();
		hastaList = anaEkran.hastane.getHastaList();
		randevuList = anaEkran.hastane.getRandevuList();
		
		for (Hasta hasta : hastaList) {
			if (kullanici.getTc().equals(hasta.getTc())) {
				this.kulllaniciHasta = hasta;
			}
		}
		
		setColumns();
		doktorlariYukle();
		setDoktorBox();
		setBolumBox();

	}

	private void setDoktorBox() {
		String ad_soyad = "";
		for (Doktor doktor : doktorList) {
			System.out.println("Doktor Ad:" + doktor.getAd() + doktor.getSoyad());
			ad_soyad = doktor.getAd() + " " + doktor.getSoyad();
			doktor_box.addItem(ad_soyad);

		}
	}

	private boolean bolumKontrol(String bolumIsmi) {
		for (int i = 0; i < bolum_box.getItemCount(); i++) {
			if (bolumIsmi.toLowerCase().equals(bolum_box.getItemAt(i).toLowerCase())) {
				//System.out.println(bolumIsmi + " mevcut");
				return false;
			}
		}
		return true;
	}

	private void setBolumBox() {

		for (Doktor doktor : doktorList) {
			if (bolumKontrol(doktor.getBolum())) {
				bolum_box.addItem(doktor.getBolum());
			}
		}

	}

	private void setColumns() {
		dtm.addColumn("Doktor Ad ve Soyad");
		dtm.addColumn("Bolum");
		dtm.addColumn("Unvan");
	}

	private void doktorlariYukle() {

		for (Doktor d : doktorList) {
			dtm.addRow(new Object[]{d.getAd() + " " + d.getSoyad(), d.getBolum(), d.getUnvan(), ""});
		}
	}

	private void removeAllTable() {
		if (dtm.getRowCount() > 0) {
			for (int i = dtm.getRowCount() - 1; i > -1; i--) {
				dtm.removeRow(i);
			}
		}
	}

	//Doktorun hastanın alacağı randevu tarihinde bir randevusu olup olmadığına bakar
	private boolean isDoktorAvaliable(Doktor secilen_doktor) {
		for (Randevu randevu : randevuList) {
			if (secilen_doktor.getSifre().equals(randevu.getDoktorSiFre())) {
				System.out.println("RANDEVU TARİH :" + randevu.getRandevuTarih() + " ALINACAK RANDEVU :" + RandevuDate.getDate());
				if (getFormatDate(randevu.getRandevuTarih()).equals(getFormatDate(RandevuDate.getDate()))) {
					return false;
				}
			}
		}
		return true;
	}

	// Hastanın aynı tarihte randevusu olup olmadığına bakar.
	private boolean isHastaAvaliable() {
		for (Randevu randevu : randevuList) {
			if (kulllaniciHasta.getTc().equals(randevu.getHastaTc())) {
				if (getFormatDate(randevu.getRandevuTarih()).equals(getFormatDate(RandevuDate.getDate()))) {
					return false;
				}
			}
		}
		return true;
	}

	//Randevu alınacak tarihin belirli şartlara uyması beklenir.                 
	boolean isDateOk() {
		// Şu anki zamanı ve 3 ay sonraki zaman
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String cd = dtf.format(currentDate);
		String ld = dtf.format(this.lastDate);
		//Seçilen zaman 
		String d = getFormatDate(RandevuDate.getDate());
		// conditions
		if (getYear(d) != getYear(cd)) {
			JOptionPane.showMessageDialog(rootPane, "YIL ŞEÇİMİ YANLIŞ");
			System.out.println("YIL ŞEÇİMİ YANLIŞ");
			return false;
		} else if (getMonth(d) < getMonth(cd)) {
			JOptionPane.showMessageDialog(rootPane, "ESKİ ZAMANDA RANDEVU SEÇİLDİ(AY OLARAK)");
			return false;
		} else if (getDay(d) < getDay(cd) && (getMonth(d) == getMonth(cd))) {
			JOptionPane.showMessageDialog(rootPane, "ESKİ ZAMANDA RANDEVU SEÇİLDİ(GÜN OLARAK)");
			return false;
		} else if (getMonth(d) > getMonth(ld)) {
			JOptionPane.showMessageDialog(rootPane, "BU KADAR İLERİ TARİHTE BİR AY SEÇİLEMEZ");
			return false;
		}
		//conditions end
		return true;
	}

	int getYear(String date) {
		String[] year_month_day = date.split("/");
		int year = Integer.parseInt(year_month_day[0]);
		System.out.println("YEAR İS :" + year);
		return year;
	}

	int getMonth(String date) {
		String[] year_month_day = date.split("/");
		int month = Integer.parseInt(year_month_day[1]);
		System.out.println("MONTH İS :" + month);
		return month;
	}

	int getDay(String date) {
		String[] year_month_day = date.split("/");
		int day = Integer.parseInt(year_month_day[2]);
		System.out.println("DAY İS :" + day);
		return day;
	}

	String createCode() {
		String sonRandevuKodu = randevuList.get(randevuList.size() - 1).getRandevuKodu();
		String first = sonRandevuKodu.split("_")[1];
		int kod_first = Integer.parseInt(first);
		kod_first++;
		String createdRandevuKod = "r_" + kod_first;
		return createdRandevuKod;
	}

	//Randevu tarihinde gerekli bilgileri alabilmek için formatlı hale getirmek gerekir.
	String getFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//YYYY/MM/DD yapıldıgında cıktı istendigi gibi gelmiyor.
		String formatDate = sdf.format(date);
		return formatDate;
	}
	int getHastaRandevuCount(){
		int randevuSayac = 0;
		for (Randevu randevu : randevuList) {
			if(randevu.getHastaTc().equals(kulllaniciHasta.getTc())){
				randevuSayac++;
			}
		}
		return randevuSayac;
	}
	int doktorRandevuCount(Doktor doktor){
		int randevuSayac = 0;
		for (Randevu randevu : randevuList) {
			if(randevu.getHastaTc().equals(kulllaniciHasta.getTc())&&(randevu.getDoktorSiFre().equals(doktor.getSifre()))){
				randevuSayac++;
			}
		}
		return randevuSayac;
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        randevuAl = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bolum_box = new javax.swing.JComboBox<>();
        doktor_box = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        doktorTablo = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        hastaRandevuDefteri = new javax.swing.JButton();
        RandevuDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        randevuAl.setText("Randevu Al");
        randevuAl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randevuAlActionPerformed(evt);
            }
        });

        jLabel1.setText("Doktor Adı");

        jLabel2.setText("Bölüm");

        bolum_box.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bolumSelected(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bolumSec(evt);
            }
        });
        bolum_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bolum_boxActionPerformed(evt);
            }
        });

        doktor_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doktor_boxActionPerformed(evt);
            }
        });

        doktorTablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(doktorTablo);

        jButton2.setText("Ara");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("ANA EKRAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        hastaRandevuDefteri.setText("RANDEVULARIMI GÖSTER");
        hastaRandevuDefteri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hastaRandevuDefteriActionPerformed(evt);
            }
        });

        jLabel3.setText("RANDEVU TARİHİ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(doktor_box, 0, 83, Short.MAX_VALUE)
                                    .addComponent(bolum_box, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RandevuDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(randevuAl, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hastaRandevuDefteri, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bolum_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doktor_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RandevuDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(hastaRandevuDefteri, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(randevuAl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void randevuAlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randevuAlActionPerformed

		for (Doktor d : doktorList) {
			// EGER aynı unvanda ve adı soyadı aynı olan bir doktor varsa ?? 
			if ((d.getAd() + " " + d.getSoyad()).equals(doktor_box.getSelectedItem().toString())
					&& d.getBolum().equals(bolum_box.getSelectedItem().toString())) {

				secilenDoktor = d;
				if (RandevuDate.getDate() == null) {
					JOptionPane.showMessageDialog(rootPane, "Randevu Tarihi boş bırakılamaz.");
					return;
				}
				if (!isDoktorAvaliable(secilenDoktor)) {
					JOptionPane.showMessageDialog(rootPane, "Bu randevu tarihinde bu doktor uygun değildir!!");
					return;
				}
				if (!isHastaAvaliable()) {
					JOptionPane.showMessageDialog(rootPane, "Bu randevu tarihinde başka bir randevunuz vardır.");
					return;
				}
				if (!isDateOk()) {
					return;
				}
				
				if(getHastaRandevuCount() >= 4){
					JOptionPane.showMessageDialog(rootPane, "4 randevudan daha fazla randevu alamssınız. Lütfen randevunuzu siliniz yada raporunuzu bekleyiniz.");
					return;
				}
				
				if(doktorRandevuCount(secilenDoktor)>= 1){
					JOptionPane.showMessageDialog(rootPane, "Aynı doktordan birden fazla randevu alamssınız. Lütfen randevunuzu siliniz yada raporunuzu bekleyiniz.");
					return;
				}
				//System.out.println("Hasta İslem ekranında secilen doktor : " + secilenDoktor.getAd() + " " + secilenDoktor.getSoyad()); // çıktı dogru geliyor

			}
		}

		int ok = JOptionPane.showConfirmDialog(rootPane, secilenDoktor.getAd() + " " + secilenDoktor.getSoyad() + "'dan randevu almak istedğinize eminmisiniz", "Randevu",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (ok == 0) {
			try {
				mySQL<Randevu> database = new mySQL();
				if (randevuList.size() != 0) {

					System.out.println("EKLENECEK RANDEVU KODU : " + randevuKodu);
					System.out.println("YENİ RANDEVU EKLENİYOR!!");
					Randevu new_randevu = new Randevu(kulllaniciHasta.getTc(), secilenDoktor.getSifre(), RandevuDate.getDate(), createCode());
					database.addToDatabase(new_randevu);
					System.out.println("YENİ RANDEVU EKLENDİ");
					//listenin yenilenmesi gerekmektedir
					randevuList = anaEkran.hastane.getRandevuList();
				} else {
					database.addToDatabase(new Randevu(kulllaniciHasta.getTc(), secilenDoktor.getSifre(), RandevuDate.getDate(), "r_1"));
					System.out.println("İLK RANDEVU EKLENDİ");
					// listenin yenilenmesi gerekmektedir
					randevuList = anaEkran.hastane.getRandevuList();
				}
				JOptionPane.showMessageDialog(rootPane, "Randevunuz başarıyla alınmıştır.");
			} catch (Exception ex) {
				System.out.println("EXCEPTİON İS : " + ex.toString());
				JOptionPane.showMessageDialog(rootPane, "İşlem başarısız oldu.");
			}

		}

    }//GEN-LAST:event_randevuAlActionPerformed

    private void bolum_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bolum_boxActionPerformed
		doktor_box.removeAllItems();
		for (Doktor doktor : doktorList) {
			if (doktor.getBolum().equals(bolum_box.getSelectedItem())) {
				doktor_box.addItem(doktor.getAd() + " " + doktor.getSoyad());
			}

		}
    }//GEN-LAST:event_bolum_boxActionPerformed

    private void doktor_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doktor_boxActionPerformed

    }//GEN-LAST:event_doktor_boxActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		// TODO add your handling code here:
		removeAllTable();

		for (Doktor d : doktorList) {
			if (d.getBolum().equals(bolum_box.getSelectedItem())) {
				dtm.addRow(new Object[]{d.getAd() + " " + d.getSoyad(), d.getBolum(), d.getUnvan(), ""});
			}
		}

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		this.setVisible(false);
		anaEkran.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void hastaRandevuDefteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hastaRandevuDefteriActionPerformed
		//bu ekrana gecis yapan hastanın randevualrının bulundugu ekrana gecis yapar
		HastaRandevuDefteri randevu = new HastaRandevuDefteri(kulllaniciHasta);
		randevu.setVisible(true);
		this.setVisible(false);
    }//GEN-LAST:event_hastaRandevuDefteriActionPerformed

    private void bolumSelected(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bolumSelected


    }//GEN-LAST:event_bolumSelected

    private void bolumSec(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bolumSec

    }//GEN-LAST:event_bolumSec

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(HastaIslemEkranı.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(HastaIslemEkranı.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(HastaIslemEkranı.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(HastaIslemEkranı.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new HastaIslemEkranı().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser RandevuDate;
    private javax.swing.JComboBox<String> bolum_box;
    private javax.swing.JTable doktorTablo;
    private javax.swing.JComboBox<String> doktor_box;
    private javax.swing.JButton hastaRandevuDefteri;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton randevuAl;
    // End of variables declaration//GEN-END:variables
}
