package com.example.quizz_androidapp.data.model.subject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectResponse {
    @SerializedName("results")
    private List<Subject> results;

    @SerializedName("page")
    private int page;

    @SerializedName("limit")
    private int limit;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("totalResults")
    private int totalResults;

    public SubjectResponse(List<Subject> results, int page, int limit, int totalPages, int totalResults) {
        this.results = results;
        this.page = page;
        this.limit = limit;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public List<Subject> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setResults(List<Subject> results) {
        this.results = results;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
