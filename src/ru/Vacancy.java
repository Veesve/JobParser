package ru;

public class Vacancy {
    private String companyName;
    private String salaryCount;
    private String creatingDate;
    private String title;
    private String url;
    private String address;
    private String siteName;

    public String getCompanyName() {
        return companyName;
    }

    public String getSalaryCount() {
        return salaryCount;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAddress() {
        return address;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSalaryCount(String salaryCount) {
        this.salaryCount = salaryCount;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "companyName='" + companyName + '\'' +
                ", salaryCount='" + salaryCount + '\'' +
                ", creatingDate='" + creatingDate + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}