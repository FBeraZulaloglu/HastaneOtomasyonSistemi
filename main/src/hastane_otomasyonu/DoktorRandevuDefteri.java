/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hastane_otomasyonu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author faruk
 */
public class DoktorRandevuDefteri extends javax.swing.JFrame {

	/**
	 * Creates new form DoktorRandevuDefteri
	 */
	Ana_Ekran anaEkran = new Ana_Ekran();
	mySQL myDataBase = anaEkran.myDataBase;
	Doktor kullaniciDoktor;
	DefaultListModel<String> dlm = new DefaultListModel<>();
	List<Randevu> randevular = anaEkran.hastane.getRandevuList();
	List<Doktor> doktorlar = anaEkran.hastane.getDoktorList();
	List<Hasta> hastalar = anaEkran.hastane.getHastaList();
	Randevu bitenRandevu;

	public DoktorRandevuDefteri() {
		initComponents();
	}

	public DoktorRandevuDefteri(Doktor kullanici) {
		initComponents();
		for (Doktor doktor : doktorlar) {

			if (doktor.getSifre().equals(kullanici.getSifre())) {
				System.out.println("SİFRELER AYNI");
				this.kullaniciDoktor = doktor;
			}
		}

		randevuList.setModel(dlm);
		System.out.println(kullaniciDoktor.getAd() + "RANDEVU DEFTERİ");
		doktorAd.setText(kullaniciDoktor.getAd() + " " + kullaniciDoktor.getSoyad());
		doktorBolum.setText("UZMANLIK : " + kullaniciDoktor.getBolum());;
		showRandevuList();
		addRaporDirectories();
	}

