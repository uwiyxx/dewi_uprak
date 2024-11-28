/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Koneksi;

/**
 *
 * @author Client
 */
public class Order extends javax.swing.JInternalFrame {
    private DefaultTableModel model;
    String id_pesanan, nama_pelanggan, nama_petugas, status;
    int jumlah, no_meja;
    /**
     * Creates new form Order
     */
    public Order() {
        initComponents();
        Nonaktif();
        
        model = new DefaultTableModel();
        jTable1.setModel(model);
        model.addColumn("ID Pesanan");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Jumlah");
        model.addColumn("Nama Petugas");
        model.addColumn("No Meja");
        model.addColumn("Status");
        
        GetData();
        IdOtomatis();
    }
    
    public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            Statement stat = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * From order";
            ResultSet rs = stat.executeQuery(sql);
            
            while (rs.next()){
                Object[] obj = new Object[6];
                obj [0] = rs.getString("id_pesanan");
                obj [1] = rs.getString("nama_pelanggan");
                obj [2] = rs.getInt("jumlah");
                obj [3] = rs.getString("nama_petugas");
                obj [4] = rs.getInt("no_meja");
                obj [5] = rs.getString("status");
                
                model.addRow(obj);
            }
        }catch (SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void SelectData(){
        int i = jTable1.getSelectedRow();
        if (i == -1) 
        {
            return;
        }Tidpesanan.setText(""+model.getValueAt(1, 0));
        Tnamapelanggan.setText(""+model.getValueAt(i, 1));
        Tjumlah.setText(""+model.getValueAt(i, 2));
        Tnamapetugas.setText(""+model.getValueAt(i, 3));
        Tnomeja.setText(""+model.getValueAt(i, 4));
        Cstatus.setSelectedItem(""+model.getValueAt(i, 5));
        hapus.setEnabled(true);
        edit.setEnabled(true);
    }
    
    public void IdOtomatis(){
        try{
            com.mysql.jdbc.Statement stat = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * From order order by id_pesanan desc";
            
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()){
                String id = rs.getString("id_pesanan").substring(4);
                String AN = ""+(Integer.parseInt(id)+1);
                String nol = "";
                
                if(AN.length()==1)
                {nol="00";}
                else if (AN.length()==2)
                {nol="0";}
                else if( AN.length()==3)
                {nol="";}
                    Tidpesanan.setText("PN"+nol+AN);
            }else{
                Tidpesanan.setText("PN001");
            }
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void LoadData(){
         id_pesanan = Tidpesanan.getText();
         nama_pelanggan = Tnamapelanggan.getText();
         jumlah = Integer.valueOf(Tjumlah.getText()) ;
         nama_petugas = Tnamapetugas.getText();
         no_meja = Integer.valueOf(Tnomeja.getText()) ;
         status = (String)Cstatus.getSelectedItem();
    }
    
    public void SimpanData(){
         LoadData();
       
          try {
            Connection conn = Koneksi.koneksiDb();
            String query = "INSERT INTO order (id_pesanan, nama_pelanggan, jumlah, nama_petugas, no_meja, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
           pst.setString(1, id_pesanan);
           pst.setString(2, nama_pelanggan);
           pst.setInt(3, jumlah);
           pst.setString(4, nama_petugas);
           pst.setInt(5, no_meja);
           pst.setString(6, status);
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
           GetData();
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Gagal menyimpan data " + e.getMessage());
       }
    }
    
    public void UbahData(){
       LoadData();
        
       try{
           com.mysql.jdbc.Statement stat = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
           String sql = "Update order Set nama_pelanggan = '"+nama_pelanggan+"',"
            +"jumlah = '"+jumlah+"',"
            +"nama_petugas = '"+nama_petugas+"',"
            +"no_meja = '"+no_meja+"',"
            +"status = '"+status+"' WHERE id_pesanan = '"+id_pesanan+"'";
        
        PreparedStatement p =(PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
        p.executeUpdate();
        
           GetData();
           Kosongkan();
           SelectData();
           
           JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
       }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
   }
}
    
     public void HapusData(){
         LoadData();
       
       int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data order "+id_pesanan+"?","konfirmasi",JOptionPane.OK_CANCEL_OPTION);
       if(pesan == JOptionPane.OK_OPTION){
           try{
               String sql  = "Delete From order Where id_pesanan = '"+id_pesanan+"'";
               PreparedStatement p = Koneksi.koneksiDb().prepareStatement(sql);
               p.execute();
               
               GetData();
               
               JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
           }catch(SQLException error){
               JOptionPane.showConfirmDialog(null, error.getMessage());
           }
       }
    }
     
     public void Nonaktif(){
       Tidpesanan.setEnabled(true);
       Tnamapelanggan.setEnabled(true);
       Tjumlah.setEnabled(true);
       Tnamapetugas.setEnabled(true);
       Tnomeja.setEnabled(true);
       Cstatus.setEnabled(true);
       simpan.setEnabled(true);
       edit.setEnabled(true);
       hapus.setEnabled(true);
   }
    
     public void Aktif(){
       Tidpesanan.setEnabled(true);
       Tnamapelanggan.setEnabled(true);
       Tjumlah.setEnabled(true);
       Tnamapetugas.setEnabled(true);
       Tnomeja.setEnabled(true);
       Cstatus.setEnabled(true);
       simpan.setEnabled(true);
       edit.setEnabled(true);
       hapus.setEnabled(true);
       Tnamapelanggan.requestFocus();
   }
     
     public void Kosongkan(){
       Tidpesanan.setText("");
       Tnamapelanggan.setText("");
       Tjumlah.setText("");
       Tnamapetugas.setText("");
       Tnomeja.setText("");
       Cstatus.setSelectedItem("Tidak Dipilih");
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Tidpesanan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Tnamapelanggan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Tjumlah = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Tnamapetugas = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Tnomeja = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Cstatus = new javax.swing.JComboBox<>();
        simpan = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(722, 462));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Id Pesanan");

        jLabel2.setText("Nama Pelanggan");

        jLabel3.setText("Jumlah");

        jLabel4.setText("Nama Petugas");

        jLabel5.setText("No Meja");

        jLabel6.setText("Status");

        Cstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak Dipilih", "Sudah Bayar", "Belum Bayar" }));

        simpan.setBackground(new java.awt.Color(51, 255, 0));
        simpan.setText("Simpan");
        simpan.setContentAreaFilled(false);
        simpan.setOpaque(true);
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        edit.setBackground(new java.awt.Color(51, 51, 255));
        edit.setText("Edit");
        edit.setContentAreaFilled(false);
        edit.setOpaque(true);
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        hapus.setBackground(new java.awt.Color(255, 0, 51));
        hapus.setText("Hapus");
        hapus.setContentAreaFilled(false);
        hapus.setOpaque(true);
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGap(7, 7, 7)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Tidpesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Tnamapelanggan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(Tjumlah))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Tnamapetugas)
                                    .addComponent(Tnomeja)
                                    .addComponent(Cstatus, 0, 139, Short.MAX_VALUE)
                                    .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Tidpesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Tnamapelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Tjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Tnamapetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Tnomeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(Cstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Pesanan", "Nama Pelanggan", "Jumlah", "Nama Petugas", "No Meja", "Status"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        // TODO add your handling code here:
        UbahData();
        Kosongkan();
        Nonaktif();
        simpan.setEnabled(true);
        edit.setEnabled(true);
        hapus.setEnabled(true);
    }//GEN-LAST:event_editActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        // TODO add your handling code here:
        SimpanData();
        Kosongkan();
        IdOtomatis();
        Nonaktif();
        simpan.setEnabled(true);
        edit.setEnabled(true);
        hapus.setEnabled(true);
    }//GEN-LAST:event_simpanActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        HapusData();
        Kosongkan();
        Nonaktif();
        simpan.setEnabled(true);
        edit.setEnabled(true);
        hapus.setEnabled(true);
    }//GEN-LAST:event_hapusActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        SelectData();
        Aktif();
        Tidpesanan.setEnabled(true);
        simpan.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Cstatus;
    private javax.swing.JTextField Tidpesanan;
    private javax.swing.JTextField Tjumlah;
    private javax.swing.JTextField Tnamapelanggan;
    private javax.swing.JTextField Tnamapetugas;
    private javax.swing.JTextField Tnomeja;
    private javax.swing.JButton edit;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton simpan;
    // End of variables declaration//GEN-END:variables
}
