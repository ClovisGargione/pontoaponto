/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

/**
 *
 * @author clovis.rodrigues
 */
public class PagerModel {
    
    private int buttonsToShow = 5;
    private int startPage;
    private int endPage;
    private int totalPages;
    private int number;
    
    public PagerModel(int totalPages, int currentPage, int buttonsToShow, int totalpages, int number) {
        setButtonsToShow(buttonsToShow);
        int halfPagesToShow = getButtonsToShow() / 2;
        if (totalPages <= getButtonsToShow()) {
            setStartPage(1);
            setEndPage(totalPages);
        } else if (currentPage - halfPagesToShow <= 0) {
            setStartPage(1);
            setEndPage(getButtonsToShow());
        } else if (currentPage + halfPagesToShow == totalPages) {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(totalPages);
        } else if (currentPage + halfPagesToShow > totalPages) {
            setStartPage(totalPages - getButtonsToShow() + 1);
            setEndPage(totalPages);
        } else {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(currentPage + halfPagesToShow);
        }
        this.totalPages =  totalpages;
        this.number = number;
    }

    public int getButtonsToShow() {
        return buttonsToShow;
    }

    public void setButtonsToShow(int buttonsToShow) {
        if (buttonsToShow % 2 != 0) {
            this.buttonsToShow = buttonsToShow;
        } else {
            throw new IllegalArgumentException("Must be an odd value!");
        }
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalpages) {
        this.totalPages = totalpages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
    
    @Override
    public String toString() {
        return "Pager [startPage=" + startPage + ", endPage=" + endPage + "]";
    }
 }
