package com.example.retrofit_android;

public class Repo {
    public final String name;
    public final int stargarzers_count;
    public final String description;

    public Repo(String _name, int _stargazers, String _description) {
        this.name = _name;
        this.stargarzers_count = _stargazers;
        this.description=_description;
    }
}
