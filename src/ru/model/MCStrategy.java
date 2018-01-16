package ru.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MCStrategy implements Strategy {
    private final static String URL_FORMAT =
            "https://moikrug.ru/vacancies?city_id=678&page=%d&q=%s";

    @Override
    public List<Vacancy> getVacancies(String request) {
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        for (int i = 0; ; i++) {
            Document document = connectToServer(request, i);
            if (document != null) {
                Elements jobElements = document.getElementsByClass("job  ");
                if (jobElements.size() > 0) {
                    for (Element e : jobElements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setCompanyName(getCompanyName(e));
                        vacancy.setSalaryCount(getSalaryCount(e));
                        vacancy.setCreatingDate(getCreatingDate(e));
                        vacancy.setTitle(getTitle(e));
                        vacancy.setUrl(getUrl(e));
                        vacancy.setAddress(getAddress(e));
                        vacancy.setSiteName("https://moikrug.ru/");
                        vacancies.add(vacancy);
                    }
                } else
                    break;
            }
        }
        return vacancies;
    }


    private static Document connectToServer(String request, int page) {
        try {
            return Jsoup.connect(String.format(URL_FORMAT, page, request)).
                    userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                    .referrer("no-referrer-when-downgrade")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getSalaryCount(Element element) {
        Elements salary = element.getElementsByClass("salary");
        if (salary.size() > 0) {
            return salary.text();
        } else
            return "";
    }

    private String getAddress(Element element) {
        Elements city = element.getElementsByClass("location");
        return city.text();
    }

    private String getCompanyName(Element element) {
        Elements companyName = element.getElementsByClass("company_name");
        return companyName.text();
    }

    private String getTitle(Element element) {
        Elements title = element.
                getElementsByClass("title");
        return title.text();
    }

    private String getUrl(Element element) {

        Elements URL = element.getElementsByClass("title");
        for (Element e : URL.first().getElementsByTag("a"))
            if (e.hasAttr("href"))
                return "https://moikrug.ru" + e.attr("href");
        return "";
    }

    private String getCreatingDate(Element element) {
        Element creatingDate = element.getElementsByClass("date").first();
        return creatingDate.text();
    }


}