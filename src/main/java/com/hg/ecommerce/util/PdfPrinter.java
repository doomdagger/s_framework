package com.hg.ecommerce.util;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This Class is dedicated to produce pdf document,
 * <strong>Still Under Development</strong>
 * @author lihe9_000
 *
 */
public class PdfPrinter {
	public static void main(String[] args) throws Exception{
		FileOutputStream out = new FileOutputStream("demo.pdf");
		//Rectangle rectangle = new Rectangle(216f, 720f);
		//Document document = new Document(rectangle, 36f, 72f, 108f, 180f);
		Document document = new Document(PageSize.A4);
		
		//document.setPageSize(PageSize.B5);
		//document.setMarginMirroring(true);
		//document.setMarginMirroringTopBottom(true);
		
		//BaseFont msyh = BaseFont.createFont("src/main/resources/com/hg/ecommerce/util/SIMSUNB.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		BaseFont msyh = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		
		PdfWriter writer = PdfWriter.getInstance(document, out);
		writer.setCloseStream(false);
		//writer.setUserunit(100f);
		
		document.open();
		
		document.add(new Paragraph("Hello World!"));
		
		document.add(new Chunk("中国",new Font(msyh,12,Font.BOLD)));
		document.add(new Chunk(" "));
		
		Font font = new Font(FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
		Chunk chunk = new Chunk("+86",font);
		chunk.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
		chunk.setTextRise(6f);
		
		document.add(chunk);
		document.add(Chunk.NEWLINE);
		
		
		document.close();
		out.close();
	}
	
}
