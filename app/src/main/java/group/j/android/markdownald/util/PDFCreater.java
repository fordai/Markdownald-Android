package group.j.android.markdownald.util;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;

public class PDFCreater {

    private Context context;

    public PDFCreater(Context context){
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createPDF(View view) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                view.getMeasuredWidth(), view.getMeasuredHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        view.draw(page.getCanvas());
        document.finishPage(page);
        try{
            String path = FileUtils.getFiles(context) + "\\table.pdf";
            Log.i(TAG, path);
            File e = new File(path);
            if (e.exists()) {
                e.delete();
            }
            document.writeTo(new FileOutputStream(e));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public void createPDF(String content) {
//        textTransformPdf(content, "E:\\");
//    }
//
//    public void textTransformPdf(String content,String pdf_save_address){
//        Document doc = new Document();
//        FileOutputStream fos;
//        try {
//            fos = new FileOutputStream(pdf_save_address);
//            PdfWriter.getInstance(doc, fos);
//            doc.open();
//            doc.setPageCount(1);
//            doc.add(new Paragraph(content, setChineseFont()));
//            doc.close();
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Font setChineseFont() {
//        BaseFont bf = null;
//        Font fontChinese = null;
//        try {
//            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//            fontChinese = new Font(bf, 12, Font.NORMAL);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fontChinese;
//    }

}
