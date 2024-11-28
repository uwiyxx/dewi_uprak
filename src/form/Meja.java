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
public class Meja extends javax.swing.JInternalFrame {
    private DefaultTableModel model;
    String id_meja, no_meja, kategori, status;
    /**
     * Creates new form Meja
     */
    public Meja() {
        initComponents();
        Nonaktif();
        
        model = new DefaultTableModel();
        Table.setModel(model);
        model.addColumn("ID Meja");
        model.addColumn("No Meja");
        model.addColumn("Kategori");
        model.addColumn("status");
        
        GetData();
        IdOtomatis();
    }
    
    public void GetData(){
         model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            Statement stat = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * From meja";
            ResultSet rs = stat.executeQuery(sql);
            
            while (rs.next()){
                Object[] obj = new Object[4];
                obj [0] = rs.getString("id_meja");
                obj [1] = rs.getString("no_meja");
                obj [2] = rs.getString("kategori");
                obj [3] = rs.getString("status");
                
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
        }Tidmeja.setText(""+model.getValueAt(1, 0));
        Tnomeja.setText(""+model.getValueAt(i, 1));
        Ckategori.setSelectedItem(""+model.getValueAt(i, 2));
        Cstatus.setSelectedItem(""+model.getValueAt(i, 3));
        hapus.setEnabled(true);
        edit.setEnabled(true);
    }
    
    public void IdOtomatis(){
        try{
            com.mysql.jdbc.Statement stat = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * From meja order by id_meja desc";
            
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()){
                String id = rs.getString("id_meja").substring(4);
                String AN = ""+(Integer.parseInt(id)+1);
                String nol = "";
                
                if(AN.length()==1)
                {nol="00";}
                else if (AN.length()==2)
                {nol="0";}
                else if( AN.length()==3)
                {nol="";}
                    Tidmeja.setText("MJ"+nol+AN);
            }else{
                Tidmeja.setText("MJ001");
            }
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void LoadData(){
         id_meja = Tidmeja.getText();
         no_meja = Tnomeja.getText();
         kategori = (String)Ckategori.getSelectedItem();
         status = (String)Cstatus.getSelectedItem();
    }
    
    public void SimpanData(){
       LoadData();
       
          try {
            Connection conn = Koneksi.koneksiDb();
            String query = "INSERT INTO meja (id_meja, no_meja, kategori, status) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
           pst.setString(1, id_meja);
           pst.setString(2, no_meja);
           pst.setString(3, kategori);
           pst.setString(4, status);
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
           String sql = "Update meja Set no_meja = '"+no_meja+"',"
            +"kategori = '"+kategori+"',"
            +"status = '"+status+"' WHERE id_meja = '"+id_meja+"'";
        
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
       
       int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data pelanggan "+id_meja+"?","konfirmasi",JOptionPane.OK_CANCEL_OPTION);
       if(pesan == JOptionPane.OK_OPTION){
           try{
               String sql  = "Delete From meja Where id_meja = '"+id_meja+"'";
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
       Tidmeja.setEnabled(true);
       Tnomeja.setEnabled(true);
       Ckategori.setEnabled(true);
       Cstatus.setEnabled(true);
       simpan.setEnabled(true);
       edit.setEnabled(true);
       hapus.setEnabled(true);
   }
    
    public void Aktif(){
       Tidmeja.setEnabled(true);
       Tnomeja.setEnabled(true);
       Ckategori.setEnabled(true);
       Cstatus.setEnabled(true);
       simpan.setEnabled(true);
       edit.setEnabled(true);
       hapus.setEnabled(true);
       Tnomeja.requestFocus();
   }
    
    public void Kosongkan(){
       Tnomeja.setText("");
       Ckategori.setSelectedItem("Tidak Dipilih");
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
        Tidmeja = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Tnomeja = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Cstatus = new javax.swing.JComboBox<>();
        simpan = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        Ckategori = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("ID Meja");

        jLabel2.setText("No Meja");

        jLabel3.setText("Status");

        Cstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak Dipilih", "Terisi", "Kosong", " " }));

        simpan.setBackground(new java.awt.Color(0, 255, 0));
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

        hapus.setBackground(new java.awt.Color(255, 51, 51));
        hapus.setText("Hapus");
        hapus.setContentAreaFilled(false);
        hapus.setOpaque(true);
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        jLabel4.setText("Kategori");

        Ckategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak Dipilih", "Single", "Double", "Family", " " }));
        Ckategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CkategoriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Tidmeja, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(Tnomeja)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Ckategori, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Cstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Tidmeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(Tnomeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Ckategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(36, 36, 36)
                .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Meja", "No Meja", "Kategori", "Status"
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        // TODO add your handling code here:
        SelectData();
        Aktif();
        Tidmeja.setEnabled(true);
        simpan.setEnabled(true);
    }//GEN-LAST:event_TableMouseClicked

    private void CkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CkategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CkategoriActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Ckategori;
    private javax.swing.JComboBox<String> Cstatus;
    private javax.swing.JTable Table;
    private javax.swing.JTextField Tidmeja;
    private javax.swing.JTextField Tnomeja;
    private javax.swing.JButton edit;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton simpan;
    // End of variables declaration//GEN-END:variables
}
