package com.example.customersupport.site;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.View;

import java.util.Map;

public class DownloadView implements View {
    private final String filename;
    private final byte[] content;

    public DownloadView(String filename, byte[] content){
        this.filename = filename;
        this.content=content;
    }


    @Override
    public String getContentType() {
        return View.super.getContentType();
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setHeader("Content-Disposition", "attachment; filename=" +filename);
        resp.setContentType("application/octet-stream");
        ServletOutputStream out = resp.getOutputStream();
        out.write(content);
    }
}
