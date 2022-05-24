package teamspace.pojo;

public class JWTDataPojo {

    private String userId;
    private int role;
    private String companyId;
    private String fullName;
    private String jwt;

    public JWTDataPojo(String userId, int role, String companyId, String fullName , String jwt) {
        this.userId = userId;
        this.role = role;
        this.companyId = companyId;
        this.fullName = fullName;
        this.jwt = jwt;
    }




    public String getJwt() {
        return jwt;
    }

    public String getUserId() {
        return userId;
    }

    public int getRole() {
        return role;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getFullName() {
        return fullName;
    }


    @Override
    public String toString() {
        return "JWTDataPojo{" +
                "userId='" + userId + '\'' +
                ", role=" + role +
                ", companyId='" + companyId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
