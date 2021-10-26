package com.example.flickermsapps;

public class FlickerPhoto {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private String farm;
    private String title;
    private String ispublic;
    private String isfriend;
    private String isfamily;
    private String photoUrl;


    public FlickerPhoto(String id, String owner, String secret, String server, String farm, String title, String ispublic, String isfriend, String isfamily, String photoUrl) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.ispublic = ispublic;
        this.isfriend = isfriend;
        this.isfamily = isfamily;
        this.photoUrl = photoUrl;
    }

    public FlickerPhoto() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public void setIsfamily(String isfamily) {
        this.isfamily = isfamily;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof FlickerPhoto)
        {
            sameSame = this.photoUrl == ((FlickerPhoto) object).getPhotoUrl();
        }

        return sameSame;
    }
}
