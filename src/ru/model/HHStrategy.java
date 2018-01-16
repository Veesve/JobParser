package ru.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private final static String URL_FORMAT =
            "https://hh.ru/search/vacancy?text=%s&enable_snippets=true&clusters=true&area=1&page=%d";
    @Override
    public List<Vacancy> getVacancies(String request) {
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        for (int i = 0; ; i++) {
            Document document = connectToServer(request, i);
            if (document != null) {
                Elements jobElements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if(jobElements.size() > 0) {
                    for (Element e : jobElements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setCompanyName(getCompanyName(e));
                        vacancy.setSalaryCount(getSalaryCount(e));
                        vacancy.setCreatingDate(getCreatingDate(e));
                        vacancy.setTitle(getTitle(e));
                        vacancy.setUrl(getUrl(e));
                        vacancy.setAddress(getAddress(e));
                        vacancy.setSiteName("https://hh.ru");
                        vacancies.add(vacancy);
                    }
                }
                else
                    break;
            }
        }
        return vacancies;
    }

    private String getCompanyName(Element element){

        return element
                .getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer")
                .text();

    }
    private String getSalaryCount(Element element){
        String salary = element
                .getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-compensation")
                .text();
        if(salary.isEmpty())
            return "";
        return salary;
    }
    private String getCreatingDate(Element element){
        return element.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-date")
                .text();
    }
    private String getTitle(Element element){
        return element
                .getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-title")
                .text();
    }
    private String getUrl(Element element){
        return element.
                getElementsByClass("vacancy-serp-item__title")
                .first()
                .getElementsByTag("a")
                .attr("href");
    }
    private String getAddress(Element element){
        return element
                .getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-address")
                .text();
    }
    private static Document connectToServer(String request,int page){
        try {
            return Jsoup.connect(String.format(URL_FORMAT,request,page)).
                    userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                    .referrer("no-referrer-when-downgrade")
                    .get();
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
