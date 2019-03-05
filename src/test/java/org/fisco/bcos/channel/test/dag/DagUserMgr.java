package org.fisco.bcos.channel.test.dag;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DagUserMgr {
	private static Logger logger = LoggerFactory.getLogger(DagUserMgr.class);

	private List<DagTransferUser> userList = new ArrayList<DagTransferUser>();
	private String file = null;
	private boolean transfer = false;

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	synchronized public void addUser(DagTransferUser user) {
		userList.add(user);
	}

	public boolean isEmpty() {
		return userList.isEmpty();
	}
	
	public DagTransferUser getFrom(int idx) {
		assert !isEmpty() : "Has no user.";
		return userList.get(idx % userList.size());
	}
	
	public DagTransferUser getTo(int idx) {
		assert !isEmpty() : "Has no user.";
		int mid = userList.size();
		return userList.get((idx + mid) % userList.size());
	}

	public DagTransferUser getNext(int idx) {
		return userList.get((idx + 1) % userList.size());
	}

	public void writeDagTransferUser() throws IOException {

		if (null == file) {
			logger.info("file null, return.");
			return;
		}

		if (isTransfer()) {
			logger.info("not transfer test, return.");
			return;
		}

		logger.info("file {}, begin load.", file);

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(file)));
			for (int i = 0; i < userList.size(); i++) {
				bw.write(userList.get(i).getUser() + "\n");
				logger.trace(" write user , user is {}", userList.get(i).getUser());
			}

			bw.flush();

		} finally {
			if (bw != null) {
				bw.close();
			}
		}

		logger.info("file {}, load end, count is {}.", file, userList.size());

		System.out.println(" # write DagTransferUser end, count is " + userList.size());
	}

	public void loadDagTransferUser() throws IOException {

		if (null == file) {
			logger.info("file null, return.");
			return;
		}

		if (!isTransfer()) {
			logger.info("transfer test, return.");
			return;
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(file)));

			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					DagTransferUser user = new DagTransferUser();
					user.setUser(line);
					addUser(user);
					// System.out.println("load DagTransferUser ==>> " + line);
				}
			}

		} finally {
			if (br != null) {
				br.close();
			}
		}

		logger.info("file {}, load end, count is {}.", file, userList.size());

		System.out.println(" # load DagTransferUser end, count is " + userList.size());
	}
}