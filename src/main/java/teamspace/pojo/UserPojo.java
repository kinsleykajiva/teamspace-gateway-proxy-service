package teamspace.pojo;


import java.time.OffsetDateTime;

public class UserPojo {


    long id;
    String email;
    String password;
    String fullName;
    String profilePictureUrl;
    boolean isActive;
    boolean isIsVerified;
    String companyClientId;
    OffsetDateTime createdAt;

    String id_;

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isIsVerified() {
        return isIsVerified;
    }

    public void setIsVerified(boolean isVerified) {
        isIsVerified = isVerified;
    }

    public String getCompanyClientId() {
        return companyClientId;
    }

    public void setCompanyClientId(String companyClientId) {
        this.companyClientId = companyClientId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserPojo{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", isActive=" + isActive +
                ", isIsVerified=" + isIsVerified +
                ", companyClientId='" + companyClientId + '\'' +
                ", createdAt=" + createdAt +
                ", id_='" + id_ + '\'' +
                '}';
    }
}


