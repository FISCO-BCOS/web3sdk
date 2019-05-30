package org.fisco.bcos.channel.test.parallel.parallelok;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DagUserMgr {
    private static Logger logger = LoggerFactory.getLogger(DagUserMgr.class);

    private List<DagTransferUser> userList = new ArrayList<DagTransferUser>();

    private String file = null;

    private String testType = "transfer";

    private String parallelokAddr = "";

    public void setContractAddr(String addr) {
        this.parallelokAddr = addr;
    }

    public String getContractAddr() {
        return this.parallelokAddr;
    }

    public List<DagTransferUser> getUserList() {
        return userList;
    }

    public void setUserList(List<DagTransferUser> userList) {
        this.userList = userList;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public synchronized void addUser(DagTransferUser user) {
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
        int mid = userList.size() / 2;
        return userList.get((idx + mid) % userList.size());
    }

    public DagTransferUser getNext(int idx) {
        return userList.get((idx + 1) % userList.size());
    }

    public void writeDagTransferUser() throws IOException {
        if (file == null) {
            return;
        }
        logger.info("file {}, begin load.", file);

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(file)));

            // Write contract address first
            bw.write(parallelokAddr + "\n");

            // And write user
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

        System.out.println(" Write DagTransferUser end, count is " + userList.size());
    }

    public void loadDagTransferUser() throws IOException {
        if (file == null) {
            return;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(file)));

            String line = null;

            // Get contract addr first
            if ((line = br.readLine()) != null) {
                parallelokAddr = line;
            }

            // And get user
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

        System.out.println("Load DagTransferUser end, count is " + userList.size());
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
}
