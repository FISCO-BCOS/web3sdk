/*
 * Copyright (c) [2016] [ <ether.camp> ]
 * This file is part of the ethereumJ library.
 *
 * The ethereumJ library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ethereumJ library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ethereumJ library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fisco.bcos.web3j.solidity.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/** Created by Anton Nashatyrev on 03.03.2016. */
public class Solc {

  private File solc = null;

  Solc() {
    try {
      initBundled();
    } catch (IOException e) {
      throw new RuntimeException("Can't init solc compiler: ", e);
    }
  }

  private void initBundled() throws IOException {
    File tmpDir = new File(System.getProperty("user.home"), "solc");
    tmpDir.mkdirs();

    InputStream is = getClass().getResourceAsStream("/native/" + getOS() + "/solc/file.list");
    try (Scanner scanner = new Scanner(is)) {
      while (scanner.hasNext()) {
        String s = scanner.next();
        File targetFile = new File(tmpDir, s);
        InputStream fis = getClass().getResourceAsStream("/native/" + getOS() + "/solc/" + s);
        Files.copy(fis, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        if (solc == null) {
          // first file in the list denotes executable
          solc = targetFile;
          solc.setExecutable(true);
        }
      }
    }
  }

  private String getOS() {
    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.contains("win")) {
      return "win";
    } else if (osName.contains("linux")) {
      return "linux";
    } else if (osName.contains("mac")) {
      return "mac";
    } else {
      throw new RuntimeException("Can't find solc compiler: unrecognized OS: " + osName);
    }
  }

  public File getExecutable() {
    return solc;
  }
}
