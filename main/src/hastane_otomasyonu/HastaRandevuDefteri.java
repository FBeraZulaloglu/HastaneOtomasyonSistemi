/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hastane_otomasyonu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HastaRandevuDefteri extends javax.swing.JFrame {

	/**
	 * Creates new form HastaRandevuDefteri
	 */
	Ana_Ekran anaEkran = new Ana_Ekran();
	mySQL myDataBase = anaEkran.myDataBase;
	//Classlar
	DefaultListModel<String> dlm;
	Hasta kullaniciHasta;
	//DataBase bağlantıları
	List<Randevu> randevuList = anaEkran.hastane.getRandevuList();
	List<Doktor> doktorList = anaEkran.hastane.getDoktorList();
	List<Hasta> hastaList = anaEkran.hastane.getHastaList();
	//Rapor Ekranı özellikleri
	JFrame raporFrame = new JFrame();
	JPanel panel = new JPanel();
	BoxLayout raporLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

	public HastaRandevuDefteri() {
		initComponents();
	}

	public HastaRandevuDefteri(Hasta kullanici) {
		initComponents();
		if (kullanici != null) {
			kullaniciHasta = kullanici;
			hastaAd.setText(kullaniciHasta.getAd() + " " + kullaniciHasta.getSoyad());
			hastaTc.setText(kullaniciHasta.getTc().toString());
		} else {
			System.out.println("KULLANICI HASTA BİLİNMEMEKTEDİR !!!!");
		}

		dlm = new DefaultListModel<>();
		randevular.setModel(dlm);
		//RAPORLARI GÖSTERECEK OLAN EKRAN
		panel.setLayout(raporLayout);
		raporFrame.setLocation(this.getHeight() - this.getHeight() / 2, this.getWidth() - this.getWidth() / 2);
		raporFrame.setResizable(true);
		raporFrame.setSize(300, 300);
		JLabel title = new JLabel("RAPORLARIM\r\n");
		panel.add(title);
		//RAPORLARI GÖSTERECEK OLAN EKRAN BİTİŞ
		showRandevuList();

	}

	private void clearRandevuList() {
		dlm.clear();
	}

	private void showRandevuList() {
		int sayac = 0;
		if (randevuList.size() == 0) {
			JOptionPane.showMessageDialog(rootPane, "Herhangi bir randevunuz bulunmamaktadir", "Randevu", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		for (Randevu randevu : randevuList) {
			{
				if (kullaniciHasta.getTc().equals(randevu.getHastaTc())) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					//YYYY/MM/DD yapıldıgında cıktı istendigi gibi gelmiyor.
					String date = sdf.format(randevu.getRandevuTarih());
					for (Doktor doktor : doktorList) {
						if (randevu.getDoktorSiFre().equals(doktor.getSifre())) {
							dlm.add(sayac, sayac + 1 + ") " + doktor.getAd() + ". " + doktor.getSoyad() + " "
									+ doktor.getBolum() + " " + doktor.getUnvan() + "," + date);
							sayac++;
						}
					}

				}
			}

		}
	}

	String getFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//YYYY/MM/DD yapıldıgında cıktı istendigi gibi gelmiyor.
		String formatDate = sdf.format(date);
		return formatDate;
	}

	private Randevu getRandevu() {
		//virgülle iki parçaya ayrılan stringin ikinci kısmı gün olduğu için 1. index alınır
		if (randevular.getSelectedValue() != null) {
			String tarih = randevular.getSelectedValue().split(",")[1];

			for (Randevu randevu : randevuList) {
				System.out.println(tarih + "-------" + randevu.getRandevuTarih());
				if (getFormatDate(randevu.getRandevuTarih()).equals(tarih)) {

					return randevu;
				}
			}
		}
		

		return null;
	}

	private void ReadRaporlar(String dirPath) {
		File dir = new File(dirPath);
		File[] raporlar = dir.listFiles();
		for (File file : raporlar) {
			BufferedReader bf = null;
			String temp = "";
			try {
				bf = new BufferedReader(new FileReader(file.getAbsolutePath()));
				String line;
				while ((line = bf.readLine()) != null) {

					temp += line;
					System.out.println(line);
				}
			} catch (Exception ex) {
				System.out.println(ex.toString() + "");
			}
			JTextArea raporArea = new JTextArea(temp);
			raporArea.setEditable(false);
			JLabel randevuNo = new JLabel(file.getName());
			//randevuNo.setLocation(, WIDTH);
			panel.add(randevuNo);
			panel.add(raporArea);
			raporFrame.add(panel);
			raporFrame.setVisible(true);
			temp = "";

		}

	}

	void randevuKodlarıGüncelle(int start_index) {
		for (int j = start_index; j < randevuList.size(); j++) {
			int eskiKodNo = Integer.parseInt(randevuList.get(j).getRandevuKodu().split("_")[1]);
			int yeniKodNo = eskiKodNo - 1;
			String yeniKod = "r_" + yeniKodNo;
			myDataBase.updateRandevuKod(randevuList.get(j).getRandevuKodu(), yeniKod);//eski kod ve yeni kod girilir
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        randevular = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        randevuSil = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        hastaTc = new javax.swing.JLabel();
        hastaAd = new javax.swing.JLabel();
        hastaAd1 = new javax.swing.JLabel();
        hastaAd2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        randevular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                randevularMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(randevular);

        jLabel1.setText("HASTA RANDEVU DEFTERİ");

        randevuSil.setText("RANDEVU SİL");
        randevuSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randevuSilActionPerformed(evt);
            }
        });

        jButton2.setText("Geri");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        hastaAd1.setText("HASTA AD SOYAD");

        hastaAd2.setText("HASTA TC KİMLİK NO");

        jButton1.setText(" RAPORLARIMI GÖSTER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hastaAd1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hastaAd2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hastaTc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hastaAd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(randevuSil, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hastaAd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hastaAd1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hastaTc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hastaAd2))
                        .addGap(65, 65, 65)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(randevuSil, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		// Hasta isleme geri dön
		HastaIslemEkranı islemEkrani = new HastaIslemEkranı(kullaniciHasta);
		islemEkrani.setVisible(true);
		this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void randevuSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randevuSilActionPerformed
		// Secilen randevuyu sil

		System.out.println("Hasta Randevu Defteri : " + kullaniciHasta.getAd() + " " + kullaniciHasta.getSoyad());
		//getRandevu metodu ile hangi randevunun silineceğini bulduktan sonra 
		//doktorun ve hastanın randevularını sil
		if (getRandevu() != null) {
			int start_index = 0;
			for (Randevu randevu : randevuList) {
				//randevu listesi update olduğu için kontrol edilmesi gerekmektedir.
				if (randevu != null && getRandevu() != null) {
					System.out.println("RANDEVU NULLMI ?"+randevu);
					System.out.println("GET RANDEVU NULLL MI ?"+getRandevu());
					if (randevu.getRandevuTarih().equals(getRandevu().getRandevuTarih())) {
						myDataBase.remove(randevu);
						//randevu listesini update le
						randevuList = anaEkran.hastane.getRandevuList();
						clearRandevuList();
						showRandevuList();
						randevuKodlarıGüncelle(start_index);
					}
				}
				start_index++;

			}

			JOptionPane.showMessageDialog(rootPane, "Randevu Başarıyla Silinmiştir.", "Randevu Sil", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(rootPane, "Silinecek randevu seçilmemiştir.", "Randevu Sil", JOptionPane.INFORMATION_MESSAGE);
		}

    }//GEN-LAST:event_randevuSilActionPerformed

    private void randevularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_randevularMouseClicked
		// TODO add your handling code here:


    }//GEN-LAST:event_randevularMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		//Hastaya ait randevular daki raporların okunduğu yer
		String randevularDir = "C:\\Users\\faruk\\Documents\\NetBeansProjects\\Hastane_Otomasyonu_Database\\HastaRaporlari";
		File hastaDir = new File(randevularDir);
		File[] raporlar = hastaDir.listFiles();
		if (raporlar != null) {
			for (File file : raporlar) {
				//Belirli bir formatta oluşturulan dir okuma işlemi yapılır
				if (file.getName().equals(kullaniciHasta.getAd() + " " + kullaniciHasta.getSoyad() + "_" + kullaniciHasta.getTc())) {
					try {
						ReadRaporlar(file.getAbsolutePath());
					} catch (Exception ex) {
						System.out.println(ex);
						System.out.println("RANDEVU FİLE BULUNAMADI");
					}
				}
			}
		} else {
			System.out.println("Böyle bir dir yoktur.");
		}

    }//GEN-LAST:event_jButton1ActionPerformed

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
			java.util.logging.Logger.getLogger(HastaRandevuDefteri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(HastaRandevuDefteri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(HastaRandevuDefteri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(HastaRandevuDefteri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new HastaRandevuDefteri().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hastaAd;
    private javax.swing.JLabel hastaAd1;
    private javax.swing.JLabel hastaAd2;
    private javax.swing.JLabel hastaTc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton randevuSil;
    private javax.swing.JList<String> randevular;
    // End of variables declaration//GEN-END:variables
}