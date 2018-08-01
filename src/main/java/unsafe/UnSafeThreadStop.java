package unsafe;

public class UnSafeThreadStop {

	public static User u = new User();

	public static class User {
		private int id;
		private String name;

		public User() {
			id = 0;
			name = "0";
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/** (non Javadoc) 
		 * @Title: toString
		 * @Description: TODO
		 * @return 
		 * @see java.lang.Object#toString() 
		 */ 
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}
	}

	public static class ChangeUserThread extends Thread {

		/**
		 * (non Javadoc)
		 * 
		 * @Title: run
		 * @Description:
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (true) {
				synchronized (u) {
					int v = (int) (System.currentTimeMillis() / 1000);
					u.setId(v);
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
					u.setName(String.valueOf(v));
				}
				Thread.yield();
			}
		}

	}

	public static class ReadUserThread extends Thread {

		/**
		 * (non Javadoc)
		 * 
		 * @Title: run
		 * @Description: TODO
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (true) {
				synchronized (u) {
					if (u.getId() != Integer.valueOf(u.getName())) {
						System.out.println(u);
					}
				}
				Thread.yield();
			}

		}

	}
	
	
	public static void main(String[] args) throws InterruptedException{
		new ReadUserThread().start();
		while(true){
			Thread t = new ChangeUserThread();
			t.start();
			Thread.sleep(150);
			t.stop();
			}
	}

}
