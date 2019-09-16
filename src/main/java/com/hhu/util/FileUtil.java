package com.hhu.util;

import java.io.*;

public class FileUtil {

    public static void main(String[] args) throws IOException {
        // String path = "F:\\Project\\java\\WordCount\\src\\test\\java\\com\\hhu\\word.txt";
        // split(path, 8);
        System.out.println(getProjectParentPath());
    }

    /**
     *  将文件中的内容转化为数组
     * @param file
     * @return
     * @throws IOException
     */
    public static int[] file2Array(File file) throws IOException {
        String content = getFileContent(file);
        String[] str = content.split("\n");
        int[] a = new int[str.length];
        for (int i =0; i < str.length; i++) {
            a[i] = Integer.parseInt(str[i]);
        }
        return a;
    }

    /**
     * 获取目录下所有文件
     * @param path
     * @return
     */
    public static File[] fileList(String path) {
        File file = new File(path);
        File[] fileList = null;
        if (file.isDirectory()) {
            fileList = file.listFiles();
        }
        return fileList;
    }

    /**
     * 将指定字符串写入文件
     * @param content
     * @param path
     * @param fileName
     */
    public static void writeToFile(String content, String path, String fileName) throws IOException {
        File file = new File(path + "\\" + fileName);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        pw.write(content);
        pw.flush();
        pw.close();
    }

    /**
     * 读取指定文件的内容
     * @param file
     * @return 文件内容的字符串
     * @throws IOException
     */
    public static String getFileContent(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line=br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }

    /**
     * 将源文件分割成 num 份
     * 1.txt 2.txt ...
     * @param src 源文件路径
     * @param num 指定份数
     * @throws IOException
     */
    public static void split(String src, int num) throws IOException {
        File srcFile = new File(src);
        int linesCount = linesCount(srcFile);
        int batch = linesCount / num;
        File destFile;
        // 获取file所在的目录
        String path = srcFile.getParent();
        for (int i = 0; i < num-1; i++) {
            destFile = new File(path+"\\" + (i + 1) + ".txt");
            split(srcFile, destFile,i * batch + 1, i * batch + batch);
        }
        destFile = new File(path+"\\" + num + ".txt");
        split(srcFile, destFile,(num - 1)*batch +1, linesCount);
    }

    /**
     * 将指定的行数写入到目标文件
     * @param srcFile
     * @param destFile
     * @param startRow 开始行数
     * @param endRow 结束行数
     * @throws IOException
     */
    private static void split(File srcFile, File destFile, int startRow, int endRow) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile)));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(destFile, true)));
        int now = 1;
        while (now != startRow) {
            br.readLine();
            now++;
        }
        String line;
        while ((line=br.readLine()) != null && now <= endRow) {
            pw.println(line);
            pw.flush();
            now++;
        }
        br.close();
        pw.close();
    }

    /**
     * 获取文件的行数
     * @param srcFile
     * @return
     * @throws IOException
     */
    public static int linesCount(File srcFile) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile)));
        int row = (int) br.lines().count();
        br.close();
        return row;
    }

    public static String getProjectParentPath() {
        // 获取当前项目路径
        String projectPath = System.getProperty("user.dir");
        projectPath.replace("\\", "\\\\");

        return projectPath;
    }

}