	private void showRandevuList() {
		if (randevuList.getSize().width == 0) {
			JOptionPane.showMessageDialog(rootPane, "Herhangi bir randevunuz bulunmamaktadir", "Randevu", JOptionPane.INFORMATION_MESSAGE);
		}
		int counter = 0;
		for (Randevu randevu : randevular) {
			if (randevu.getDoktorSiFre().equals(kullaniciDoktor.getSifre())) {
				for (Hasta hasta : hastalar) {
					if (hasta.getTc().equals(randevu.getHastaTc())) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						String date = sdf.format(randevu.getRandevuTarih());
						dlm.add(counter, (counter + 1) + ") " + date + "," + hasta.getAd() + " " + hasta.getSoyad() + " " + hasta.getTc());
						System.out.println("TABLOYA BİR RANDEVU EKLENDİ");
						counter++;
					}

				}

			}
		}

	}
	//listeyi temizler
	private void clearRandevuList(){
		dlm.clear();
	} 
	//raporların bulunacağı drler oluşturulur her hastaya ayrı bir dir
	private void addRaporDirectories() {
		// Bütün hastalar için adları soyadları ve tc leri nin bulunduğu dir ler ekler
		for (Hasta hasta : hastalar) {
			try {
				File raporFile = new File("C:\\Users\\faruk\\Documents\\NetBeansProjects\\Hastane_Otomasyonu_Database\\HastaRaporlari\\" + hasta.getAd() + " " + hasta.getSoyad() + "_" + hasta.getTc());
				if (!raporFile.exists()) {
					if (raporFile.mkdir()) {
						System.out.println("Hastanın Rapor directorysi oluşturuldu");
					}
				}
			} catch (Exception ex) {
				System.out.println("RAPOR DİR EKLENEMEDİ");
			}
		}
	}
	
	String getRaporSıra(String hastaDirPath) {
		//Hastanın raporlarına bir sıra verme işlemi için kullanılır
		File hastaDir = new File(hastaDirPath);
		File[] raporlar = hastaDir.listFiles();
		if (raporlar != null) {
			return "RAPOR " + (raporlar.length + 1);
		} else {
			return "RAPOR " + 1;
		}
	}
	
	//hastaya rapor eklenir
	boolean addRapor(int hastaTC) {
		// seçilen hastaya text area rapor yazılır
		if (rapor_txt.getText().equals("") || rapor_txt.getText().length() < 10) {
			System.out.println("RAPOR EKLENEMEMİŞTİRRR.");
			JOptionPane.showMessageDialog(rootPane, "Yazdıgınız raporun uzunluğu en az 10 karakter içermelidir.");
			return false;
		} else {
			System.out.println("RAPOR UZUNLUGU : " + rapor_txt.getText().length());
			for (Hasta hasta : hastalar) {
				if (hasta.getTc() == hastaTC) {
					try {
						//istenieln hasta bulunduktan sonra o hastanın dir ine rapor eklenir(yeni bir txt dosyası)
						String dir = "C:\\Users\\faruk\\Documents\\NetBeansProjects\\Hastane_Otomasyonu_Database\\HastaRaporlari\\" + hasta.getAd() + " " + hasta.getSoyad() + "_" + hasta.getTc();//dir formatı böyle belirlenmiştir.
						//dir belirlendikten sonra bu dire rapor eklemesi yapılır
						File rapor = new File(dir, getRaporSıra(dir) + ".txt");
						if (!rapor.exists()) {
							rapor.createNewFile();
						}
						RaporYaz(rapor);
					} catch (Exception ex) {
						System.out.println("RAPOR SİSTEME EKLENEMEMİŞTİR.");
						System.out.println("RAPOR HATA :" + ex.toString());
						return false;
					}

				}

			}
		}
		return true;
	}
	
	//rapor yazın içinde addRApor çalışır
	void RaporYaz(File rapor) {
		try {
			FileWriter myWriter = new FileWriter(rapor.getAbsolutePath());
			myWriter.write(rapor_txt.getText());
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(rootPane, "Rapor yazılamadı. Tekrar Deneyiniz.");
			e.printStackTrace();
		}
	}
	
	void randevuKodlarıGüncelle(int start_index){
		for (int j = start_index; j < randevular.size(); j++) {
			int eskiKodNo = Integer.parseInt(randevular.get(j).getRandevuKodu().split("_")[1]);
			int yeniKodNo = eskiKodNo - 1;
			String yeniKod = "r_" + yeniKodNo;
			myDataBase.updateRandevuKod(randevular.get(j).getRandevuKodu(), yeniKod);//eski kod ve yeni kod girilir
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        randevuList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        doktorAd = new javax.swing.JLabel();
        doktorBolum = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        secilenHastaAd = new javax.swing.JLabel();
        secilenHastaTC = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        rapor_txt = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(randevuList);

        jLabel1.setText("               RANDEVU LİSTESİ");
        jLabel1.setToolTipText("");

        doktorAd.setText("DOKTOR AD SOYAD ....");

        doktorBolum.setText("DOKTOR BOLUM..");

        jButton1.setText("Ana Ekran");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Rapor Yaz");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Hasta Adı:");

        jLabel3.setText("Hasta Tc:");

        secilenHastaAd.setText("SECİLEN HASTA ADI...");

        secilenHastaTC.setText("SECİLEM HASTA TC...");

        rapor_txt.setColumns(20);
        rapor_txt.setRows(5);
        rapor_txt.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                rapor_txtPropertyChange(evt);
            }
        });
        rapor_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                raporUzunlukKontrol(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rapor_txtKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(rapor_txt);

        jLabel4.setText("RAPORUNUZU AŞAĞI KISMA YAZABİLİRSİNİZ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(doktorAd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(doktorBolum, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(secilenHastaTC, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(secilenHastaAd))
                        .addGap(0, 51, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(doktorAd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(doktorBolum, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(secilenHastaAd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(secilenHastaTC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(51, 51, 51)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	// bu metod listeden seçilen randevunun dönmesini sağlar
	private int getHastaTc() {
		//ilk önce virgüle göre ayırır sıra numarası ve gün gelir sonra boşluk karakterine göre günü alır.
		if (randevuList.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(rootPane, "HERHANGİ BİR HASTA SEÇİLMEDİ");
			return 0;
		}
		String hastaAd = randevuList.getSelectedValue().split(",")[1].split(" ")[0];
		String hastaSoyad = randevuList.getSelectedValue().split(",")[1].split(" ")[1];
		String hastaTC = randevuList.getSelectedValue().split(",")[1].split(" ")[2];

		for (Randevu randevu : randevular) {

			if (Integer.parseInt(hastaTC) == randevu.getHastaTc()) {
				secilenHastaAd.setText(hastaAd + " " + hastaSoyad);
				secilenHastaTC.setText(hastaTC);
				rapor_txt.setVisible(true);
				bitenRandevu = randevu;
				return Integer.parseInt(hastaTC);
			}
		}
		return 0;
	}

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		this.setVisible(false);
		Ana_Ekran anaEkran = new Ana_Ekran();
		anaEkran.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		// Secilen hasta için bir rapor yazılması gerkemektedir.
		if (!addRapor(getHastaTc())) {
			return;
		}
		JOptionPane.showMessageDialog(rootPane, secilenHastaAd.getText() + " 'a rapor yazılmıştır.");
		//mySQL database = new mySQL();
		//raporu yazılan randevu bitmiş sayılır ve sitemden otomatik silinir.
		int bitenRandevuIndex = 0;
		int i = 0;
		while (randevular.get(i) != null) {
			if (randevular.get(i) == bitenRandevu) {
				bitenRandevuIndex = i;
				break;
			}
			i++;
		}
		myDataBase.remove(bitenRandevu);
		randevular = anaEkran.hastane.getRandevuList();
		clearRandevuList();
		showRandevuList();
		//RANDEVULARIN KODLARI UPDATE EDİLMELİDİR
		int start_index = bitenRandevuIndex;
		randevuKodlarıGüncelle(start_index);
		

    }//GEN-LAST:event_jButton2ActionPerformed

    private void rapor_txtPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_rapor_txtPropertyChange
		// TODO add your handling code here:
		System.out.println("RAPOR YAZILIYOR...");
    }//GEN-LAST:event_rapor_txtPropertyChange

    private void raporUzunlukKontrol(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_raporUzunlukKontrol
		// yazarken raporun uzunluğunu kontrol et ve eğer satırda 20 karakteri geçerse bir \n at
		String[] satirSayi = rapor_txt.getText().split("\n");
		//System.out.println(satirSayi.length+" tane satırım vardır");
		int uzunluk = rapor_txt.getText().split("\n")[satirSayi.length - 1].length();
		if (uzunluk > 40) {
			rapor_txt.setText(rapor_txt.getText() + "\n");
		}
		//NOT Silme düzgün şekilde yapılamıyor..
    }//GEN-LAST:event_raporUzunlukKontrol

    private void rapor_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rapor_txtKeyTyped
		// TODO add your handling code here:

    }//GEN-LAST:event_rapor_txtKeyTyped

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
			java.util.logging.Logger.getLogger(DoktorRandevuDefteri.class
					.getName()).log(java.util.logging.Level.SEVERE, null, ex);

		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(DoktorRandevuDefteri.class
					.getName()).log(java.util.logging.Level.SEVERE, null, ex);

		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(DoktorRandevuDefteri.class
					.getName()).log(java.util.logging.Level.SEVERE, null, ex);

		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DoktorRandevuDefteri.class
					.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new DoktorRandevuDefteri().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel doktorAd;
    private javax.swing.JLabel doktorBolum;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> randevuList;
    private javax.swing.JTextArea rapor_txt;
    private javax.swing.JLabel secilenHastaAd;
    private javax.swing.JLabel secilenHastaTC;
    // End of variables declaration//GEN-END:variables
}
