package org.fisco.bcos.web3j.console;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Stack;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.apache.commons.io.FileUtils;
import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.fisco.bcos.web3j.solidity.compiler.CompilationResult;
import org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler;

public class ConsoleUtils {

  public static final String JAVAPATH = "java/org/fisco/bcos/temp";
  public static final String CLASSPATH = "java/classes/org/fisco/bcos/temp";
  public static final String TARGETCLASSPATH = "java/classes";
  public static final String PACKAGENAME = "org.fisco.bcos.temp";

  public static void printJson(String jsonStr) {
    System.out.println(formatJson(jsonStr));
  }

  public static String formatJson(String jsonStr) {
    if (null == jsonStr || "".equals(jsonStr)) return "";
    jsonStr = jsonStr.replace("\\n", "");
    StringBuilder sb = new StringBuilder();
    char last = '\0';
    char current = '\0';
    int indent = 0;
    boolean isInQuotationMarks = false;
    for (int i = 0; i < jsonStr.length(); i++) {
      last = current;
      current = jsonStr.charAt(i);
      switch (current) {
        case '"':
          if (last != '\\') {
            isInQuotationMarks = !isInQuotationMarks;
          }
          sb.append(current);
          break;
        case '{':
        case '[':
          sb.append(current);
          if (!isInQuotationMarks) {
            sb.append('\n');
            indent++;
            addIndentBlank(sb, indent);
          }
          break;
        case '}':
        case ']':
          if (!isInQuotationMarks) {
            sb.append('\n');
            indent--;
            addIndentBlank(sb, indent);
          }
          sb.append(current);
          break;
        case ',':
          sb.append(current);
          if (last != '\\' && !isInQuotationMarks) {
            sb.append('\n');
            addIndentBlank(sb, indent);
          }
          break;
        case ' ':
          if (',' != jsonStr.charAt(i - 1)) {
            sb.append(current);
          }
          break;
        case '\\':
          break;
        default:
          if (!(current == " ".charAt(0))) sb.append(current);
      }
    }

    return sb.toString();
  }

  private static void addIndentBlank(StringBuilder sb, int indent) {
    for (int i = 0; i < indent; i++) {
      sb.append("    ");
    }
  }

  public static boolean isInvalidHash(String hash) {
    if (hash.startsWith("0x") && hash.length() == 66) {
      return false;
    } else {
      System.out.println("Please provide a valid hash.");
      System.out.println();
      return true;
    }
  }

  public static boolean isInvalidNumber(String number, int flag) {
    String numberStr = number.trim();
    if (!numberStr.matches("^[0-9]*$") || "".equals(numberStr)) {
      if (flag == 0)
        System.out.println("Please provide block number as a decimal non-negative integer.");
      else
        System.out.println("Please provide transaction index as a decimal non-negative integer.");
      System.out.println();
      return true;
    } else {
      return false;
    }
  }

  public static boolean isInvalidAddress(String address) {
    if (!address.startsWith("0x") || (address.length() != 42)) {
      System.out.println("Please provide a valid address.");
      System.out.println();
      return true;
    } else {
      return false;
    }
  }

  // dynamic compile target java code
  public static void dynamicCompileJavaToClass() throws Exception {

    File sourceDir = new File(JAVAPATH);
    if (!sourceDir.exists()) {
      sourceDir.mkdirs();
    }

    File distDir = new File(TARGETCLASSPATH);
    if (!distDir.exists()) {
      distDir.mkdirs();
    }
    File[] javaFiles = sourceDir.listFiles();
    for (File javaFile : javaFiles) {
      JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
      int compileResult =
          javac.run(null, null, null, "-d", distDir.getAbsolutePath(), javaFile.getAbsolutePath());
      if (compileResult != 0) {
        System.err.println("compile failed!!");
        System.out.println();
        return;
      }
    }
  }

  // dynamic load class
  public static void dynamicLoadClass()
      throws NoSuchMethodException, MalformedURLException, InvocationTargetException,
          IllegalAccessException, ClassNotFoundException {

    int clazzCount = 0;
    File clazzPath = new File(TARGETCLASSPATH);

    if (clazzPath.exists() && clazzPath.isDirectory()) {

      int clazzPathLen = clazzPath.getAbsolutePath().length() + 1;

      Stack<File> stack = new Stack<>();
      stack.push(clazzPath);

      while (!stack.isEmpty()) {
        File path = stack.pop();
        File[] classFiles =
            path.listFiles(
                new FileFilter() {
                  public boolean accept(File pathname) {
                    return pathname.isDirectory() || pathname.getName().endsWith(".class");
                  }
                });
        for (File subFile : classFiles) {
          if (subFile.isDirectory()) {
            stack.push(subFile);
          } else {
            if (clazzCount++ == 0) {
              Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
              boolean accessible = method.isAccessible();
              try {
                if (!accessible) {
                  method.setAccessible(true);
                }
                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                method.invoke(classLoader, clazzPath.toURI().toURL());
              } finally {
                method.setAccessible(accessible);
              }
            }
            String className = subFile.getAbsolutePath();
            if (className.contains("$")) {
              continue;
            }
            className = className.substring(clazzPathLen, className.length() - 6);
            className = className.replace(File.separatorChar, '.');
            Class.forName(className);
          }
        }
      }
    }
  }

  public static void dynamicCompileSolFilesToJava() throws IOException {
    File solFileList = new File("solidity/contracts/");
    File[] solFiles = solFileList.listFiles();

    for (File solFile : solFiles) {

      SolidityCompiler.Result res =
          SolidityCompiler.compile(
              solFile,
              true,
              SolidityCompiler.Options.ABI,
              SolidityCompiler.Options.BIN,
              SolidityCompiler.Options.INTERFACE,
              SolidityCompiler.Options.METADATA);
      CompilationResult result = CompilationResult.parse(res.output);
      String contractname = solFile.getName().split("\\.")[0];
      CompilationResult.ContractMetadata a = result.getContract(solFile.getName().split("\\.")[0]);
      FileUtils.writeStringToFile(new File("solidity/abi/" + contractname + ".abi"), a.abi);
      FileUtils.writeStringToFile(new File("solidity/bin/" + contractname + ".bin"), a.bin);
      String binFile;
      String abiFile;
      String tempDirPath = new File("java").getAbsolutePath();
      String filename = contractname;
      abiFile = "solidity/abi/" + filename + ".abi";
      binFile = "solidity/bin/" + filename + ".bin";
      SolidityFunctionWrapperGenerator.main(
          Arrays.asList("-a", abiFile, "-b", binFile, "-p", PACKAGENAME, "-o", tempDirPath)
              .toArray(new String[0]));
    }
  }

  public static void singleLine() {
    System.out.println(
        "-------------------------------------------------------------------------------------");
  }

  public static void singleLineForTable() {
    System.out.println(
        "---------------------------------------------------------------------------------------------");
  }

  public static void doubleLine() {
    System.out.println(
        "=====================================================================================");
  }
}
