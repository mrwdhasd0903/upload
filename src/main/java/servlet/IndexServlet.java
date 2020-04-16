package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

@WebServlet("/api")
@MultipartConfig
public class IndexServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //获取表单中为name的文件
        Part part = req.getPart("file");
        //获取文件名 form-data; name="file"; filename="qweqwe.jpg"
        String disposition = part.getHeader("Content-Disposition");
        //截取后缀 如果抛出异常则为空文件
        String suffix = null;
        try {
            suffix = disposition.substring(disposition.lastIndexOf("."), disposition.length() - 1);
        } catch (Exception e) {
            return;
        }
        //生成文件相对服务器路径 如:io+img+2020+03+31+
        String filepath = "/io/" + getDirectory(suffix) + "/" + getPath() + "/";
        //生成文件名 如 42364512.png
        String filename = System.currentTimeMillis() + suffix;
        // 生成服务器访问路径 如 D:-IdeaProjects-upload-src-main-webapp
        String serverpath = req.getServletContext().getRealPath("");
        //创建绝对目录
        this.mkdir(serverpath + filepath);
        FileOutputStream fos = new FileOutputStream(serverpath + filepath + filename);
        byte[] bty = new byte[1024];
        //获取上传的 io流
        InputStream is = part.getInputStream();
        int length = 0;
        //写入文件
        while ((length = is.read(bty)) != -1) {
            fos.write(bty, 0, length);
        }
        //关闭io流
        fos.close();
        is.close();
        //返回路径给前端
        resp.getWriter().write("/upload"+filepath + filename);
    }

    /**
     * 创建目录
     *
     * @param path
     */
    public static void mkdir(String path) {
        File fd = null;
        try {
            fd = new File(path);
            if (!fd.exists()) {
                fd.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fd = null;
        }
    }

    /**
     * 根据后缀获取目录
     *
     * @param suffix
     * @return
     */
    public static String getDirectory(String suffix) {
        String Directory;
        suffix = suffix.toLowerCase();
        if (".jpg|.jpeg|.png|.gif".indexOf(suffix) != -1) {
            Directory = "img";//图片
        } else if (".md|.pdf|.txt|.doc|.docx|.xls|.xlsx|.ppt|.pptx|".indexOf(suffix) != -1) {
            Directory = "doc";//文档
        } else if (".mp3|.cd|.wave|.aiff|.mpeg".indexOf(suffix) != -1) {
            Directory = "audio";//音频
        } else if (".mp4".indexOf(suffix) != -1) {
            Directory = "video";//视频
        } else {
            Directory = "other";//其他
        }
        return Directory;
    }

    /**
     * 根据系统时间生成路径
     *
     * @return
     */
    public static String getPath() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        int year = calendar.get(Calendar.YEAR);
        // 获取当前月
        int month = calendar.get(Calendar.MONTH) + 1;
        // 获取当前日
        int day = calendar.get(Calendar.DATE);
        String path = year + "/" + month + "/" + day;
        return path;
    }
}
