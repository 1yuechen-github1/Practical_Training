package alarm.add;
/*
	用户文件类，属性有 id,name,passward,photo
	register time, vip_id, reserved
	与数据表TFiles1  一一对应
*/
import alarm.add.land;
public class TFilees1 {
	private int id;
	private String name;
	private String passward;
	//private blob   photo;
	private int register_time;
	private int vip_id;
	private String reserved;
	private String photo;
	
	public void setid(int id) {
		this.id =id;
	}
	public int getid() {
		return id;
	}
	
	public void setregister_time(int register_time) {
		this.register_time =register_time;
	}
	public int getregister_time() {
		return register_time;
	}
	
	public void setvip_id(int vip_id) {
		this.vip_id =vip_id;
	}
	public int getvip_id() {
		return vip_id;
	}
	
	public  void setname(String username) {
		this.name =username;
	}
	public String getname(String username) {
		return username;
	}
	
	public void setpassward(String passward) {
		this.passward =passward;
	}
	public String getpassward() {
		return passward;
	}  
	
	public void setreserved(String reserved) {
		this.reserved =reserved;
	}
	public String getreserved() {
		return reserved;
	}  
	
	public void setphoto(String photo) {
		this.photo =photo;
	}
	public String getphoto() {
		return photo;
	} 
}
