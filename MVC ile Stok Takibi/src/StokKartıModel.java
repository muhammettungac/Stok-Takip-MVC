import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class StokKartıModel {
private static String StokKodu;
private static String StokAdı;
private static int StokTipi;
private static String Birimi;
private static String BarkodNo;
private static double KDVTipi;
private static String Açıklama;
private static Object OluşturmaTarihi;
private static Baglanti baglanti;
private static ResultSet rs;
private static Statement statement;
private static PreparedStatement pstatement;
boolean yön;
static StokKartıFrameView frameview;
static StokKartıListeFrameView listeframeview;
public StokKartıModel() {
	setStokKodu(StokKartıFrameView.getStokKodu());
	setStokAdı(StokKartıFrameView.getStokAdı());
	setStokTipi(StokKartıFrameView.getStokTipi());
	setBarkodNo(StokKartıFrameView.getBarkodNo());
	setBirimi(StokKartıFrameView.getBirimi());
	setAçıklama(StokKartıFrameView.getAçıklama());
	setKDVTipi(StokKartıFrameView.getKDVTipi());
	setOluşturmaTarihi(StokKartıFrameView.getOluşturmaTarihi());
}
public static void tabloGoster() {
	listeframeview=StokKartıListeFrameController.getListeFrameView();
	try {
		statement=baglanti.con.createStatement();
		rs=statement.executeQuery("select * from stokkarti");
		while (rs.next()) {
			Object [] o= {rs.getString("StokKodu"),rs.getString("StokAdi"),rs.getInt("StokTipi"),rs.getString("Birimi"),rs.getString("Barkodu"),rs.getString("KDVTipi"),rs.getString("Aciklama"),rs.getString("OlusturmaTarihi")};
			listeframeview.tableModel.addRow(o);
		}	
	} catch (Exception e) {
		e.getStackTrace();	
	}
}
public static void getir(){
	frameview=StokKartıFrameController.getFrameView();
	StokKodu=StokKartıFrameView.getStokKodu();
	try {
		statement=baglanti.con.createStatement();
		rs=statement.executeQuery("select * from stokkarti where StokKodu like '"+getStokKodu()+"'");
		if(!getStokKodu().equals("")) {
			while(rs.next()) {
					frameview.setStokAdı(rs.getString("StokAdi"));
					frameview.setStokTipi(rs.getInt("StokTipi"));
					frameview.setBirimi(rs.getString("Birimi"));
					frameview.setBarkodNo(rs.getString("Barkodu"));
					frameview.setKDVTipi(rs.getString("KDVTipi"));
					frameview.setAçıklama(rs.getString("Aciklama"));
					frameview.setOluşturmaTarihi(rs.getString("OlusturmaTarihi"));			
				}
			}		
		else if(!getStokKodu().equals(rs.toString())) {
			frameview.setStokAdı("");
			frameview.setStokTipi(0);
			frameview.setBirimi("");
			frameview.setBarkodNo("");
			frameview.setKDVTipi("");
			frameview.setAçıklama("");
			frameview.setOluşturmaTarihi("");	
		}
		
	} catch (Exception e) {
	
		e.printStackTrace();
	}
}

public void delete(){	
	if(!StokKodu.equals("")){
		try {		
			statement=(Statement)baglanti.con.createStatement();
			statement.executeUpdate("delete from stokkarti where StokKodu='"+getStokKodu()+"'");
			JOptionPane.showMessageDialog(null, "Başarıyla Silindi");		 
	}
	catch (SQLException e) {
	
		JOptionPane.showMessageDialog(null, "Hata Tekrar Deneyin");
	}
}
	else {
		JOptionPane.showMessageDialog(null, "Geçerli bir StokKodu girin");
	}
}
public boolean kontrol() {
	
	this.yön=true;
	try {
		statement=(Statement)baglanti.con.createStatement();
		rs=statement.executeQuery("select StokKodu from stokkarti where StokKodu='"+getStokKodu()+"'");
		
			if (!rs.toString().equals(getStokKodu())) {
				this.yön=false;					
			}			
	} catch (Exception e) {
		e.printStackTrace();		
	}
return this.yön;
}

public void insert() {
	if(!getStokKodu().equals("")) {
		try {			
			pstatement=(PreparedStatement)baglanti.con.prepareStatement("insert into stokkarti (StokKodu,StokAdi,StokTipi,Birimi,Barkodu,KDVTipi,Aciklama,OlusturmaTarihi) values (?,?,?,?,?,?,?,?)");
			pstatement.setString(1,getStokKodu());
			pstatement.setString(2,getStokAdı());
			pstatement.setInt(3,getStokTipi());
			pstatement.setString(4,getBirimi());
			pstatement.setString(5,getBarkodNo().toString());
			pstatement.setDouble(6,getKDVTipi());
			pstatement.setString(7,getAçıklama());
			pstatement.setObject(8,getOluşturmaTarihi());
			pstatement.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "İşlem Başarılı");
		} catch (Exception e8) {
			JOptionPane.showMessageDialog(null, "İşlem Başarısız");		
		}
	}
	else {
		JOptionPane.showMessageDialog(null,"Bir Stok Kodu Girin" );
	}	
}

public void update() {
	if(!getStokKodu().equals("")) {
		try{
			pstatement=(PreparedStatement)baglanti.con.prepareStatement("update stokkarti set StokAdi=?,StokTipi=?,Birimi=?,Barkodu=?,KDVTipi=?,Aciklama=?,OlusturmaTarihi=? where StokKodu=?");		
			pstatement.setString(1,getStokAdı());
			pstatement.setInt(2,getStokTipi());
			pstatement.setString(3,getBirimi());
			pstatement.setString(4,getBarkodNo());
			pstatement.setDouble(5,getKDVTipi());
			pstatement.setString(6,getAçıklama());
			pstatement.setObject(7,getOluşturmaTarihi());
			pstatement.setString(8,getStokKodu());
			pstatement.executeUpdate();
			JOptionPane.showMessageDialog(null, "Güncelleme İşlemi Başarılı");
		}
		catch (SQLException e) {
		
			JOptionPane.showMessageDialog(null, "Güncelleme İşlemi Başarısız");
		}
	}
	else {
		JOptionPane.showMessageDialog(null, "Bir Stok Kodu Girin");
	}		
}

public void setStokKodu(String StokKodu) {
this.StokKodu=StokKodu;	
}
public static String getStokKodu() {
	
return StokKodu;
}


public void setStokAdı(String StokAdı) {
this.StokAdı=StokAdı;	
}
public String getStokAdı() {
	
return this.StokAdı;
}


public void setStokTipi(int StokTipi) {
this.StokTipi=StokTipi;	
}
public int getStokTipi() {
	
return this.StokTipi;
}


public void setBirimi(String Birimi) {
this.Birimi=Birimi;	
}
public String getBirimi() {
	
return this.Birimi;
}


public void setBarkodNo(String BarkodNo) {
this.BarkodNo=BarkodNo;	
}
public String getBarkodNo() {
	
return this.BarkodNo;
}


public void setKDVTipi(Double KDVTipi) {
this.KDVTipi=KDVTipi;	
}
public double getKDVTipi() {
return this.KDVTipi;
}


public void setAçıklama(String Açıklama) {
this.Açıklama=Açıklama;	
}
public String getAçıklama() {
	
return this.Açıklama;
}


public void setOluşturmaTarihi(Object OluşturmaTarihi) {
	this.OluşturmaTarihi=OluşturmaTarihi;
}

public Object getOluşturmaTarihi() {
	return this.OluşturmaTarihi;
}
}
