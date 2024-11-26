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
public class Menu extends javax.swing.JInternalFrame {
    private DefaultTableModel model;
    String id_menu, nama_menu;
    int harga;
    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        Nonaktif();
        
        model = new DefaultTableModel();
        Table.setModel(model);
        model.addColumn("ID Menu");
        model.addColumn("Nama Menu");
        model.addColumn("Harga");
        
        GetData();
        IdOtomatis();
    }

    public void GetData(){
         model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            Statement stat = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * From menu";
            ResultSet rs = stat.executeQuery(sql);
            
            while (rs.next()){
                Object[] obj = new Object[3];
                obj [0] = rs.getString("id_menu");
                obj [1] = rs.getString("nama_menu");
                obj [2] = rs.getString("harga");
                
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
        }Idmenu.setText(""+model.getValueAt(1, 0));
        Namamenu.setText(""+model.getValueAt(i, 1));
        Harga.setText(""+model.getValueAt(i, 2));
        hapus.setEnabled(true);
        edit.setEnabled(true);
    }
    
    public void IdOtomatis(){
        try{
            com.mysql.jdbc.Statement stat = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * From menu order by id_menu desc";
            
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()){
                String id = rs.getString("id_menu").substring(3);
                String AN = ""+(Integer.parseInt(id)+1);
                String nol = "";
                
                if(AN.length()==1)
                {nol="00";}
                else if (AN.length()==2)
                {nol="0";}
                else if( AN.length()==3)
                {nol="";}
                    Idmenu.setText("MN"+nol+AN);
            }else{
                Idmenu.setText("MN001");
            }
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void LoadData(){
         id_menu = Idmenu.getText();
         nama_menu = Namamenu.getText();
         harga = Integer.valueOf(Harga.getText()) ;
    }
    
    public void SimpanData(){
         LoadData();
       
          try {
            Connection conn = Koneksi.koneksiDb();
            String query = "INSERT INTO menu (id_menu, nama_menu, harga) VALUES (?, ?, ?";
            PreparedStatement pst = conn.prepareStatement(query);
           pst.setString(1, id_menu);
           pst.setString(2, nama_menu);
           pst.setInt(3, harga);
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
           String sql = "Update menu Set nama_menu = '"+nama_menu+"',"
            +"harga = '"+harga+"' WHERE id_menu = '"+id_menu+"'";
        
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
       
       int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data menu "+id_menu+"?","konfirmasi",JOptionPane.OK_CANCEL_OPTION);
       if(pesan == JOptionPane.OK_OPTION){
           try{
               String sql  = "Delete From menu Where id_menu = '"+id_menu+"'";
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
       Idmenu.setEnabled(true);
       Namamenu.setEnabled(true);
       Harga.setEnabled(true);
       simpan.setEnabled(true);
       edit.setEnabled(true);
       hapus.setEnabled(true);
   }
    
     public void Aktif(){
       Idmenu.setEnabled(true);
       Namamenu.setEnabled(true);
       Harga.setEnabled(true);
       simpan.setEnabled(true);
       edit.setEnabled(true);
       hapus.setEnabled(true);
       Namamenu.requestFocus();
   }
     
     public void Kosongkan(){
       Idmenu.setText("");
       Namamenu.setText("");
       Harga.setText("");

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
        Idmenu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Namamenu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Harga = new javax.swing.JTextField();
        simpan = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Id Menu ");

        jLabel2.setText("Nama Menu ");

        jLabel3.setText("Harga");

        simpan.setBackground(new java.awt.Color(0, 255, 51));
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(33, 33, 33)
                                    .addComponent(Idmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Namamenu)
                                        .addComponent(Harga)))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(97, 97, 97)
                            .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Idmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Namamenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                "ID Menu", "Nama Menu", "Harga"
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
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
//        int selectedRow = Table.getSelectedRow();
//
//        if (selectedRow == -1) {
//        JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit!");
//        return;
//    }
//
//        String id = Idmenu.getText();
//        String nama = Namamenu.getText();
//        String harga = Harga.getText();
//
//        if (id.isEmpty() || nama.isEmpty() || harga.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Semua input harus diisi!");
//        return;
//    }
//
//        DefaultTableModel model = (DefaultTableModel) Table.getModel();
//        model.setValueAt(id, selectedRow, 0);
//        model.setValueAt(nama, selectedRow, 1);
//        model.setValueAt(harga, selectedRow, 2);
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
        
//        String id =this.Idmenu.getText();
//        String nama =this.Namamenu.getText();
//        String harga =this.Harga.getText();
//        
//        if (id.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Id Menu wajib diisi!!");
//            return;
//        }
//        if (nama.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Nama Menu wajib diisi!!");
//            return;
//        }
//        if (harga.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Harga wajib diisi!!");
//            return;
//        }
//         DefaultTableModel model = (DefaultTableModel) Table.getModel();
//        model.addRow(new Object[]{id, nama, harga});
//
//        Idmenu.setText("");   
//        Namamenu.setText("");
//        Harga.setText("");
        
    }//GEN-LAST:event_simpanActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        HapusData();
        Kosongkan();
        IdOtomatis();
        Nonaktif();
        simpan.setEnabled(true);
        edit.setEnabled(true);
        hapus.setEnabled(true);
//        int selectedRow = Table.getSelectedRow();
//        if (selectedRow != -1) {
//            DefaultTableModel model = (DefaultTableModel) Table.getModel();
//            model.removeRow(selectedRow);   
//        } else {
//            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!");
//        }
    }//GEN-LAST:event_hapusActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        // TODO add your handling code here:
         SelectData();
        Aktif();
        Idmenu.setEnabled(false);
        simpan.setEnabled(false);
    }//GEN-LAST:event_TableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Harga;
    private javax.swing.JTextField Idmenu;
    private javax.swing.JTextField Namamenu;
    private javax.swing.JTable Table;
    private javax.swing.JButton edit;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton simpan;
    // End of variables declaration//GEN-END:variables
}
