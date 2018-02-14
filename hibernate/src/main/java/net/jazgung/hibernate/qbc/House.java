package net.jazgung.hibernate.qbc;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "qbc_House")
@Table(name = "qbc_house")
public class House extends AbstractEntity {

	private static final long serialVersionUID = -4416573797168716528L;

	private Long id;

	private Integer area;

	private Set<Room> rooms;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	@OneToMany
	@JoinTable(name = "qbc_house_room", joinColumns = @JoinColumn(name = "house_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public static void main(String[] args) throws Throwable {
		String s = "程博、谢璨璨、巩佳知、陈莉、杨涛、罗熙、杨翊、刘屾、张巧、税月、易欣、赖立根、廖腾红、郑延年、冯巧、陈炳凤、王日春、胡尚游、邓玉龙、何树礼、谢璨璨、陈莉、胡尚游、杨秀川、陈俊、陈俊、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、张阳东、许旭、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、刘赞、欧阳智聪、欧阳智聪、欧阳智聪、欧阳智聪、赖晓艳、税月、刘溯、朱怡、朱怡、朱怡、朱怡、曹大峰、李琴、朱怡、曹大峰、巩佳知、朱怡、曹大峰、赖立根、陈晓春、赖晓艳、谢璨璨、李琴、朱怡、谢璨璨、陈晓春、朱怡、曹大峰、谢璨璨、黄有为、朱怡、曹大峰、谢璨璨、黄有为、朱怡、巩佳知、";

		String[] ss = s.split("、");
		Set<String> set = new HashSet<String>();
		for (String sss : ss) {
			set.add(sss);
		}

		for (String sss : set) {
			System.out.println(sss);
		}

		// BufferedReader reader = new BufferedReader(
		// new InputStreamReader(new FileInputStream("G:\\documents\\Tencent
		// Files\\342589700\\FileRecv\\导出安装参数1.csv"), "GBK"));
		// reader.readLine();
		//
		// String row = reader.readLine();
		// Map<String, List<String>> sss = new HashMap<String, List<String>>();
		// Set<String> productAids = new HashSet<String>();
		// int i = 1;
		// while (null != row) {
		// System.out.println("处理第" + i + "行");
		// i++;
		// String[] colunms = row.split(",");
		// String productAid = colunms[2];
		// if (!productAids.contains(productAid)) {
		//
		// String applicationAid = colunms[4];
		// if (sss.containsKey(applicationAid)) {
		// productAids.add(productAid);
		// sss.get(applicationAid).add(row);
		// } else {
		// String installParam = colunms[8];
		// if (installParam.contains("CF0180")) {
		// productAids.add(productAid);
		// List<String> ss = new ArrayList<String>();
		// ss.add(row);
		// sss.put(applicationAid, ss);
		// }
		// }
		// }
		// row = reader.readLine();
		// }
		//
		// for (String aid : sss.keySet()) {
		// List<String> s = sss.get(aid);
		// for (String str : s) {
		// System.out.println(str);
		// }
		// }
	}
}
