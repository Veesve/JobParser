package ru.view;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.Vacancy;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class HtmlView implements ViewType {
    private static final String filePath = "src/ru/view/results.html";

    @Override
    public void show(List<Vacancy> vacancyList) {
        File file = new File(filePath).getAbsoluteFile();
        if (!file.exists() && !file.isDirectory())
            file = createFile();
        updateFile(updateFileContent(vacancyList, file));
        openHtml(file);


    }

    private void openHtml(File file) {
        try {
            Runtime rTime = Runtime.getRuntime();
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateFile(String html) {
        try {
            File file = new File(filePath);
            if (!file.exists())
                file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            writer.print(html);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String updateFileContent(List<Vacancy> vacancyList, File file) {
        Document doc;
        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "some exception occured";
        }
        Element template = doc.getElementsByClass("template").first();
        doc.select("tr[class=vacancy]").remove();
        for (Vacancy vacancy : vacancyList) {
            Element newTemplate = template.clone().removeClass("template").addClass("vacancy");
            newTemplate.getElementsByClass("title")
                    .first().appendElement("a")
                    .attr("href",vacancy.getUrl())
                    .text(vacancy.getTitle());//оборачивание элемента Title в новодобавленный тег a
            newTemplate.getElementsByClass("company_name").first().text(vacancy.getCompanyName());
            newTemplate.getElementsByClass("creating_date").first().text(vacancy.getCreatingDate().toString());
            newTemplate.getElementsByClass("address").first().text(vacancy.getAddress());
            newTemplate.getElementsByClass("salary").first().text(vacancy.getSalaryCount());
            newTemplate.getElementsByClass("site").first().text(vacancy.getSiteName());
            template.before(newTemplate.outerHtml());
        }

        return doc.html();
    }

    private static File createFile() {
        File file = new File(filePath);
        String html ="<!doctype html>\n" +
                "<html lang=\"ru\">"+
                "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\"> \n" +
                "<title>Вакансии</title> "+
                "</head>" +
                "<body>" +
                "<table>" +
                "<tr>" +
                "<td>Title</td>" +
                "<td>Company Name</td>" +
                "<td>Creating date</td>" +
                "<td>Address</td>" +
                "<td>Salary</td>" +
                "<td>Site</td>" +
                "</tr>" +
                "<tr class = \"template\">" +
                "<td class = \"title\"></td>" +
                "<td class = \"company_name\"></td>" +
                "<td class = \"creating_date\"></td>" +
                "<td class = \"address\"></td>" +
                "<td class = \"salary\"></td>" +
                "<td class = \"site\"></td>" +
                "</tr>" +
                "</table>" +
                "</body><" +
                "/html>";

        updateFile(Jsoup.parse(html).html());
        return file;
    }

}
