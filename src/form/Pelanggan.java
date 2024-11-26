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
public class Pelanggan extends javax.swing.JInternalFrame {
    private DefaultTableModel model;
    String id_pelanggan, nama_pelanggan, jenis_kelamin, no_hp, alamat;
    
    /**
     * Creates new form Pelanggan
     */
    public Pelanggan() {
        initComponents();
        Nonaktif();
        
        model = new DefaultTableModel();
        Table.setModel(model);
        model.addColumn("ID Pelanggan");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Jenis Kelamin");
        model.addColumn("No Hp");
        model.addColumn("Alamat");
        
        GetData();
        
    }
    public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            Statement stat = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * From pelanggan";
            ResultSet rs = stat.executeQuery(sql);
            
            while (rs.next()){
                Object[] obj = new Object[6];
                obj [0] = rs.getString("id_pelanggan");
                obj [1] = rs.getString("nama_pelanggan");
                obj [2] = rs.getString("jenis_kelamin");
                obj [3] = rs.getString("no_hp");
                obj [4] = rs.getString("alamat");
                
                model.addRow(obj);
            }
        }catch (SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void SelectData(){
        int i = Table.getSelectedRow();
        if (i == -1) 
        {
            return;
        }Tidpelanggan.setText(""+model.getValueAt(1, 0));
        Tnama.setText(""+model.getValueAt(i, 1));
        if ("P".equals(model.getValueAt(i, 2).toString())) {
            Rperempuan.setSelected(true);
        } else {
            Rlaki.setSelected(true);
        }
        Tnohp.setText(""+model.getValueAt(i, 3));
        Talamat.setText(""+model.getValueAt(i, 4));
        hapus.setEnabled(true);
        edit.setEnabled(true);
    }

   public void LoadData(){
       id_pelanggan = Tidpelanggan.getText();
       nama_pelanggan = Tnama.getText();
       jenis_kelamin = null;
       if (Rlaki.isSelected()) {
           jenis_kelamin = "L";
       } else if(Rperempuan.isSelected()){
           jenis_kelamin = "P";
       }
       no_hp = Tnohp.getText();
       alamat = Talamat.getText();
   }
   
   public void SimpanData(){
       LoadData();
       
          try {
            Connection conn = Koneksi.koneksiDb();
            String query = "INSERT INTO pelanggan (id_pelanggan, nama_pelanggan, jenis_kelamin, no_hp, alamat) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
           pst.setString(1, id_pelanggan);
           pst.setString(2, nama_pelanggan);
           pst.setString(3, jenis_kelamin);
           pst.setString(4, no_hp);
           pst.setString(5, alamat);
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
           String sql = "Update pelanggan Set nama_pelanggan = '"+nama_pelanggan+"',"
            +"jenis_kelamin = '"+jenis_kelamin+"',"
            +"no_hp = '"+no_hp+"',"
            +"alamat = '"+alamat+"' WHERE id_pelanggan = '"+id_pelanggan+"'";
        
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
       
       int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data pelanggan "+id_pelanggan+"?","konfirmasi",JOptionPane.OK_CANCEL_OPTION);
       if(pesan == JOptionPane.OK_OPTION){
           try{
               String sql  = "Delete From pelanggan Where id_pelanggan = '"+id_pelanggan+"'";
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
       Tidpelanggan.setEnabled(true);
       Tnama.setEnabled(true);
       Rlaki.setEnabled(true);
       Rperempuan.setEnabled(true);
       Tnohp.setEnabled(true);
       Talamat.setEnabled(true);
       simpan.setEnabled(true);
       edit.setEnabled(true);
       hapus.setEnabled(true);
   }
   
   public void Aktif(){
       Tidpelanggan.setEnabled(true);
       Tnama.setEnabled(true);
       Rlaki.setEnabled(true);
       Rperempuan.setEnabled(true);
       Tnohp.setEnabled(true);
       Talamat.setEnabled(true);
       simpan.setEnabled(true);
       edit.setEnabled(true);
       hapus.setEnabled(true);
       Tnama.requestFocus();
   }
   
   public void Kosongkan(){
       Tnama.setText("");
       Rlaki.isSelected();
       Rperempuan.isSelected();
       Tnohp.setText("");
       Talamat.setText("");

   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Tidpelanggan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Tnama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Rlaki = new javax.swing.JRadioButton();
        Rperempuan = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        Tnohp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        simpan = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Talamat = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(722, 487));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Id Pelanggan");

        Tidpelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TidpelangganActionPerformed(evt);
            }
        });

        jLabel2.setText("Nama Pelanggan");

        jLabel3.setText("Jenis Kelamin");

        Rlaki.setText("Laki-Laki");

        Rperempuan.setText("Perempuan");

        jLabel4.setText("No Hp");

        jLabel5.setText("Alamat");

        simpan.setBackground(new java.awt.Color(51, 255, 51));
        simpan.setText("Simpan");
        simpan.setContentAreaFilled(false);
        simpan.setOpaque(true);
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        edit.setBackground(new java.awt.Color(0, 51, 255));
        edit.setText("Edit");
        edit.setContentAreaFilled(false);
        edit.setOpaque(true);
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        hapus.setBackground(new java.awt.Color(255, 0, 0));
        hapus.setText("Hapus");
        hapus.setContentAreaFilled(false);
        hapus.setOpaque(true);
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        Talamat.setColumns(20);
        Talamat.setRows(5);
        jScrollPane2.setViewportView(Talamat);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Tnama, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                        .addComponent(Tidpelanggan)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4)))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(Rlaki)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(Rperempuan))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(Tnohp, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)))))
                            .addGap(106, 106, 106))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Tidpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Tnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Rlaki)
                    .addComponent(Rperempuan))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Tnohp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pelanggan", "Nama Pelanggan", "Jenis Kelamin", "No Hp", "Alamat"
            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        HapusData();
        Kosongkan();
        Nonaktif();
        simpan.setEnabled(true);
        edit.setEnabled(true);
        hapus.setEnabled(true);
    }//GEN-LAST:event_hapusActionPerformed

    private void TidpelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TidpelangganActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_TidpelangganActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        // TODO add your handling code here:
        SimpanData();
        Kosongkan();
        Nonaktif();
        simpan.setEnabled(true);
        edit.setEnabled(true);
        hapus.setEnabled(true);
    }//GEN-LAST:event_simpanActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        // TODO add your handling code here:
        UbahData();
        Kosongkan();
        Nonaktif();
        simpan.setEnabled(true);
        edit.setEnabled(true);
        hapus.setEnabled(true);
    }//GEN-LAST:event_editActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        // TODO add your handling code here:
       SelectData();
        Aktif();
        Tidpelanggan.setEnabled(false);
        simpan.setEnabled(false);
    }//GEN-LAST:event_TableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Rlaki;
    private javax.swing.JRadioButton Rperempuan;
    private javax.swing.JTable Table;
    private javax.swing.JTextArea Talamat;
    private javax.swing.JTextField Tidpelanggan;
    private javax.swing.JTextField Tnama;
    private javax.swing.JTextField Tnohp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton edit;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton simpan;
    // End of variables declaration//GEN-END:variables
}
