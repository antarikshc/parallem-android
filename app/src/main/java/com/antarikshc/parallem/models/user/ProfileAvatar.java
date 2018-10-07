package com.antarikshc.parallem.models.user;

public class ProfileAvatar {

    private Integer avatarId;
    private Boolean isSelected;

    public ProfileAvatar(Integer avatarId, Boolean isSelected) {
        this.avatarId = avatarId;
        this.isSelected = isSelected;
    }

    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
