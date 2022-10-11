package com.example.mebournists;

import java.util.Comparator;

public class Recommendation
{

    private String id;
    private String name;
    private int duration;
    private int image;
    private String activityLevel;
    private String description;



    public Recommendation(String id, String name, int duration, String activityLevel, int image, String description)
    {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.activityLevel = activityLevel;
        this.image = image;
        this.description = description;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static Comparator<Recommendation> durationAscending = new Comparator<Recommendation>()
    {
        @Override
        public int compare(Recommendation recommendation1, Recommendation recommendation2)
        {
            int duration1 = Integer.valueOf(recommendation1.getDuration());
            int duration2 = Integer.valueOf(recommendation2.getDuration());

            return Integer.compare(duration1, duration2);
        }
    };

    public static Comparator<Recommendation> nameAscending = new Comparator<Recommendation>()
    {
        @Override
        public int compare(Recommendation recommendation1, Recommendation recommendation2)
        {
            String name1 = recommendation1.getName();
            String name2 = recommendation2.getName();
            name1 = name1.toLowerCase();
            name2 = name2.toLowerCase();

            return name1.compareTo(name2);
        }
    };
}